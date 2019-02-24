package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.disasters.Disaster;
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
	private Address[][]world;
	public Simulator() throws  IOException {
	buildings=new ArrayList<ResidentialBuilding>	();
	citizens=new  ArrayList<Citizen>();
	emergencyUnits=new ArrayList<Unit> ();
	plannedDisasters=new ArrayList<Disaster>() ;
	
	
	}
	private void loadUnits(String filePath) throws IOException{
		String currentLine = "";
		ArrayList<ArrayList<String>> res=new ArrayList<>();
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
		// Parsing the currentLine String
			String[] tmp=currentLine.split(",");
			if(tmp.length==4) 
				this.emergencyUnits.add(new Evacuator(tmp[1],new Address(0,0),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3])));
			else {
				switch(tmp[0]) {
				case "AMP":	this.emergencyUnits.add(new Ambulance(tmp[1],new Address(0,0),Integer.parseInt(tmp[2])));break;
				case "DCU":this.emergencyUnits.add(new DiseaseControlUnit(tmp[1],new Address(0,0),Integer.parseInt(tmp[2])));break;
				case "FTK":this.emergencyUnits.add(new FireTruck(tmp[1],new Address(0,0),Integer.parseInt(tmp[2])));break;
				case "GCU":this.emergencyUnits.add(new GasControlUnit(tmp[1],new Address(0,0),Integer.parseInt(tmp[2])));break;
				default:break;
				}
			}
		}
		}
	private void loadDisasters(String filePath) throws IOException {
		String currentLine = "";
		ArrayList<ArrayList<String>> res=new ArrayList<>();
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
		// Parsing the currentLine String
			String[] tmp=currentLine.split(",");
			
		}
	}
}
