package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import View.Game;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;

public class CommandCenter implements SOSListener,MouseListener{
	private Simulator engine;
	private Game GUI;
	
	private ArrayList<ResidentialBuilding> visibleBuildings;
	
	private ArrayList<Citizen> visibleCitizens;
	
	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;
	
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<>();
		visibleCitizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
		GUI = new Game();
		
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


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
