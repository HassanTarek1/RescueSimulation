package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
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
	private WorldListener wListener;
	
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
	public void cycleStep() {
		
	}
	public void setwListener(WorldListener wListener) {
		this.wListener = wListener;
	}
	//methods
	public void respond(Rescuable r) {
		
	}

//constructor(s):
	
	

	public Unit(String id, Address location, int stepsPerCycle){
		unitID = id;
		this.location = location;
		if(wListener!=null)
			wListener.assignAddress(this, location.getX(), location.getY());
		this.stepsPerCycle = stepsPerCycle;
		state = UnitState.IDLE;
	}
	public Unit() {}
	
}
