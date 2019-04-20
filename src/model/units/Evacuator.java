package model.units;

import java.util.ArrayList;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
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
		if(x!= null && ((x.getOccupants().isEmpty()) || x.getFoundationDamage()>=100)) {
			super.jobsDone();
			if(this.getWorldListener()!=null)
				this.getWorldListener().assignAddress(this, 0, 0);
			}
		
	}
	public void respond(Rescuable r) throws UnitException {
		if(r instanceof Citizen) {
			String message="Evacuators can only be sent to buildings";
			throw new IncompatibleTargetException(this, r, message);
		}
		if(r.getDisaster()==null) {
			throw new CannotTreatException(this,r,"The building does not have an active disaster");
		}
		if(!canTreat(r)) {
			throw new CannotTreatException(this,r, "Destroyed building");
		}
		if(!(r.getDisaster() instanceof Collapse)) {
			throw new CannotTreatException(this,r,"The building is not collapsing");
		}
		super.respond(r);
	}
	
	@Override
	public String toString() {
		return getUnitID()+" (Evacuator)";
	}
	
	public String toString2() {
		String s = "";
		s+= "Unit ID: "+this.getUnitID()+"\n";
		s+= "Unit type: Evacuator\n";
		s+= "Unit location: "+this.getLocation().getX()+","+this.getLocation().getY()+"\n";
		s+= "Steps per cycle: "+getStepsPerCycle()+"\n";
		s+= "Target:\n";
		
		if (this.getTarget() != null) {
			s+= ((ResidentialBuilding) this.getTarget()).toString();
		}
		else {
			s+= "NO TARGET\n";
		}

		s+= "Unit state: "+this.getState()+"\n";
		s+= "Number of passengers: "+this.getPassengers().size()+"\n";
		s+= "Passenger info: \n";
		
		for (Citizen currCitizen : this.getPassengers()) 
			s+= currCitizen.toString2();
		
		return s;
	}
		
}
