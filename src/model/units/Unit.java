package model.units;

import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

 public abstract class Unit implements Simulatable,SOSResponder{
	 
//variables:
	 
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;
	
//setters/getters:
	
	public String getUnitID() {
		return unitID;
	}
	
	public UnitState getState() {
		return state;
	}
	
	public void setState(UnitState state) {
		this.state = state;
	}
	
	public Address getLocation() {
		return location;
	}
	
	public void setLocation(Address location) {
		this.location = location;
	}
	
	public Rescuable getTarget() {
		return target;
	}
	
	public int getStepsPerCycle() {
		return stepsPerCycle;
	}
	
	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	//methods
	public void cycleStep() {
		
		switch (state) {
		case RESPONDING: setDistanceToTarget(distanceToTarget - getStepsPerCycle());break;
		case TREATING: treat();break;
		default: break;
		}
					
		//Police Units
		
		if(this instanceof Evacuator) {
			
		}
		
		
		if(state == UnitState.RESPONDING && distanceToTarget <= 0) {
			this.setState(UnitState.TREATING);
			this.setLocation(this.getTarget().getLocation());
		}
		
		
	}
	
	public void jobsDone() {
		
		//Replaced in subclasses
	}
	
	public void respond(Rescuable r) {
		//TODO
	}
	
	public void treat() {
		//implemented in relevant subclasses
		//MedicalUnit and FireUnit and PoliceUnit
	}

//constructor(s):
	public Unit(String id, Address location, int stepsPerCycle){
		unitID = id;
		this.location = location;
		if(worldListener!=null)
			worldListener.assignAddress(this, location.getX(), location.getY());
		this.stepsPerCycle = stepsPerCycle;
		state = UnitState.IDLE;
	}
	public Unit() {}
	
}
