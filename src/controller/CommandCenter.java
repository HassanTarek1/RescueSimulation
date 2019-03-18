package controller;

import java.util.ArrayList;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;

public class CommandCenter implements SOSListener{
	private Simulator engine;
	
	private ArrayList<ResidentialBuilding> visibleBuildings;
	
	private ArrayList<Citizen> visibleCitizens;
	
	private ArrayList<Unit> emergencyUnits;
	
	public CommandCenter() throws Exception {
		engine = new Simulator();
		visibleBuildings = new ArrayList<>();
		visibleCitizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
	}

	
	public void receiveSOSCall(Rescuable r) {
		
		
	}
}
