package model.units;

import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

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
	
	public void respond(Rescuable r) throws IncompatibleTargetException {
		if(r instanceof Citizen) {
			String message="What are you doing. you can not extinguish a citizen";
			throw new IncompatibleTargetException(this, r, message);
		}
		super.respond(r);
	}
	
}
