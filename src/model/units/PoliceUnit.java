package model.units;

import java.util.ArrayList;

import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;

public abstract class PoliceUnit extends Unit {

//variables:	
	
	private ArrayList<Citizen> passengers;
	private int maxCapacity;
	private int distanceToBase;
	private boolean toBase;
	
//setters/getters:
	
	
	public boolean isToBase() {
		return toBase;
	}

	public void setToBase(boolean toBase) {
		this.toBase = toBase;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	public int getDistanceToBase() {
		return distanceToBase;
	}
	
	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}
	
	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}
//Methods
	
	
//constructor(s):	
	
	public PoliceUnit(String id, Address location, int stepsPerCycle,WorldListener listener, int maxCapacity){
		super(id, location, stepsPerCycle, listener);
		passengers = new ArrayList<>();
		this.maxCapacity = maxCapacity;
	}
	
	public PoliceUnit(String id, Address location, int stepsPerCycle, int maxCapacity){
		super(id, location, stepsPerCycle);
		passengers = new ArrayList<>();
		this.maxCapacity = maxCapacity;
	}

	
}
