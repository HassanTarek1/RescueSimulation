package controller;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.hamcrest.core.Is;

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
import view.MiniFrame;
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
				
				Random ran = new Random();
				int x = ran.nextInt(5);
				GUI.getGame().PlaySound(GUI.getGame().getEndCycleSound()[x]).start();
				
			} catch (DisasterException e1) {
				// TODO Auto-generated catch block
				new MiniFrame(e1.getMessage());
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
			//System.out.println(selected.toString());
			//TODO something here
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
					}
		catch(Exception e1) {}
		
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
		}
		catch (Exception e1) {
			// TODO: handle exception
		}
	}
	public void updateLog(GameGUI game) {
		logText+="  Cycle: "+ GUI.getGame().getCurrentCycle()+"\n\n";
		ArrayList<Citizen> toRemoveC=new ArrayList<>();
		ArrayList<ResidentialBuilding> toRemoveB=new ArrayList<>();
		for (int i = 0; i < visibleCitizens.size(); i++) {
			
			Citizen citizen=visibleCitizens.get(i);
			if(citizen.getDisaster().isActive() && 
					citizen.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
			 logText=logText+"  "+citizen.getDisaster().toString()+"in location "+citizen.getLocation().toString()+"\n";
			}
			else if(citizen.getState()==CitizenState.DECEASED) {
				logText+="Citizen "+citizen.getName()+" in location "+citizen.getLocation().toString()+" Died"+"\n";
				toRemoveC.add(citizen);
			}
		}
		visibleCitizens.removeAll(toRemoveC);
		for (int i=0;i<visibleBuildings.size();i++) {
			ResidentialBuilding building=visibleBuildings.get(i);
			if(building.getDisaster().isActive() && 
					building.getDisaster().getStartCycle()==GUI.getGame().getCurrentCycle()) {
				logText=logText+"  "+building.getDisaster().toString()+"in location "+building.getLocation().toString()+"\n";
			}
			else if(building.getStructuralIntegrity()<=0) {
				logText+="Building in location "+building.getLocation().toString()+" was destroyed"+"\n";
				toRemoveB.add(building);
			}
		}
		visibleBuildings.removeAll(toRemoveB);
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
//		for(int j = 0; j < visibleBuildings.size(); j++) {
//			if (x == visibleBuildings.get(j).getLocation().getX() && y == visibleBuildings.get(j).getLocation().getY()) {
//				s.addItem(visibleBuildings.get(j));
//			}
//		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox<Simulatable> source = (JComboBox<Simulatable>) e.getSource();
			if (source.getSelectedIndex() >0) {
				
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
				else if(r instanceof Unit) {
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


}
