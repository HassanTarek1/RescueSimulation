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
		
		if(this instanceof Evacuator && state==UnitState.TREATING) {
			Evacuator tmp=((Evacuator)this);
			int passengers=tmp.getPassengers().size();
			if(passengers==tmp.getMaxCapacity() && distanceToTarget<=0) {
				tmp.setDistanceToTarget(distanceToTarget+getStepsPerCycle());
				tmp.setDistanceToBase(tmp.getDistanceToBase()-getStepsPerCycle());
			}
			else {
				if(passengers==tmp.getMaxCapacity() && tmp.getDistanceToBase()<=0){
					tmp.getPassengers().clear();
					tmp.setState(UnitState.RESPONDING);
					if(worldListener!=null)
						worldListener.assignAddress(this, 0, 0);
				}
			}
		}
		
		
		if(state == UnitState.RESPONDING && distanceToTarget <= 0) {
			this.setState(UnitState.TREATING);
			if(worldListener!=null) {
				Rescuable target=this.getTarget();
				Address add=target.getLocation();
				worldListener.assignAddress(this,add.getX(), add.getY());
			}
		}
		
		
	}
	
	abstract public void jobsDone() ;
	
	public void respond(Rescuable r) {
		if(this.getTarget()!=r) {
			Rescuable s=this.getTarget();
			s.getDisaster().setActive(true);
			this.target=r;
			this.state=UnitState.RESPONDING;
			Address tAdd=target.getLocation();
			Address uAdd=this.getLocation();
			this.distanceToTarget=tAdd.getX()-uAdd.getX()+tAdd.getY()-uAdd.getY();
		}
	}
	
	public abstract void treat() ;
		
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
