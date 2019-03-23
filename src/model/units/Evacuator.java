package model.units;

import java.util.ArrayList;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;

public class Evacuator extends PoliceUnit{
	
//variables.	
//setters/getters.
		
//constructor(s):
	
	public Evacuator(String id, Address location, int stepsPerCycle, int maxCapacity){
		
		super(id, location, stepsPerCycle, maxCapacity);
	}
	
	public void treat() {
		ArrayList<Citizen> occ = ((ResidentialBuilding)this.getTarget()).getOccupants();
		
		int i = 0;
		
		while(occ.size() > 0 && getPassengers().size() < getMaxCapacity()) {
			Citizen currCitizen = occ.get(i);
			occ.remove(i);
			getPassengers().add(currCitizen);
			i++;
		}
	}

	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getOccupants().isEmpty()) || x.getFoundationDamage()>=100)
			this.setState(UnitState.IDLE);
		
	}
		
}
