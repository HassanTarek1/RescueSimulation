package model.units;



import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
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
		if(this.getState() != UnitState.IDLE && target != null) {
			
			if(!(this instanceof Evacuator)) {
				if(state == UnitState.RESPONDING) {
					if(distanceToTarget > 0)
						distanceToTarget = (distanceToTarget - stepsPerCycle);
					else if(distanceToTarget <= 0) {
						distanceToTarget = 0;
						state = UnitState.TREATING;
						worldListener.assignAddress(this, target.getLocation().getX(), target.getLocation().getY());
					}			
				}
				
				if (state == UnitState.TREATING) 
					treat();
					
			}
			
			else {
				if(this.state == UnitState.RESPONDING) {
					
					//on the way to the target
					
					if(distanceToTarget > 0) {
						((Evacuator) this).jobsDone();
					distanceToTarget = (distanceToTarget - stepsPerCycle);
					((Evacuator) this).setDistanceToBase(((Evacuator) this).getDistanceToBase() + stepsPerCycle);
						if(distanceToTarget <= 0) {
							distanceToTarget = 0;
							((Evacuator) this).setDistanceToBase(this.getTarget().getLocation().getX() + this.getTarget().getLocation().getY());
							worldListener.assignAddress(this, target.getLocation().getX(), target.getLocation().getY());
						}
					}
					
					//reached the target last cycle (should start treating)
					else if(distanceToTarget <= 0) {
						state = UnitState.TREATING;
						
					}
				}
					
				else if(state == UnitState.TREATING) {
						Evacuator Evac = (Evacuator) this;
						
						
						if (!Evac.isToBase() && distanceToTarget > 0){
							distanceToTarget = (distanceToTarget - stepsPerCycle);
							Evac.setDistanceToBase(Evac.getDistanceToBase() + stepsPerCycle);
							
						}
						
						//reached the target with no passengers
						else if(!Evac.isToBase() && distanceToTarget <= 0){
					
							
							distanceToTarget = 0;
							Evac.setDistanceToBase(Evac.getTarget().getLocation().getX() + Evac.getTarget().getLocation().getY());
							worldListener.assignAddress(Evac, target.getLocation().getX(), target.getLocation().getY());
							treat();
							Evac.setToBase(true);
							//Evac.jobsDone();
						}
						
						//not empty and on the way to the base	
						if( Evac.isToBase()) {
							distanceToTarget = (distanceToTarget + stepsPerCycle);
							Evac.setDistanceToBase(Evac.getDistanceToBase() - stepsPerCycle);
						}
						
						//empty and in base 
						else if(Evac.isToBase() && Evac.getDistanceToBase() <= 0) {
							boolean allDead = true;
							Evac.setToBase(false);
							
							for (Citizen currCitizen : ((ResidentialBuilding) Evac.getTarget()).getOccupants()) {
								if (currCitizen.getState() != CitizenState.DECEASED) {
									allDead = false;
								}
							}
							
							Evac.jobsDone();
							if (allDead) 
								jobsDone();
							
							worldListener.assignAddress(Evac, 0, 0);
						}
							
						
						//not empty and in base
						if(Evac.isToBase() && Evac.getDistanceToBase() <= 0) {
							distanceToTarget = (target.getLocation().getX() + target.getLocation().getY());
							Evac.setDistanceToBase(0);
							worldListener.assignAddress(Evac, 0, 0);
							while(!Evac.getPassengers().isEmpty()) {
								if( Evac.getPassengers().get(0).getState() != CitizenState.DECEASED)
									Evac.getPassengers().get(0).setState(CitizenState.RESCUED);
								 	worldListener.assignAddress(Evac.getPassengers().get(0), 0, 0);
								 	Evac.getPassengers().get(0).getEmergencyService().receiveSOSCall(Evac.getPassengers().get(0));
								 	Evac.getPassengers().remove(0);

							}
							boolean allDead = true;
							for (Citizen currCitizen : ((ResidentialBuilding) Evac.getTarget()).getOccupants()) {
								if (currCitizen.getState() != CitizenState.DECEASED) {
									allDead = false;
								}
							}
							if (allDead) 
								jobsDone();
							Evac.jobsDone();
						
						}	
				}
			}
		}
	}
	
	public void jobsDone() {
		target = null;
		this.state = UnitState.IDLE;
		if(this.getWorldListener()!=null)
			this.getWorldListener().assignAddress(this, 0,0);
	}
	
	public void respond(Rescuable r) throws UnitException {
		//Overridden in medicalUnit class
		if (this == null || r == null) {
			throw new NullPointerException();
		}
			if(this.getTarget() != null && r!=this.getTarget()) {
				if(this.getTarget() instanceof Citizen) {
					Citizen s=(Citizen)this.getTarget();
					if(s.getDisaster() != null)
						s.getDisaster().setActive(true);
				}
				else if(this.getTarget() instanceof ResidentialBuilding){
					ResidentialBuilding s=(ResidentialBuilding)this.getTarget();
					if(s.getDisaster() != null)
						s.getDisaster().setActive(true);
				}
				target=r;
				this.state = UnitState.RESPONDING;
				setDistanceToTarget(DeltaX() + DeltaY());
			}
			
			else if(this.getTarget() == null) {
				target=r;
				setDistanceToTarget(DeltaX() + DeltaY());
				this.state = UnitState.RESPONDING;
			}
			
			
	}
	
	public void treat() {};

	
	private int DeltaX() {
		if(target != null) {
			int X = this.location.getX();
			int NX = this.target.getLocation().getX();
			
			return Math.abs(NX - X);
		}
		
		return 0;
	}
	
	private int DeltaY() {
		if(target != null) {
			int Y = this.location.getY();
			int NY = this.target.getLocation().getY();
			
			return Math.abs(NY - Y);
		}
		
		return 0;
	}
	public boolean canTreat(Rescuable r) {
		if (r instanceof Citizen) {
			Citizen target=(Citizen)r;
			if(target.getHp()<=0)
				return false;
			return true;
		}else if(r instanceof ResidentialBuilding) {
			ResidentialBuilding target=(ResidentialBuilding)r;
			if(target.getStructuralIntegrity()<=0)
				return false;
			return true;
		}
		return false;
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
	public abstract String toString2();
	public Unit() {}
	
}
