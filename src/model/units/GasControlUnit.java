package model.units;

import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

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

	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getGasLevel()<=0) || x.getStructuralIntegrity()<=0) 
			super.jobsDone();
	}
	
}
