package model.units;

import java.util.ArrayList;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class Evacuator extends PoliceUnit{
	
//variables.	
//setters/getters.
		
//constructor(s):
	
	public Evacuator(String id, Address location, int stepsPerCycle,WorldListener listener,int maxCapacity){
		
		super(id, location, stepsPerCycle, listener, maxCapacity);
	}
	
public Evacuator(String id, Address location, int stepsPerCycle,int maxCapacity){
		
		super(id, location, stepsPerCycle, maxCapacity);
	}

	public void treat() {
		ArrayList<Citizen> occ = ((ResidentialBuilding)this.getTarget()).getOccupants();
			ResidentialBuilding building=(ResidentialBuilding)this.getTarget();
			while(occ.size() > 0 && getPassengers().size() < getMaxCapacity() && 
					building.getStructuralIntegrity()>0) {
				Citizen currCitizen = occ.get(0);
				if(currCitizen.getState()!=CitizenState.DECEASED) {
					occ.remove(0);
					getPassengers().add(currCitizen);
				}
			}

	}
	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getOccupants().isEmpty()) || x.getFoundationDamage()>=100) {
			super.jobsDone();
			if(this.getWorldListener()!=null)
				this.getWorldListener().assignAddress(this, 0, 0);
			}
		
	}
		
}
