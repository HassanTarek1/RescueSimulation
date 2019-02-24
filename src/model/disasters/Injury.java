package model.disasters;

import model.people.Citizen;

public class Injury {
	private int cycle;
	private Citizen target;
	public Injury() {
		// TODO Auto-generated constructor stub
	}
	public Injury(int cycle, Citizen target) {
		this.cycle=cycle;
		this.target=target;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public Citizen getTarget() {
		return target;
	}
	public void setTarget(Citizen target) {
		this.target = target;
	}
}
