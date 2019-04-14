package controller;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


import exceptions.BuildingAlreadyCollapsedException;
import exceptions.DisasterException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import view.Button;
import view.GameGUI;
import view.MainMenu;

public class CommandCenter implements SOSListener, MouseListener {
	private Simulator engine;
	private MainMenu GUI;
	
	private ArrayList<ResidentialBuilding> visibleBuildings;
	
	private ArrayList<Citizen> visibleCitizens;
	
	private ArrayList<Unit> emergencyUnits;
	private String logText="";
	
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<>();
		visibleCitizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
		GUI = new MainMenu(this);
		GameGUI game=GUI.getGame();
		updateCitizens(game);
		updateBuildings(game);
		
		
	}

	
	public Simulator getEngine() {
		return engine;
	}


	public MainMenu getGUI() {
		return GUI;
	}


	public ArrayList<ResidentialBuilding> getVisibleBuildings() {
		return visibleBuildings;
	}


	public ArrayList<Citizen> getVisibleCitizens() {
		return visibleCitizens;
	}


	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}


	public void receiveSOSCall(Rescuable r) {
		if(r instanceof Citizen) {
			visibleCitizens.add((Citizen)r);
			return;
		}
		if(r instanceof ResidentialBuilding) {
			visibleBuildings.add((ResidentialBuilding)r);
			return;
		}
		
	}
	public void updateCitizens(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (Citizen citizen : visibleCitizens) {
			int xC=citizen.getLocation().getX();
			int yC=citizen.getLocation().getY();
			view.Cell cell=cells[xC][yC];
			cell.setImage(new ImageIcon("icons/Game panel/citizen.png"));
		}
	}
	public void updateBuildings(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (ResidentialBuilding building : visibleBuildings) {
			int xC=building.getLocation().getX();
			int yC=building.getLocation().getY();
			view.Cell cell=cells[xC][yC];
			cell.setImage(new ImageIcon("icons/Game panel/building.png"));
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==GUI.getGame().getPanel().getTopBar().getEndCycle()) {
			try {
				engine.nextCycle();
				GUI.getGame().nextCycleGUI();
				updateLog(GUI.getGame());
				
			} catch (BuildingAlreadyCollapsedException e1) {
				// TODO Auto-generated catch block
				
			} catch (DisasterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == GUI.getGame().getPanel().getTopBar().getEndCycle()) {
			Button endButton=GUI.getGame().getPanel().getTopBar().getEndCycle();
			endButton.setIcon(new ImageIcon("icons/Game panel/endCycle1.png"));
			endButton.setLocation(1150,13);
			endButton.setSize(168,35);
			try {
				GUI.getGame().PlaySound("sounds/Morse.wav").start();
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == GUI.getGame().getPanel().getTopBar().getEndCycle()) {
			Button endButton=GUI.getGame().getPanel().getTopBar().getEndCycle();
			endButton.setIcon(new ImageIcon("icons/Game panel/endCycle.png"));
			endButton.setLocation(1150,17);
			endButton.setSize(135,25);
		}
	}
	public void updateLog(GameGUI game) {
		logText+="  Cycle: "+ GUI.getGame().getCurrentCycle()+"\n";
		for (Citizen citizen : visibleCitizens) {
			if(citizen.getDisaster().isActive() && 
					citizen.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
			 logText=logText+"  "+citizen.getDisaster().toString()+"\n";
			}
		}
		for (ResidentialBuilding building: visibleBuildings) {
			if(building.getDisaster().isActive() && 
					building.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
				logText=logText+"  "+building.getDisaster().toString()+"\n";
			}
		}
		logText+="------------------------------------------"+"\n";
		game.getPanel().getMidArea().getMidWest().getTop().getLog().getLogTextArea().setText(logText);
	}
	


	
}
