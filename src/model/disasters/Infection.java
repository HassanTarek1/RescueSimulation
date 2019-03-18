package model.disasters;

import model.people.Citizen;

public class Infection extends Disaster{

	public Infection(int cycle, Citizen target) {
		super(cycle,target);
	}

	//methods
	public void strike() {
		Citizen target=(Citizen)this.getTarget();
		int oldToxicity=target.getToxicity();
		target.setToxicity(oldToxicity+25);
	}
	
	public void cycleStep() {
		Citizen target=(Citizen)this.getTarget();
		int oldToxicity=target.getToxicity();
		target.setToxicity(oldToxicity+15);
	}
}
