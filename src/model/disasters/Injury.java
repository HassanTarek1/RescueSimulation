package model.disasters;

import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;
import model.people.CitizenState;

public class Injury extends Disaster{
	public Injury(int cycle, Citizen target) {
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
		int oldBlood=target.getBloodLoss();
		target.setBloodLoss(oldBlood+30);
	}
	public void cycleStep() {
		Citizen target=(Citizen)this.getTarget();
		int oldBlood=target.getBloodLoss();
		target.setBloodLoss(oldBlood+10);

	}
	public String toString() {
		return "Injury on Citizen ";
	}
}
