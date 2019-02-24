package model.units;

public class Ambulance extends MedicalUnit{
	private String id;
	private Address location;
	private int stepsPerCycle;
	
	public Ambulance() {
		// TODO Auto-generated constructor stub
	}
	Ambulance(String id, Address location, int stepsPerCycle){
		this.id=id;
		this.location=location;
		this.stepsPerCycle=stepsPerCycle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Address getLocation() {
		return location;
	}
	public void setLocation(Address location) {
		this.location = location;
	}
	public int getStepsPerCycle() {
		return stepsPerCycle;
	}
	public void setStepsPerCycle(int stepsPerCycle) {
		this.stepsPerCycle = stepsPerCycle;
	}

}
