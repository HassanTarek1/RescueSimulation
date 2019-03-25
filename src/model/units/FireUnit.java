package model.units;

import model.events.WorldListener;
import simulation.Address;

public abstract class FireUnit extends Unit{
	
//variables.	
//setters/getters.
	
//constructor(s):
	
	public FireUnit(String id, Address location,int stepsPerCycle,WorldListener listener){
		super(id, location, stepsPerCycle, listener);
	}
		
	public FireUnit(String id, Address location,int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
}
