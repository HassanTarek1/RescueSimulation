package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit{

//variables.	
//setters/getters.
				
//constructor(s):	

	

	public Ambulance(String id, Address location, int stepsPerCycle,WorldListener listener){
		super(id, location, stepsPerCycle,listener);
	}
	
	public Ambulance(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
	public void treat() {
		
		Citizen X=(Citizen)(this.getTarget());
		
		if(X.getDisaster() instanceof Injury)
			X.getDisaster().setActive(false);
		
		if(X.getBloodLoss()>0) {
			X.setBloodLoss(X.getBloodLoss()-getTreatmentAmount());
		}
		
		if((X.getBloodLoss()<=0 && X.getToxicity()<=0) || X.getHp()<=0) {
			X.setState(CitizenState.RESCUED);super.jobsDone();
		}
		
		if(X.getBloodLoss()<=0)
			this.heal();
	}

	public void jobsDone() {
		Citizen x = (Citizen)this.getTarget();
		if((x.getBloodLoss()<=0 && x.getHp()>=100) || x.getState()==CitizenState.DECEASED) 
			super.jobsDone();
	}
	
	public void respond(Rescuable r) throws UnitException {
		if(r instanceof ResidentialBuilding) {
			String message="What are you doing. you can not heal a building";
			throw new IncompatibleTargetException(this, r, message);
		}
		if(r.getDisaster()==null) {
			throw new CannotTreatException(this,r,"the Citizen is Safe");
		}
		if(!canTreat(r)) {
			throw new CannotTreatException(this,r, "The Citizen Already Dead");
		}
		if(!(r.getDisaster() instanceof Injury)) {
			throw new CannotTreatException(this,r,"the Disease Unit traets only the Injury");
		}
		super.respond(r);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getUnitID()+" (Ambulance)";
	}
	
	public String toString2() {
		String s = "";
		s+= "Unit ID: "+this.getUnitID()+"\n";
		s+= "Unit type: Ambulance\n";
		s+= "Unit location: "+this.getLocation().getX()+","+this.getLocation().getY()+"\n";
		s+= "Steps per cycle: "+getStepsPerCycle()+"\n";
		s+= "Target:\n";
		
		if (this.getTarget() != null) {
			s+= ((Citizen) this.getTarget()).toString2();
		}
		else {
			s+= "NO TARGET\n";
		}
		
		s+= "Unit state: "+this.getState()+"\n";
		return s;
	}
	
}
