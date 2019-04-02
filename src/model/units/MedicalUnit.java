package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public abstract class MedicalUnit extends Unit{
	
//variables:
	
	private int healingAmount = 10;
	private int treatmentAmount = 10;
	
//setters/getters.
	public int getTreatmentAmount() {
		return treatmentAmount;
	}
//constructor(s):

	public MedicalUnit(String id, Address location, int stepsPerCycle,WorldListener listener){
		super(id, location, stepsPerCycle, listener);
	}
	
	public MedicalUnit(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
//Methods
	
	public void heal() {
		Citizen X=(Citizen)(this.getTarget());
		if(X != null) {
			X.setHp(X.getHp()+healingAmount);
		}
		if(X.getHp()>=100) {
			X.setHp(100);
			jobsDone();
		}
	}
	
	public void respond(Rescuable r) {
			
			if(r!=this.getTarget() && this.getTarget()!=null) {
				Citizen currTarget=(Citizen)this.getTarget();
				super.respond(r);
				if(currTarget.getBloodLoss()<=0 && currTarget.getToxicity()<=0 && currTarget.getHp()<=100) {
					currTarget.getDisaster().setActive(false);
				}
			}
			
			if(this.getTarget() == null)
				super.respond(r);
				
	}
}
