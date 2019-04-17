package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.DisasterException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulatable;
import simulation.Simulator;
import view.Button;
import view.Cell;
import view.GameGUI;
import view.MainMenu;
import view.Selector;

public class CommandCenter implements SOSListener, MouseListener,ActionListener {
	private Simulator engine;
	private MainMenu GUI;
	
	private ArrayList<ResidentialBuilding> visibleBuildings;
	
	private ArrayList<Citizen> visibleCitizens;
	
	private ArrayList<Unit> emergencyUnits;
	private String logText="";
	
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		GUI = new MainMenu(this);
		updatetopBar();
		
		
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
	public void updateCitizens(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (Citizen citizen : visibleCitizens) {
			int xC=citizen.getLocation().getX();
			int yC=citizen.getLocation().getY();
			view.Cell cell=cells[xC][yC];
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
				
			} catch (BuildingAlreadyCollapsedException e1) {
				// TODO Auto-generated catch block
				
			} catch (DisasterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() instanceof view.Cell) {
			
			updateInfo((view.Cell)e.getSource());
			getItems(((view.Cell)e.getSource()).getIndxX(),((view.Cell)e.getSource()).getIndxY(),
					GUI.getGame().getPanel().getMidArea().getMiddleEast().getSelector());
			
			
		}
		
		else if (e.getSource() instanceof JButton) {
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
			currCell.setImage(new ImageIcon("icons/Game panel/green_pressed.png"));
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() instanceof Cell) {
			Cell currCell = (Cell) e.getSource();
			currCell.setImage(new ImageIcon("icons/Game panel/green.png"));
		}
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
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
		
		if(e.getSource() == GUI.getGame().getPanel().getTopBar().getEndCycle()) {
			Button endButton=GUI.getGame().getPanel().getTopBar().getEndCycle();
			endButton.setIcon(new ImageIcon("icons/Game panel/endCycle.png"));
			endButton.setLocation(1150,17);
			endButton.setSize(135,25);
		}
	}
	public void updateLog(GameGUI game) {
		logText+="  Cycle: "+ GUI.getGame().getCurrentCycle()+"\n\n";

		for (Citizen citizen : visibleCitizens) {
			if(citizen.getDisaster().isActive() && 
					citizen.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
			 logText=logText+"  "+citizen.getDisaster().toString()+"in location "+citizen.getLocation().toString()+"\n";
			}
		}
		for (ResidentialBuilding building: visibleBuildings) {
			if(building.getDisaster().isActive() && 
					building.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
				logText=logText+"  "+building.getDisaster().toString()+"in location "+building.getLocation().toString()+"\n";
			}
		}
		logText+="------------------------------------------"+"\n";
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
		s.addItem(null);
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
		s.addItem(null);
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
			if (source.getSelectedIndex() >0) {
				updateInfoSelector((Simulatable) source.getSelectedItem());
			}
			
		}
		
	}


}
