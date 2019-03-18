package model.disasters;

import model.people.Citizen;

public class Injury extends Disaster{
	public Injury(int cycle, Citizen target) {
		super(cycle,target);
	}

	//methods 
	public void strike() {
		Citizen target=(Citizen)this.getTarget();
		int oldBlood=target.getBloodLoss();
		target.setBloodLoss(oldBlood+30);
	}
	public void cycleStep() {
		Citizen target=(Citizen)this.getTarget();
		int oldBlood=target.getBloodLoss();
		target.setBloodLoss(oldBlood+10);

	}
}
