package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit{

//variables.	
//setters/getters.
					
//constructor(s):
	
	public GasControlUnit(String id, Address location, int stepsPerCycle, WorldListener listener){
		super(id, location,  stepsPerCycle,listener);
	}
	
	public GasControlUnit(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			super.jobsDone();
			return;
		} else if (target.getGasLevel() > 0) 
			target.setGasLevel(target.getGasLevel() - 10);

		if (target.getGasLevel() == 0)
			super.jobsDone();
	}

	public void respond(Rescuable r) throws UnitException {
		if(r instanceof Citizen) {
			String message="What are you doing. you can not extinguish a citizen";
			throw new IncompatibleTargetException(this, r, message);
		}
		if(!canTreat(r)) {
			throw new CannotTreatException(this,r, "the Building is Safe");
		}
		if(!(r.getDisaster() instanceof GasLeak)) {
			throw new CannotTreatException(this,r,"the Fire Unit treats only GasLeak Disaster");
		}
		super.respond(r);
	}
	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getGasLevel()<=0) || x.getStructuralIntegrity()<=0) 
			super.jobsDone();
	}
	
}
