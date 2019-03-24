package model.units;

import model.disasters.Fire;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit{

//variables.	
//setters/getters.
			
//constructor(s):
	
	public FireTruck(String id, Address location, int stepsPerCycle,WorldListener listener){
		super(id, location, listener, stepsPerCycle);
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

	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getFireDamage()<=0) || x.getStructuralIntegrity()<=0) {
			this.setState(UnitState.IDLE);
			WorldListener listener=this.getWorldListener();
			super.jobsDone();
			if(listener!=null)
				listener.assignAddress(this, 0, 0);
		}			
	}		
}
