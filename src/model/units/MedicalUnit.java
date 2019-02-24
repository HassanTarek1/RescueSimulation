package model.units;

import simulation.Address;

public abstract class MedicalUnit extends Unit{
	
//variables:
	
	private int healingAmount = 10;
	private int treatmentAmount = 10;
	
//setters/getters.
	
//constructor(s):

	MedicalUnit(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
}
