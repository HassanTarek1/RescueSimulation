package model.units;

import model.people.Citizen;
import simulation.Address;

public abstract class MedicalUnit extends Unit{
	
//variables:
	
	private int healingAmount = 10;
	private int treatmentAmount = 10;
	
//setters/getters.
	public int getTreatmentAmount() {
		return treatmentAmount;
	}
//constructor(s):

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

	
	public void treat() {
		
	//Replaced in subclasses
		
	}	
	
}
