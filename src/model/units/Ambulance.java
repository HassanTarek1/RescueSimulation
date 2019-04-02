package model.units;

import model.disasters.Injury;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

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
		
		if(X.getBloodLoss()<=0 && X.getToxicity()<=0) 
			X.setState(CitizenState.RESCUED);
		
		if(X.getBloodLoss()<=0)
			this.heal();
		
	}

	public void jobsDone() {
		Citizen x = (Citizen)this.getTarget();
		if((x.getBloodLoss()<=0 && x.getHp()>=100) || x.getState()==CitizenState.DECEASED) 
			super.jobsDone();
	}
}
