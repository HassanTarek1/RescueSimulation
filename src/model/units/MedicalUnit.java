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
		if(X.getHp()<100) {
			X.setHp(X.getHp()+healingAmount);
		}
	}
	public void respond(Rescuable r) {
			super.respond(r);
			Citizen x=((Citizen)r);
			if(x.getBloodLoss()<=0 && x.getToxicity()<=0 && x.getHp()<=100) {
				if(x.getDisaster() != null)
					x.getDisaster().setActive(false);
			}
		
	}
}
