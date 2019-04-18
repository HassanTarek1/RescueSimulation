package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
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
	
	public void heal() throws NullPointerException {
		Citizen target = (Citizen)getTarget();
		if(target != null) {
		if(target.getHp()<100)
			target.setHp(target.getHp()+healingAmount);
		
		
		if(target.getHp() == 100)	
			jobsDone();
		}
	}
	
	public void respond(Rescuable r) throws UnitException{
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
