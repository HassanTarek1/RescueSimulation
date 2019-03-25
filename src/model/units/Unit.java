package model.units;


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
		case RESPONDING: setDistanceToTarget(distanceToTarget - getStepsPerCycle());
		               if (this instanceof PoliceUnit) {
			                ((PoliceUnit)this).setDistanceToBase(((PoliceUnit)this).getDistanceToBase()+getStepsPerCycle());
		                     }break;
		case TREATING:
			
			if(this instanceof Evacuator) {
				//checks if the evacuator is full and didn't arrive at the base
				Evacuator tmp=((Evacuator)this);
				int passengers=tmp.getPassengers().size();
				if(passengers==tmp.getMaxCapacity() && tmp.getDistanceToBase()>0) {
					tmp.setDistanceToTarget(distanceToTarget+getStepsPerCycle());
					tmp.setDistanceToBase(tmp.getDistanceToBase()-getStepsPerCycle());
					}
				else {
					treat();
				}
			}
			else
				treat();
			break;
		default: break;
		}		
		
		if(state == UnitState.RESPONDING && distanceToTarget <= 0) {
			this.setState(UnitState.TREATING);
			if(worldListener!=null) {
				Rescuable target=this.getTarget();
				Address address=target.getLocation();
				worldListener.assignAddress(this,address.getX(), address.getY());
			}
		}
		
		
	}
	
	public void jobsDone() {
		target = null;
		this.state = UnitState.IDLE;
	}
	
	public void respond(Rescuable r) {
		//Overridden in medicalUnit class
			if(this.getTarget() != null && r!=this.getTarget()) {
				if(this.getTarget() instanceof Citizen) {
					Citizen s=(Citizen)this.getTarget();
					s.getDisaster().setActive(true);
				}
				else {
					ResidentialBuilding s=(ResidentialBuilding)this.getTarget();
					if(s.getDisaster() != null)
						s.getDisaster().setActive(true);
				}
				target=r;
			}
			
			if(this.getTarget() == null)
				target=r;
			
			
				setDistanceToTarget(DeltaX() + DeltaY());
				this.state = UnitState.RESPONDING;
			
	}
	
	public void treat() {};

	
	public int DeltaX() {
		if(target != null) {
			int X = this.location.getX();
			int NX = this.target.getLocation().getX();
			
			return Math.abs(NX - X);
		}
		
		return 0;
	}
	
	public int DeltaY() {
		if(target != null) {
			int Y = this.location.getY();
			int NY = this.target.getLocation().getY();
			
			return Math.abs(NY - Y);
		}
		
		return 0;
	}
	
//constructor(s):
	public Unit(String id, Address location, int stepsPerCycle, WorldListener listener){
		worldListener = listener;
		unitID = id;
		this.location = location;
		if(worldListener!=null)
			worldListener.assignAddress(this, location.getX(), location.getY());
		this.stepsPerCycle = stepsPerCycle;
		state = UnitState.IDLE;
	}
	
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
