package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.disasters.Fire;
import model.disasters.GasLeak;
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
	
	public void respond(Rescuable r) throws UnitException {
		super.respond(r);
	}
	
}
