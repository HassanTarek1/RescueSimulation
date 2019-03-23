package model.units;

import model.disasters.Fire;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit{

//variables.	
//setters/getters.
			
//constructor(s):
	
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
		ResidentialBuilding X = (ResidentialBuilding) this.getTarget();
		
		if((X.getFireDamage() <= 0) || X.getStructuralIntegrity() <= 0) {
			this.setState(UnitState.IDLE);
			
		}
		
		
	}
	
}
