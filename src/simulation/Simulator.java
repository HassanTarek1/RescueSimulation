package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][]world;
	public Simulator() {
	
	}
		private String[] readFile(String path) throws IOException{
		String currentLine = "";
		String res="";
		FileReader fileReader= new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
		res=res+","+currentLine;
		// Parsing the currentLine String
		}
		return res.split(",");
		}
}
