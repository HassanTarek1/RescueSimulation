package model.units;

import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public abstract class FireUnit extends Unit{
	
//variables.	
//setters/getters.
	
//constructor(s):
	
	public FireUnit(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}

	@Override
	public void treat() {
		ResidentialBuilding X=(ResidentialBuilding)(this.getTarget());
		X.setFireDamage(X.getFireDamage()-10);
		X.setFoundationDamage(X.getFireDamage()-10);
	}
	
}
