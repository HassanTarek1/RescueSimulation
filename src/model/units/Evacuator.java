package model.units;

import java.util.ArrayList;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
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
		//clears all the passengers at the base
		if(this.getPassengers().size() > 0 && this.getDistanceToBase()<=0){
			this.getWorldListener().assignAddress(this, 0, 0);;
			for(Citizen currCitizen : this.getPassengers()) {
				currCitizen.setState(CitizenState.RESCUED);
				if(this.getWorldListener()!=null)
					this.getWorldListener().assignAddress(currCitizen, 0, 0);
			}
			this.getPassengers().clear();
		}else {
			//filling up citizens from the building
			while(occ.size() > 0 && getPassengers().size() < getMaxCapacity()) {
				Citizen currCitizen = occ.get(0);
				occ.remove(0);
				getPassengers().add(currCitizen);
			}

		}
		
	}

	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getOccupants().isEmpty()) || x.getFoundationDamage()>=100)
			this.setState(UnitState.IDLE);
		
	}
		
}
