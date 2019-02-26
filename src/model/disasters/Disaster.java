package model.disasters;

import simulation.Rescuable;
import simulation.Simulatable;

	public abstract class Disaster  implements Simulatable{
	private int startCycle;
	private boolean active = false;
	private Rescuable target;
	
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle=startCycle;
		this.target=target;
	}
	public  int getStartCycle() {
		return startCycle;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Rescuable getTarget() {
		return target;
	}

}
