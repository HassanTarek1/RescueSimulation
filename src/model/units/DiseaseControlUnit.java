package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Infection;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit{

//variables.	
//setters/getters.
				
//constructor(s):	
	
	public DiseaseControlUnit(String id, Address location, int stepsPerCycle,WorldListener listener){
		super(id, location,  stepsPerCycle,listener);
	}
	
	public DiseaseControlUnit(String id, Address location, int stepsPerCycle){
		super(id, location,  stepsPerCycle);
	}
	
	public void treat() {
		
		Citizen X=(Citizen)(this.getTarget());
		
		if(X.getDisaster() instanceof Infection)
			X.getDisaster().setActive(false);
		
		if(X.getToxicity() >0) {
			X.setToxicity(X.getToxicity()-getTreatmentAmount());
		}
		else if(X.getToxicity() <= 0 && X.getHp() < 100) {
			this.heal();
		}
		
		if(X.getBloodLoss()==0 && X.getToxicity()==0) {
			X.setState(CitizenState.RESCUED);
		}
		if(X.getToxicity()<=0)
			this.heal();	
	}

	
	public void jobsDone() {
		Citizen x = (Citizen)this.getTarget();
		if((x.getToxicity()<=0 && x.getHp()>=100) || x.getState()==CitizenState.DECEASED) 
			super.jobsDone();
	}
	
	public void respond(Rescuable r) throws UnitException {
		if(r instanceof ResidentialBuilding) {
			String message="What are you doing. you can not heal a building";
			throw new IncompatibleTargetException(this, r, message);
		}
		if(!canTreat(r) || r.getDisaster()==null) {
			throw new CannotTreatException(this,r,"the Citizen is Safe");
		}
		if(!(r.getDisaster() instanceof Infection)) {
			throw new CannotTreatException(this,r,"the Disease Unit traets only the infection");
		}
		super.respond(r);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getUnitID()+" (Disease control)";
	}
	
	public String toString2() {
		String s = "";
		s+= "Unit ID: "+this.getUnitID()+"\n";
		s+= "Unit type: Disease control unit\n";
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
