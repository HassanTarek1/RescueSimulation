package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;

public class Simulator {
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][]world = new Address[10][10];
	
	public Simulator() throws Exception {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				world[i][j] = new Address(i, j);
			}
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
		
		
	}
	private void loadUnits(String filePath) throws Exception{
		String currentLine = "";

		FileReader fileReader= new FileReader(filePath);
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
}
