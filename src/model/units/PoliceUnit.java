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
	
	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}
	
	public void setPassengers(ArrayList<Citizen> passengers) {
		this.passengers = passengers;
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
	
//constructor(s):	
	
	PoliceUnit(String id, Address location, int stepsPerCycle, int maxCapacity){
		super(id, location, stepsPerCycle);
		
		this.maxCapacity = maxCapacity;
	}
	
	
}
