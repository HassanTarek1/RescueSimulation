package controller;


import java.util.ArrayList;
import javax.swing.ImageIcon;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import view.GameGUI;
import view.MainMenu;

public class CommandCenter implements SOSListener{
	private Simulator engine;
	private MainMenu GUI;
	
	private ArrayList<ResidentialBuilding> visibleBuildings;
	
	private ArrayList<Citizen> visibleCitizens;
	
	private ArrayList<Unit> emergencyUnits;
	
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<>();
		visibleCitizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
		GUI = new MainMenu(this);
		GameGUI game=GUI.getGame();
		updateCitizens(game);
		updateBuildings(game);
		
		
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
	private void updateCitizens(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (Citizen citizen : visibleCitizens) {
			int xC=citizen.getLocation().getX();
			int yC=citizen.getLocation().getY();
			view.Cell cell=cells[xC][yC];
			cell.setImage(new ImageIcon("icons/Game panel/citizen.png"));
		}
	}
	private void updateBuildings(GameGUI game) {
		view.Cell[][] cells=game.getPanel().getMidArea().getMidGrid().getCells();
		for (ResidentialBuilding building : visibleBuildings) {
			int xC=building.getLocation().getX();
			int yC=building.getLocation().getY();
			view.Cell cell=cells[xC][yC];
			cell.setImage(new ImageIcon("icons/Game panel/building.png"));
		}
	}


	
}
