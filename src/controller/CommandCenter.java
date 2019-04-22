package controller;


import java.awt.FontFormatException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hamcrest.core.Is;

import Chating.Client;
import Chating.CustomOutputStream;
import Chating.Server;
import exceptions.DisasterException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.MedicalUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulatable;
import simulation.Simulator;
import view.Button;
import view.Cell;
import view.CellContent;
import view.Chatwindow;
import view.GameGUI;
import view.IPinput;
import view.MainMenu;
import view.MiniFrame;
import view.Selector;

public class CommandCenter implements SOSListener, MouseListener,ActionListener {
	private Simulator engine;
	private MainMenu GUI;
	
	private ArrayList<ResidentialBuilding> visibleBuildings;
	
	private ArrayList<Citizen> visibleCitizens;

	private ArrayList<Unit> emergencyUnits;
	private CellContent iconContent = null;
	private String logText="";
	
	private ArrayList<Citizen> deadCitizens;
	private ArrayList<ResidentialBuilding> collapsedBuildings;
	private Server server;
	private Chatwindow window;
	public CommandCenter() throws Exception {
		
		GUI = new MainMenu(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		deadCitizens = new ArrayList<Citizen>();
		collapsedBuildings = new ArrayList<ResidentialBuilding>();
		emergencyUnits = new ArrayList<Unit>();
		this.window=new Chatwindow();
		this.window.setVisible(false);
		JTextField inText=window.getInText();
		JTextArea textArea=window.getChatText();
		String text=inText.getText();
		InputStream input=new ByteArrayInputStream(text.getBytes("UTF-8"));
		OutputStream output=new CustomOutputStream(textArea);
		server=new Server(input, output);
		
	}

	public CellContent getIconContent() {
		return iconContent;
	}

	public String getLogText() {
		return logText;
	}

	public Chatwindow getWindow() {
		return window;
	}

	public ArrayList<Citizen> getDeadCitizens() {
		return deadCitizens;
	}

	public ArrayList<ResidentialBuilding> getCollapsedBuildings() {
		return collapsedBuildings;
	}

	public Server getServer() {
		return server;
	}

	public void setEmergencyUnits(ArrayList<Unit> emergencyUnits) {
		this.emergencyUnits = emergencyUnits;
	}

	public void setEngine(Simulator engine) {
		this.engine = engine;
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

		if((r instanceof Citizen) && !(visibleCitizens.contains((Citizen)r))) {
			visibleCitizens.add((Citizen)r);
			return;
		}
		if((r instanceof ResidentialBuilding) && !(visibleBuildings.contains((ResidentialBuilding)r))) {
			visibleBuildings.add((ResidentialBuilding)r);
			return;
		}
		
	}
	
	public void UpdateCellDisasterStatus() {
		for (int i = 0; i < 10; i++) {
			
			for (int j = 0; j < 10; j++) {
				boolean disaster = false;
				ArrayList<Citizen> lisyC = ListCitizens(i, j);
				for (Citizen citizen : lisyC) {
					if (citizen.getDisaster() != null && citizen.getDisaster().isActive()) {
						disaster = true;
					}
				}
				ResidentialBuilding building = buildingInCell(i, j);
				if (building != null && building.getDisaster() != null && building.getDisaster().isActive()) {
					disaster = true;
				}
				GUI.getGame().getPanel().getMidArea().getMidGrid().getCells()[i][j].setDisaster(disaster);
				if (disaster) 
					GUI.getGame().getPanel().getMidArea().getMidGrid().getCells()[i][j].setImage(new ImageIcon("icons/Game panel/red.png"));	
				else if (i != 0 && j != 0) 
					GUI.getGame().getPanel().getMidArea().getMidGrid().getCells()[i][j].setImage(new ImageIcon("icons/Game panel/grey.png"));
				
			}
		}
	}
	
	public void updateCitizens(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (Citizen citizen : visibleCitizens) {
			int xC=citizen.getLocation().getX();
			int yC=citizen.getLocation().getY();
			view.Cell cell=cells[xC][yC];
			if (xC != 0 || yC != 0) {	
			
				if(citizen.getState()==CitizenState.DECEASED) {
					cell.removeAll();
					cell.add(cell.getDeadCitizen());
				}
				else if(citizen.getToxicity()>0) {
					cell.removeAll();
					cell.add(cell.getInfectedCitizen());
				}
				else if(citizen.getBloodLoss()>0) {
					cell.removeAll();
					cell.add(cell.getInjuredCitizen());
				}
				else {
					cell.removeAll();
					cell.add(cell.getCitizen());
				}
			}
		}
	}
	public void updateBuildings(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (ResidentialBuilding building : visibleBuildings) {
			int xC=building.getLocation().getX();
			int yC=building.getLocation().getY();
			view.Cell cell=cells[xC][yC];
			if(building.getStructuralIntegrity()<=0) {
				cell.removeAll();
				cell.add(cell.getFallenBuilding());
			}
			else if(building.getFoundationDamage()>0) {
				cell.removeAll();
				cell.add(cell.getCollapse());
			}
			
			else if(building.getFireDamage()>0) {
				cell.removeAll();
				cell.add(cell.getBuildingOnFire());
			}
			else if(building.getGasLevel()>0) {
				cell.removeAll();
				cell.add(cell.getGasedBuilding());
			}
			else {
				cell.removeAll();
				cell.add(cell.getBuilding());
				}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==GUI.getGame().getPanel().getTopBar().getEndCycle()) {
			try {
				engine.nextCycle();
				GUI.getGame().nextCycleGUI();
				updateLog(GUI.getGame());
				
				Random ran = new Random();
				int x = ran.nextInt(5);
				GUI.getGame().PlaySound(GUI.getGame().getEndCycleSound()[x]).start();
				if(checkInput()) {
					this.window.setVisible(true);
				}
			} catch (DisasterException e1) {
				// TODO Auto-generated catch block
				//new MiniFrame(e1.getMessage());
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
		//to treat a target
		else if (e.getSource()==GUI.getGame().getPanel().getTopBar().getTreat()) {
			Rescuable selectedTarget = GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().getSelectedTarget();
			Unit selectedUnit = GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().getSelectedUnit();
			try{
				selectedUnit.respond(selectedTarget);
				selectedTarget = null;
				selectedUnit = null;
				GUI.getGame().getPanel().getTopBar().getUnit().setText("Unit: Empty");
				GUI.getGame().getPanel().getTopBar().getTarget().setText("Target: Empty");
				GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().setSelectedIndex(0);
				//GUI.getGame().PlaySound("sounds/Fruit collect 1.wav").start();
				updateUnitCount(UnitState.IDLE);
				updateUnitCount(UnitState.RESPONDING);
				updateUnitCount(UnitState.TREATING);
				
			}
			catch (Exception e1) {
				// TODO: handle exception
				
//				try {
//					GUI.getGame().PlaySound("sounds/Basso.wav").start();
//				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				
				new MiniFrame(e1.getMessage());
			}
			
		}
		else if(e.getSource()==GUI.getGame().getPanel().getTopBar().getChat()) {
			try {
				server.close();
				IPinput in=new IPinput();
				this.window.setVisible(true);
				this.window.setIp(in.getIp());
				String ip=window.getIp();
				JTextField inText=window.getInText();
				JTextArea textArea=window.getChatText();
				String text=inText.getText();
				InputStream input=new ByteArrayInputStream(text.getBytes("UTF-8"));
				OutputStream output=new CustomOutputStream(textArea);
				Client client=new Client(ip, input,output);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == GUI.getGame().getGameOver().getReturnButton()){
			
			try {
					GUI.PlaySound("sounds/Hero.wav").start();	
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
			
			try {
				CommandCenter newc = new CommandCenter();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			GUI.getGame().dispose();
		}
		
		else if(e.getSource() instanceof view.Cell) {
			
//			try {
//				GUI.getGame().PlaySound("sounds/Text 1.wav").start();
//			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
			updateInfo((view.Cell)e.getSource());
			getItems(((view.Cell)e.getSource()).getIndxX(),((view.Cell)e.getSource()).getIndxY(),
					GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			
			ResidentialBuilding currBuilding = null;
			currBuilding = buildingInCell(((Cell) e.getSource()).getIndxX(), ((Cell) e.getSource()).getIndxY());
		
			if (currBuilding != null) {
				
				GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().setSelectedTarget(currBuilding);
				
				GUI.getGame().getPanel().getTopBar().getTarget().setText("Target: Building at ("+currBuilding.getLocation().getX()
						+","+currBuilding.getLocation().getY()+")");
			}
			

		}
		
		else if (e.getSource() instanceof JButton) {
			
//			try {
//				GUI.getGame().PlaySound("sounds/Jump 1.wav").start();
//			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
			JButton currButton = (JButton) e.getSource();
			
			//----------------------------------------------------
			if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getAmbulance()) {
				AddUnitsToSelector(Ambulance.class, UnitState.IDLE, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
				
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getDiseaseControlUnit()) {
				AddUnitsToSelector(DiseaseControlUnit.class, UnitState.IDLE, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getFireTruck()) {
				AddUnitsToSelector(FireTruck.class, UnitState.IDLE, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getGasControlUnit()) {
				AddUnitsToSelector(GasControlUnit.class, UnitState.IDLE, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getEvacuator()) {
				AddUnitsToSelector(Evacuator.class, UnitState.IDLE, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			//----------------------------------------------------
			if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getAmbulance()) {
				AddUnitsToSelector(Ambulance.class, UnitState.RESPONDING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getDiseaseControlUnit()) {
				AddUnitsToSelector(DiseaseControlUnit.class, UnitState.RESPONDING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getFireTruck()) {
				AddUnitsToSelector(FireTruck.class, UnitState.RESPONDING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getGasControlUnit()) {
				AddUnitsToSelector(GasControlUnit.class, UnitState.RESPONDING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getEvacuator()) {
				AddUnitsToSelector(Evacuator.class, UnitState.RESPONDING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			//----------------------------------------------------
			if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getAmbulance()) {
				AddUnitsToSelector(Ambulance.class, UnitState.TREATING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getDiseaseControlUnit()) {
				AddUnitsToSelector(DiseaseControlUnit.class, UnitState.TREATING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getFireTruck()) {
				AddUnitsToSelector(FireTruck.class, UnitState.TREATING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getGasControlUnit()) {
				AddUnitsToSelector(GasControlUnit.class, UnitState.TREATING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
			
			else if (currButton == GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getEvacuator()) {
				AddUnitsToSelector(Evacuator.class, UnitState.TREATING, GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() instanceof Cell) {
			Cell currCell = (Cell) e.getSource();
			if (currCell.isDisaster()) {
				currCell.setImage(new ImageIcon("icons/Game panel/red_pressed.png"));
			} else if(currCell.getIndxX() ==0 && currCell.getIndxY() == 0){
				currCell.setImage(new ImageIcon("icons/Game panel/blue_pressed.png"));
			}
			else {
				currCell.setImage(new ImageIcon("icons/Game panel/grey_pressed.png"));
			}
			
		}
		
		if (e.getSource() instanceof Cell) {
			Cell currCell = (Cell) e.getSource();
			try {
				iconContent = new CellContent(getIconList(currCell));
				Point location = MouseInfo.getPointerInfo().getLocation(); 
				iconContent.setLocation(location);
				iconContent.setVisible(true);
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() instanceof Cell) {
			Cell currCell = (Cell) e.getSource();
			if (currCell.isDisaster()) {
				currCell.setImage(new ImageIcon("icons/Game panel/red.png"));
			}
			else if(currCell.getIndxX() ==0 && currCell.getIndxY() == 0) {
				currCell.setImage(new ImageIcon("icons/Game panel/blue.png"));
			}
			else {
				currCell.setImage(new ImageIcon("icons/Game panel/grey.png"));
			}
			
		}
		
		if (e.getSource() instanceof Cell) {
			iconContent.dispose();
			iconContent = null;
	}
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			if(e.getSource() == GUI.getGame().getPanel().getTopBar().getEndCycle()) {
				Button endButton=GUI.getGame().getPanel().getTopBar().getEndCycle();
				endButton.setIcon(new ImageIcon("icons/Game panel/Next Cycle1.png"));
				//endButton.setLocation(1240,17);
				//endButton.setSize(160,25);
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
			else if(e.getSource() == GUI.getGame().getPanel().getTopBar().getTreat()) {
				Button treatButton=GUI.getGame().getPanel().getTopBar().getTreat();
				treatButton.setIcon(new ImageIcon("icons/Game panel/Respond1.png"));
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
			
			else if(e.getSource() == GUI.getGame().getGameOver().getReturnButton()){
				JLabel gameOverMainMenu = GUI.getGame().getGameOver().getReturnButton();
				gameOverMainMenu.setIcon(new ImageIcon("icons/Game panel/Return 2.png"));
				
				try {
					GUI.PlaySound("sounds/bottle.wav").start();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
			else if(e.getSource()==GUI.getGame().getPanel().getTopBar().getChat()) {
				GUI.getGame().getPanel().getTopBar().getChat().setIcon(new ImageIcon("icons/Game panel/chat1.png"));
				try {
					GUI.PlaySound("sounds/Morse.wav").start();
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
		catch(Exception e1) {}	
		
	}
	
	public ArrayList<Integer> getIconList(Cell currCell) {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		
		int x = currCell.getIndxX();
		int y = currCell.getIndxY();
		ArrayList<Citizen> citizenList = ListCitizens(x, y);
		int bc = countBuildingCitizen(x, y);
		ResidentialBuilding currBuilding = buildingInCell(x, y);
		
//		if (currBuilding != null) 
//			keys.add(0);
		
//		if (bc < citizenList.size()) 
//			keys.add(2);
		
		if (currBuilding != null) {
			
//			if (bc > 0) 
//			keys.add(1);
		
		if(currBuilding.getDisaster() != null && currBuilding.getDisaster() instanceof Fire && currBuilding.getStructuralIntegrity() > 0)
			keys.add(3);
		
		if(currBuilding.getDisaster() != null && currBuilding.getDisaster() instanceof GasLeak && currBuilding.getStructuralIntegrity() > 0)
			keys.add(4);
		
		if(currBuilding.getDisaster() != null && currBuilding.getDisaster() instanceof Collapse && currBuilding.getStructuralIntegrity() > 0)
			keys.add(5);
		
		if (currBuilding.getStructuralIntegrity() <= 0) 
			keys.add(13);
		
		}
		
		for (Citizen citizen : citizenList) {
			if (citizen.getDisaster() != null) {
				if (citizen.getDisaster() instanceof Injury && citizen.getState() != CitizenState.DECEASED) {
					if (!keys.contains(6)) {
						keys.add(6);
					}
				}
				
				if (citizen.getDisaster() instanceof Infection && citizen.getState() != CitizenState.DECEASED) {
					if (!keys.contains(7)) {
						keys.add(7);
					}
				}
			}
			if (citizen.getState() == CitizenState.DECEASED) {
				if (!keys.contains(14)) {
					keys.add(14);
				}
			}
			
		}
		
		for (Unit unit : emergencyUnits) {
			if (unit.getLocation().getX() == x && unit.getLocation().getY() == y) {
				if (unit instanceof Ambulance) {
					if (!keys.contains(8)) {
						keys.add(8);
					}
				}
				
				if (unit instanceof DiseaseControlUnit) {
					if (!keys.contains(9)) {
						keys.add(9);
					}
				}
				
				if (unit instanceof FireTruck) {
					if (!keys.contains(10)) {
						keys.add(10);
					}
				}
				
				if (unit instanceof GasControlUnit) {
					if (!keys.contains(11)) {
						keys.add(11);
					}
				}
				
				if (unit instanceof Evacuator) {
					if (!keys.contains(12)) {
						keys.add(12);
					}
				}
			}
		}
		
		return keys;
		
	}
	
	public ArrayList<Citizen> ListCitizens(int x,int y) {
		ArrayList<Citizen> ret = new ArrayList<Citizen>();
		for (Citizen citizen : visibleCitizens) {
			if (citizen.getLocation().getX() == x && citizen.getLocation().getY() == y) {
				ret.add(citizen);
			}
		}
		return ret;
	}
	
	public int countBuildingCitizen(int x,int y) {
		int c = 0;
		ResidentialBuilding building = buildingInCell(x, y);
		
		for (Citizen citizen : visibleCitizens) {
			if (citizen.getLocation().getX() == x && citizen.getLocation().getY() == y && building != null &&building.getOccupants().contains(citizen))
				c++;
		}
		return c;
	}


	@Override
	public void mouseExited(MouseEvent e) {
		try {
			if(e.getSource() == GUI.getGame().getPanel().getTopBar().getEndCycle()) {
				Button endButton=GUI.getGame().getPanel().getTopBar().getEndCycle();
				endButton.setIcon(new ImageIcon("icons/Game panel/Next Cycle.png"));
				//endButton.setLocation(1200,17);
				//endButton.setSize(135,25);
			}
			else if(e.getSource() == GUI.getGame().getPanel().getTopBar().getTreat()) {
				Button treatButton=GUI.getGame().getPanel().getTopBar().getTreat();
				treatButton.setIcon(new ImageIcon("icons/Game panel/Respond.png"));
			}
			
			else if(e.getSource() == GUI.getGame().getGameOver().getReturnButton()){
				JLabel gameOverMainMenu = GUI.getGame().getGameOver().getReturnButton();
				gameOverMainMenu.setIcon(new ImageIcon("icons/Game panel/Return.png"));
			}
			else if(e.getSource()==GUI.getGame().getPanel().getTopBar().getChat()) {
				GUI.getGame().getPanel().getTopBar().getChat().setIcon(new ImageIcon("icons/Game panel/chat.png"));
			}
		}
		catch (Exception e1) {
			// TODO: handle exception
		}
			
		
	}
	public void updateLog(GameGUI game) {
		logText+="  Cycle: "+ GUI.getGame().getCurrentCycle()+"\n\n";
		//ArrayList<Citizen> toRemoveC=new ArrayList<>();
		//ArrayList<ResidentialBuilding> toRemoveB=new ArrayList<>();
		for (int i = 0; i < visibleCitizens.size(); i++) {
			
			Citizen citizen=visibleCitizens.get(i);
			if(citizen.getDisaster().isActive() && 
					citizen.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
			 logText=logText+"  "+citizen.getDisaster().toString()+"in location "+citizen.getLocation().toString()+"\n";
			}
			else if(citizen.getState()==CitizenState.DECEASED) {
				if(!deadCitizens.contains(citizen)) {
					deadCitizens.add(citizen);
					logText+="Citizen "+citizen.getName()+" in location "+citizen.getLocation().toString()+" Died"+"\n";
				}
				//toRemoveC.add(citizen);
			}
		}
		//visibleCitizens.removeAll(toRemoveC);
		for (int i=0;i<visibleBuildings.size();i++) {
			ResidentialBuilding building=visibleBuildings.get(i);
			if(building.getDisaster().isActive() && 
					building.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
				logText=logText+"  "+building.getDisaster().toString()+"in location "+building.getLocation().toString()+"\n";
			}
			else if(building.getStructuralIntegrity()<=0) {
				if(!collapsedBuildings.contains(building)) {
					collapsedBuildings.add(building);
					logText+="Building in location "+building.getLocation().toString()+" was destroyed"+"\n";
				}
				//toRemoveB.add(building);
			}
		}
	//	visibleBuildings.removeAll(toRemoveB);
		logText+="-----------------------------------\n";
		JTextArea logTextArea=game.getPanel().getMidArea().getMidWest().getTop().getLog().getTextArea();
		logTextArea.setText(logText);
		logTextArea.setSize(logTextArea.getSize().width,logTextArea.getSize().height+10);
	}
	public void updateInfo(view.Cell cell) {
		String s="";
		int x=cell.getIndxX();
		int y=cell.getIndxY();
		Citizen c=citizenInCell(x, y);
		ResidentialBuilding b=buildingInCell(x, y);
		JTextArea text=GUI.getGame().getPanel().getMidArea().getMidWest().getBottom().getInfo().getTextArea();
		if(c==null && b!=null) 
			s=b.toString();
		else if(c!=null && b!=null)
			s=b.toString();
		else if (b==null && c!=null)
			s=c.toString();
		int length=s.length();
		text.setSize((int) text.getSize().width, text.getSize().height+length*10);
		text.setText(s);
				
	}
	
	public void updateInfoSelector(Simulatable selected) {
		String s="";
		JTextArea text=GUI.getGame().getPanel().getMidArea().getMidWest().getBottom().getInfo().getTextArea();
	
		if (selected != null && selected instanceof Citizen)  {		
				s = ((Citizen) selected).toString2();
			int length=s.length();
			text.setSize((int) text.getSize().width, text.getSize().height+length*10);
		}
		
		if (selected != null && selected instanceof Unit)  {			
				s = ((Unit) selected).toString2();
			int length=s.length();
			text.setSize((int) text.getSize().width, text.getSize().height+length*10);
		}
		
		text.setText(s);
	}
	public Citizen citizenInCell(int x,int y) {
		for (Citizen citizen :visibleCitizens ) {
			if(x==citizen.getLocation().getX() && y==citizen.getLocation().getY())
				return citizen;
		}
		return null;
	}
	public ResidentialBuilding buildingInCell(int x,int y) {
		for (ResidentialBuilding building :visibleBuildings) {
			if(x==building.getLocation().getX() && y==building.getLocation().getY())
				return building;
		}
		return null;
	}

	
	public int countUnits(Class<?> cls,UnitState S) {
		int ret = 0;
		for (Unit unit : emergencyUnits) {
			if(unit.getClass() == cls && unit.getState() == S) {
				ret++;
			}
		}
		return ret;
	}
	
	public void AddUnitsToSelector(Class<?> cls,UnitState st,Selector s) {
		s.removeAllItems();
		//s.addItem(null);
		for (Unit unit : emergencyUnits) {
			if(unit.getClass() == cls && unit.getState() == st) {
				s.addItem(unit);
			}
		}
	}
	
	public void updateUnitCount(UnitState state) {
		
		if (state == UnitState.IDLE) {
			
		GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getAmbulance()
		.setText("X "+countUnits(model.units.Ambulance.class, state));
		
		GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getDiseaseControlUnit().
		setText("X "+countUnits(model.units.DiseaseControlUnit.class, state));
		
		GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getEvacuator().
		setText("X "+countUnits(model.units.Evacuator.class, state));
		
		GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getFireTruck().
		setText("X "+countUnits(model.units.FireTruck.class, state));
		
		GUI.getGame().getPanel().getMidArea().getMiddleEast().getTop().getGasControlUnit().
		setText("X "+countUnits(model.units.GasControlUnit.class, state));
		}
		
		else if (state == UnitState.RESPONDING) {
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getAmbulance()
			.setText("X "+countUnits(model.units.Ambulance.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getDiseaseControlUnit().
			setText("X "+countUnits(model.units.DiseaseControlUnit.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getEvacuator().
			setText("X "+countUnits(model.units.Evacuator.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getFireTruck().
			setText("X "+countUnits(model.units.FireTruck.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getCenter().getGasControlUnit().
			setText("X "+countUnits(model.units.GasControlUnit.class, state));
			}
		
		else if (state == UnitState.TREATING) {
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getAmbulance()
			.setText("X "+countUnits(model.units.Ambulance.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getDiseaseControlUnit().
			setText("X "+countUnits(model.units.DiseaseControlUnit.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getEvacuator().
			setText("X "+countUnits(model.units.Evacuator.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getFireTruck().
			setText("X "+countUnits(model.units.FireTruck.class, state));
			
			GUI.getGame().getPanel().getMidArea().getMiddleEast().getBottom().getGasControlUnit().
			setText("X "+countUnits(model.units.GasControlUnit.class, state));
		}
		
					
	}

	public void updatetopBar() {
		JLabel topInfo=GUI.getGame().getPanel().getTopBar().getTopInfo();
		String n="Casualties : ";
		int x=0;
		x=this.getEngine().calculateCasualties();
		n+=x;
		n+="   //   current cycle : "+GUI.getGame().getCurrentCycle();
		topInfo.setText(n);
		GUI.getGame().getPanel().getTopBar().add(topInfo);
	}
	
	public void getItems(int x,int y,Selector s){
		s.removeAllItems();
		//s.addItem(null);
		for (int i = 0; i < visibleCitizens.size(); i++) {
			if (x == visibleCitizens.get(i).getLocation().getX() && y == visibleCitizens.get(i).getLocation().getY()) {
				s.addItem(visibleCitizens.get(i));
			}
			
		}
		for (Unit unit : emergencyUnits) {
			if (unit.getLocation().getX() == x && unit.getLocation().getY() == y) {
				s.addItem(unit);
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<Simulatable> source = (JComboBox<Simulatable>) e.getSource();
			if (source.getSelectedIndex() >=0) {
				
//				try {
//					GUI.getGame().PlaySound("sounds/Confirm 1.wav").start();
//				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
				updateInfoSelector((Simulatable) source.getSelectedItem());
				Simulatable r= (Simulatable) source.getSelectedItem();
				if(r instanceof Rescuable) {
					GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().setSelectedTarget((Rescuable)r);
					
					Rescuable Target = GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().getSelectedTarget();
					if (Target instanceof Citizen) {
						GUI.getGame().getPanel().getTopBar().getTarget().setText("Target: Citizen "+((Citizen) Target).getName()+" at "+"("+Target.getLocation().getX()
								+","+Target.getLocation().getY()+")");
					}
					else {
						GUI.getGame().getPanel().getTopBar().getTarget().setText("Target: Empty");
					}
					
				}
				 if(r instanceof Unit) {
					GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().setSelectedUnit((Unit)r);
					
					Unit unit = GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector().getSelectedUnit();
					
					if (unit == null) {
						GUI.getGame().getPanel().getTopBar().getUnit().setText("Unit: Empty");
					}
					else {
						GUI.getGame().getPanel().getTopBar().getUnit().setText("Unit: "+unit.getClass().getSimpleName()+" "+unit.getUnitID()+ " at "+"("+unit.getLocation().getX()
								+","+unit.getLocation().getY()+")");
					}
					
				}
			}
			
		}
		
	}
	
	public  String[] NextMove() {
		ArrayList<Citizen> citizens = this.getVisibleCitizens();
		ArrayList<ResidentialBuilding> buildings = this.getVisibleBuildings();
		ArrayList<Unit> units = this.emergencyUnits;
		int[] score = new int[citizens.size()+buildings.size()];
		int[] time = new int[citizens.size()+buildings.size()];
		int[] canBeSaved = new int[citizens.size()+buildings.size()];
		
		Arrays.fill(canBeSaved, -1);
		
		for (int i = 0; i < citizens.size() && (citizens.get(i).getState() != CitizenState.DECEASED) ; i++) {
			Disaster currDisaster = citizens.get(i).getDisaster();
			score[i]+= 1;
			int time1 = 0;
			int time2 = 0;
			
			int bloodLoss = citizens.get(i).getBloodLoss();
			int toxicity  = citizens.get(i).getToxicity();
			
			int damage = 0;
			
			if ((bloodLoss>0 && bloodLoss<30))
				damage+= 5;
			
			if ((bloodLoss>=30 && bloodLoss<70))
				damage+= 10;
				
			if ((bloodLoss>=70 ))
				damage+= 15;
			
			if (( toxicity>0 && toxicity<30))
				damage+= 5;
			
			if ((toxicity>=30 && toxicity<70))
				damage+= 10;
				
			if ((toxicity>=70 ))
				damage+= 15;
			
			if (damage != 0) {
				time1 = (int) Math.ceil(citizens.get(i).getHp()/damage);
			}
			else {
				time1 = Integer.MAX_VALUE;
			}
			
			if (time1 <= 1) {
				time1 = 10000;
			}
			
			if (currDisaster instanceof Injury && currDisaster.isActive()) {
				time2 = (int) Math.ceil((100 - citizens.get(i).getBloodLoss())/10);
			}
			
			if (currDisaster instanceof Infection && currDisaster.isActive()) {
				time2 = (int) Math.ceil((100 - citizens.get(i).getToxicity())/15);
			}
			
			if (time2 <= 1) {
				time2 = 10000;
			}
			
			if (citizens.get(i).getDisaster() != null) {
				time[i] = Math.min(time1, time2);
			}
			else {
				time[i] = time1;
			}
			
		}
		
		for (int i = 0; i < buildings.size(); i++) {
			Disaster currDisaster = buildings.get(i).getDisaster();
			
			int time1 = 0;
			int time2 = 0;
			
			int damage = 0;
			
			int foundationDamage = buildings.get(i).getFoundationDamage();
			int fireDamage = buildings.get(i).getFireDamage();
			
			if (foundationDamage>0) {
				damage+=11;
			}
			
			if(fireDamage>0 && fireDamage<30)
				damage+= 3;
			
			if(fireDamage>=30 && fireDamage<70)
				damage+= 5;
				
			if(fireDamage>=70 && fireDamage<100)
				damage+= 7;
				
			if (damage != 0) {
				time1 = (int) Math.ceil(citizens.get(i).getHp()/damage);
			}
			else {
				time1 = Integer.MAX_VALUE;
			}
			
			if (time1 <= 1) {
				time1 = 10000;
			}	
		
			
			score[i+citizens.size()]+=  buildings.get(i).getOccupants().size(); 
			
			if (currDisaster instanceof Fire && currDisaster.isActive()) {
				time2 = (int) Math.ceil((100 - buildings.get(i).getFireDamage())/10);
			}
			
			if (currDisaster instanceof GasLeak && currDisaster.isActive()) {
				time2 = (int) Math.ceil((100 - buildings.get(i).getGasLevel())/15);
			}
			
			if (currDisaster instanceof Collapse && currDisaster.isActive()) {
				time2 = (int) Math.ceil((100 - buildings.get(i).getFoundationDamage())/10);
			}
			
			if (time2 <= 1) {
				time2 = 10000;
			}
			
			if (citizens.get(i).getDisaster() != null) {
				time[i+citizens.size()] = Math.min(time1, time2);
			}
			else {
				time[i+citizens.size()] = time1;
			}
			
		}
		
		for (int i = 0; i < time.length; i++) {
			for (int j = 0; j < units.size(); j++) {
				int x = units.get(j).getLocation().getX();
				int y = units.get(j).getLocation().getY();
				int xt = 0;
				int yt = 0;
				
				int timeu = 0;
				
				if (i >= citizens.size()) {
					xt = buildings.get(i%citizens.size()).getLocation().getX();
					yt = buildings.get(i%citizens.size()).getLocation().getY();
				}
				else {
					xt = citizens.get(i).getLocation().getX();
					yt = citizens.get(i).getLocation().getY();
				}
				
				
				int distance = (xt-x)+(yt+y);
				
				 timeu = (int) Math.ceil(distance/units.get(j).getStepsPerCycle());
				 
				 if (timeu < time[i]) {
					 if (time[i] != 10000) {
						 if (i >= citizens.size()) {
							 if (buildings.get(i-citizens.size()).getDisaster() != null) {
								if (buildings.get(i-citizens.size()).getDisaster() instanceof Collapse && buildings.get(i-citizens.size()).getDisaster().isActive() && units.get(j) instanceof Evacuator) {
									canBeSaved[i] = j;
								}
								
								if ((buildings.get(i-citizens.size()).getFireDamage() > 0 && units.get(j) instanceof FireTruck) || (buildings.get(i-citizens.size()).getGasLevel() > 0 && units.get(j) instanceof GasControlUnit)) {
									canBeSaved[i] = j;
								}
								
								if (buildings.get(i-citizens.size()).getDisaster() instanceof Fire && buildings.get(i-citizens.size()).getDisaster().isActive() && units.get(j) instanceof FireTruck) {
									canBeSaved[i] = j;
								}
								
								if (buildings.get(i-citizens.size()).getDisaster() instanceof GasLeak && buildings.get(i-citizens.size()).getDisaster().isActive() && units.get(j) instanceof GasControlUnit) {
									canBeSaved[i] = j;
								}
							}
							
						}
						 else {
							 if (citizens.get(i).getDisaster() != null) {
								if (citizens.get(i).getDisaster() instanceof Injury && citizens.get(i).getDisaster().isActive() && units.get(j) instanceof Ambulance) {
									canBeSaved[i] = j;
								}
								
								if (citizens.get(i).getDisaster() instanceof Infection && citizens.get(i).getDisaster().isActive() && units.get(j) instanceof DiseaseControlUnit) {
									canBeSaved[i] = j;
								}
								if (!citizens.get(i).getDisaster().isActive() && (citizens.get(i).getToxicity() > 0 || citizens.get(i).getBloodLoss() > 0) && units.get(i) instanceof MedicalUnit) {
									canBeSaved[i] = j;
								}
							}
							
						}
						
					}	
				}
				
			}
		}
		
		return null;
		
	}
	public boolean checkInput() {
		BufferedReader br=new BufferedReader(new InputStreamReader(server.getInput()));
		try {
			if(br.ready()) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


}
