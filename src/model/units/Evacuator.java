package model.units;

import java.util.ArrayList;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

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
				occ.remove(0);
				getPassengers().add(currCitizen);
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
	public void respond(Rescuable r) throws UnitException {
		if(r instanceof Citizen) {
			String message="What are you doing. you can not evacuate a citizen";
			throw new IncompatibleTargetException(this, r, message);
		}
		if(!canTreat(r)) {
			throw new CannotTreatException(this,r,"the Building is Safe");
		}
		super.respond(r);
	}
		
}
