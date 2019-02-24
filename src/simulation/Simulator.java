package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;

public class Simulator {
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][]world = new Address[10][10];
	public Simulator() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				world[i][j] = new Address(i, j);
			}
		}
	}
	
	private void loadBuildings(String filePath) throws IOException{
		String currline = "";
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currline = br.readLine()) != null) {
			String[] values = currline.split(",");
			Address a = new Address(Integer.parseInt(values[0]),Integer.parseInt(values[1]));
			buildings.add(new ResidentialBuilding(a));
		}
	}
	
	private void loadCitizens(String filePath) throws IOException{
		String currline = "";
		FileReader fileReader= new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currline = br.readLine()) != null) {
			String[] values = currline.split(",");
			Address a = new Address(Integer.parseInt(values[0]),Integer.parseInt(values[1]));
			citizens.add(new Citizen(a, values[2], values[3], Integer.parseInt(values[4])));
		}
	}
}
