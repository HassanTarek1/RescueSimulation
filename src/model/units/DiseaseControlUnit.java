package model.units;

import model.disasters.Infection;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class DiseaseControlUnit extends MedicalUnit{

//variables.	
//setters/getters.
				
//constructor(s):	
	
	public DiseaseControlUnit(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
	public void treat() {
		
		Citizen X=(Citizen)(this.getTarget());
		
		if(X.getDisaster() instanceof Infection)
			X.getDisaster().setActive(false);
		
		if(X.getToxicity() >0) {
			X.setToxicity(X.getToxicity()-getTreatmentAmount());
		}
		
		if(X.getBloodLoss()==0 && X.getToxicity()==0) {
			X.setState(CitizenState.RESCUED);
		}
		
		if(X.getToxicity() <= 0 && X.getHp() < 100) {
			this.heal();
		}
		
		jobsDone();
		
	}
	
	public void jobsDone() {
		Citizen X = (Citizen) this.getTarget();
		
		if((X.getToxicity() <= 0 && X.getHp() >= 100) || X.getState() == CitizenState.DECEASED) {
			this.setState(UnitState.IDLE);
		}
	}

}
