package model.units;

import java.util.ArrayList;

import model.people.Citizen;
import simulation.Address;

public abstract class PoliceUnit extends Unit {

//variables:	
	
	private ArrayList<Citizen> passengers;
	private int maxCapacity;
	private int distanceToBase;
	
//setters/getters:
	
	
	public int getMaxCapacity() {
		return maxCapacity;
	}
	
	public int getDistanceToBase() {
		return distanceToBase;
	}
	
	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}
	
//constructor(s):	
	
	public PoliceUnit(String id, Address location, int stepsPerCycle, int maxCapacity){
		super(id, location, stepsPerCycle);
		passengers = new ArrayList<>();
		this.maxCapacity = maxCapacity;
	}
	
	
}