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

public class FireTruck extends FireUnit{

//variables.	
//setters/getters.
			
//constructor(s):
	
	public FireTruck(String id, Address location, int stepsPerCycle,WorldListener listener){
		super(id, location,  stepsPerCycle,listener);
	}
	
	public FireTruck(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
	public void treat() {
		ResidentialBuilding X=(ResidentialBuilding)(this.getTarget());
		
		if(X.getDisaster() instanceof Fire)
			X.getDisaster().setActive(false);
		
		
		X.setFireDamage(X.getFireDamage()-10);
		
		
		jobsDone();
	}
	
	public void respond(Rescuable r) throws UnitException {
		if(r instanceof Citizen) {
			String message="What are you doing. you can not extinguish a citizen";
			throw new IncompatibleTargetException(this, r, message);
		}
		if(!canTreat(r)) {
			throw new CannotTreatException(this,r, "the Building is Safe");
		}
		if(!(r.getDisaster() instanceof Fire)) {
			throw new CannotTreatException(this,r,"the Fire Unit treats only the Fire Disater");
		}
		super.respond(r);
	}

	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getFireDamage()<=0) || x.getStructuralIntegrity()<=0) 
			super.jobsDone();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getUnitID()+" (Fire truck)";
	}
	
	public String toString2() {
		String s = "";
		s+= "Unit ID: "+this.getUnitID()+"\n";
		s+= "Unit type: Fire truck\n";
		s+= "Unit location: "+this.getLocation().getX()+","+this.getLocation().getY()+"\n";
		s+= "Steps per cycle: "+getStepsPerCycle()+"\n";
		s+= "Target:\n";

		if (this.getTarget() != null) {
			s+= ((ResidentialBuilding) this.getTarget()).toString();
		}
		else {
			s+= "NO TARGET\n";
		}
		
		s+= "Unit state: "+this.getState()+"\n";
		return s;
	}
}
