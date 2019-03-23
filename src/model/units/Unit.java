package model.units;


import model.events.SOSResponder;
import model.events.WorldListener;
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
				if(passengers > 0 && tmp.getDistanceToBase()<=0){
					
					for(Citizen currCitizen : tmp.getPassengers())
						currCitizen.setState(CitizenState.RESCUED);
					
					tmp.getPassengers().clear();
					
					tmp.setState(UnitState.RESPONDING);
					
					tmp.jobsDone();

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
		
		if(this.target != null) {	
			boolean set = true;
			
			if(this instanceof Ambulance && ((Citizen) target).getBloodLoss() <= 0) set = false;
			if(this instanceof DiseaseControlUnit && ((Citizen) target).getToxicity() <= 0)set = false;
			
			if(set)
				this.target.getDisaster().setActive(true);
			
		}
		
			target = r;
			setDistanceToTarget(DeltaX() + DeltaY());
			this.state = UnitState.RESPONDING;
		
	}
	
	public abstract void treat() ;

	
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
