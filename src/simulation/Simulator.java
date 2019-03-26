package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
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

public class Simulator implements WorldListener{
	
	private int currentCycle;
	
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][]world ;
	private SOSListener emergencyService;
	
	//constructor:
	public Simulator() {
		
	}
	public Simulator(SOSListener emergencyService) throws Exception {
		
		currentCycle = 0;
		
		this.emergencyService = emergencyService;
		
		world= new Address[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) 
				world[i][j] = new Address(i, j);
		}
		
		buildings = new ArrayList<>();
		citizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
		plannedDisasters = new ArrayList<>();
		executedDisasters = new ArrayList<>();
		
		this.loadBuildings("buildings.csv");
		this.loadCitizens("citizens.csv");
		this.loadDisasters("disasters.csv");
		this.loadUnits("units.csv");
		
		for(Citizen currCitizen : citizens) {
			currCitizen.setWorldListener(this);
			currCitizen.setEmergencyService(emergencyService);
		}
		
		for(Unit currUnit : emergencyUnits) {
			currUnit.setWorldListener(this);
		}
		
		for(ResidentialBuilding currBuilding : buildings) {
			currBuilding.setEmergencyService(emergencyService);
		}
		
	}
	//setters/getters:
	
	public ArrayList<Unit> getEmergencyUnits(){
		return emergencyUnits;
	}
	
	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}
	
	
	//methods:
	
	private void loadUnits(String filePath) throws Exception{
		String currentLine = "";

		FileReader fileReader= new FileReader(filePath);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] tmp=currentLine.split(",");
			if(tmp.length==4) 
				this.emergencyUnits.add(new Evacuator(tmp[1],world[0][0],Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3])));
			else {
				switch(tmp[0]) {
				case "AMB":	this.emergencyUnits.add(new Ambulance(tmp[1],world[0][0],Integer.parseInt(tmp[2])));break;
				case "DCU": this.emergencyUnits.add(new DiseaseControlUnit(tmp[1],world[0][0],Integer.parseInt(tmp[2])));break;
				case "FTK": this.emergencyUnits.add(new FireTruck(tmp[1],world[0][0],Integer.parseInt(tmp[2])));break;
				case "GCU": this.emergencyUnits.add(new GasControlUnit(tmp[1],world[0][0],Integer.parseInt(tmp[2])));break;
				default:break;
				}
			}
		}
	}
	private void loadDisasters(String filePath) throws Exception {
		String currentLine = "";
		FileReader fileReader= new FileReader(filePath);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] tmp=currentLine.split(",");
			switch(tmp[1]) {
				case "INJ": this.plannedDisasters.add(new Injury(Integer.parseInt(tmp[0]),this.findCitizen(tmp[2])));break;
				case "INF": this.plannedDisasters.add(new Infection(Integer.parseInt(tmp[0]),this.findCitizen(tmp[2])));break;
				case "FIR": this.plannedDisasters.add(new Fire(Integer.parseInt(tmp[0]),this.findBuilding(Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3]))));break;
				case "GLK": this.plannedDisasters.add(new GasLeak(Integer.parseInt(tmp[0]),this.findBuilding(Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3]))));break;
			}
			}
	}
	private void loadBuildings(String filePath) throws Exception{
		String currline = "";
		FileReader fileReader= new FileReader(filePath);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		while ((currline = br.readLine()) != null) {
			String[] values = currline.split(",");
			Address a = world[Integer.parseInt(values[0])][Integer.parseInt(values[1])];
			buildings.add(new ResidentialBuilding(a));
		}
	}
	private void loadCitizens(String filePath) throws Exception{
		String currline = "";
		FileReader fileReader= new FileReader(filePath);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fileReader);
		while ((currline = br.readLine()) != null) {
			String[] values = currline.split(",");
			Address a = world[Integer.parseInt(values[0])][Integer.parseInt(values[1])];
			Citizen c = new Citizen(a, values[2], values[3], Integer.parseInt(values[4]));
			citizens.add(c);
			ResidentialBuilding b = findBuilding(a.getX(), a.getY());
			if(b != null)
				b.getOccupants().add(c);
		}
	}
	private Citizen findCitizen(String id) {
		for (int i = 0; i < this.citizens.size(); i++) {
			if(citizens.get(i).getNationalID().equals(id))
				return citizens.get(i);
		}
		return null;
	}
	private ResidentialBuilding findBuilding(int x,int y) {
		for (int i = 0; i < this.buildings.size(); i++) {
			if(buildings.get(i).getLocation().getX()==x && buildings.get(i).getLocation().getY()==y )
				return buildings.get(i);
		}
		return null;
	}

	public void assignAddress(Simulatable sim, int x, int y) {
		Address newAddress=world[x][y];
		if(sim instanceof Citizen ) {
			Citizen tmp=(Citizen)sim;
			tmp.setLocation(newAddress);
		}else {
			if(sim instanceof Unit) {
				Unit tmp=(Unit)sim;
				tmp.setLocation(newAddress);
			}
		}
			
		
	}
	public boolean checkGameOver() {
		if(plannedDisasters.size() == 0 && NoActiveDisasters() && IdleUnits())
			return true;
		return false;
	}
	
	private boolean NoActiveDisasters() {
		for (Disaster disaster : executedDisasters) {
			if(disaster.isActive())
				return false;
		}
	
		for(Citizen currCitizen : citizens) {
			if(currCitizen.getDisaster() != null && currCitizen.getDisaster().isActive() == true)
				return false;
		}
		
		for(ResidentialBuilding currBuilding : buildings) {
			if(currBuilding.getDisaster() != null && currBuilding.getDisaster().isActive() == true)
				return false;
		}

		return true;
		
	}
	private boolean IdleUnits() {
		for(Unit currUnit : emergencyUnits)
			if(currUnit.getState() != UnitState.IDLE) return false;
		return true;
	}
	
	public int calculateCasualties() {
		int Dead = 0;
		for(Citizen currCitizen : citizens) {
			if(currCitizen.getState() == CitizenState.DECEASED)
				Dead++;
		}
		return Dead;
	}
	
	public void nextCycle() {
		currentCycle++;
		for (int i = 0 ; i < plannedDisasters.size() ; i++) {
			Disaster currDisaster = plannedDisasters.get(i);
			
			if(currDisaster.getStartCycle() == currentCycle) {
				plannedDisasters.remove(i);
				executedDisasters.add(currDisaster);
				
				Rescuable target = currDisaster.getTarget();
				
				
				
				if(target instanceof ResidentialBuilding) {
					
					if (currDisaster instanceof Fire) {
						int Gaslvl =  ((ResidentialBuilding) target).getGasLevel();
						
						if(Gaslvl <= 0) 
							currDisaster.strike();
						
						else if(Gaslvl > 0 && Gaslvl < 70) {
							Collapse currDisaster1 = new Collapse(currentCycle, (ResidentialBuilding) target);
							currDisaster1.strike();
							RemoveDisaters((ResidentialBuilding)target);
							executedDisasters.add(currDisaster1);
							((ResidentialBuilding) target).setFireDamage(0);
						}
						
						else if(Gaslvl >= 70) {
							((ResidentialBuilding) target).setStructuralIntegrity(0);
							for (Citizen citizen : ((ResidentialBuilding)target).getOccupants()) {
								citizen.setState(CitizenState.DECEASED);
							}
						}	
					}
					
					else if (currDisaster instanceof GasLeak) {
						if(((ResidentialBuilding) target).getFireDamage() > 0) {
							Collapse currDisaster1 = new Collapse(currentCycle, (ResidentialBuilding) target);
							currDisaster1.strike();
							RemoveDisaters((ResidentialBuilding)target);
							executedDisasters.add(currDisaster1);
							((ResidentialBuilding) target).setFireDamage(0);
						}
						else
							currDisaster.strike();
					}
					
					else if (currDisaster instanceof Collapse) {
						RemoveDisaters((ResidentialBuilding) target);
						currDisaster.strike();
						((ResidentialBuilding) target).setFireDamage(0);
					}
					else {
						currDisaster.strike();
					}
					
				}
				if(target instanceof Citizen)
					currDisaster.strike();
			}
		}
		
		for(ResidentialBuilding currBuilding : buildings) {
			if(currBuilding.getFireDamage() >= 100) {
				RemoveDisaters(currBuilding);
				currBuilding.setFireDamage(0);
				Collapse tempCollapse = new Collapse(currentCycle, currBuilding);
				tempCollapse.strike();
				executedDisasters.add(tempCollapse);
				
			}
		}
		for(Unit currUnit : emergencyUnits) {
			currUnit.cycleStep();
		}
		
		
		for(Disaster currDisaster : executedDisasters) {
			if(currDisaster.getStartCycle() != currentCycle && currDisaster.isActive())
				currDisaster.cycleStep();
		}
		
		for(ResidentialBuilding currBuilding : buildings) 
			currBuilding.cycleStep();
		
		for(Citizen currCitizen : citizens) 
			currCitizen.cycleStep();
		
		
	}
	
	private void RemoveDisaters(ResidentialBuilding currBuilding) {
		for (int i = 0; i < executedDisasters.size(); i++) {
			if(executedDisasters.get(i).getTarget() == currBuilding)
				executedDisasters.remove(i);
		}
	}

}
