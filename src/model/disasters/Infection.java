package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;
import model.people.CitizenState;

public class Infection extends Disaster{

	public Infection(int cycle, Citizen target) {
		super(cycle,target);
	}

	//methods
	public void strike() throws CitizenAlreadyDeadException {
		Citizen target=(Citizen)this.getTarget();
		if(target.getState()==CitizenState.DECEASED) {
			String message ="The Citizen is Already Dead";
			throw new CitizenAlreadyDeadException(this,message);
		}
		target.struckBy(this);
		this.setActive(true);
		int oldToxicity=target.getToxicity();
		target.setToxicity(oldToxicity+25);
	}
	
	public void cycleStep() {
		Citizen target=(Citizen)this.getTarget();
		int oldToxicity=target.getToxicity();
		target.setToxicity(oldToxicity+15);
	}
	public String toString() {
		return "Infection on Citizen in location "+ this.getTarget().getLocation();
	}
}
