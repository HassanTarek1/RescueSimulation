package model.disasters;

import simulation.Rescuable;
import simulation.Simulatable;

	public class Disaster  implements Simulatable{
	private int startCycle;
	private boolean active;
	private Rescuable target;
	
	public Disaster() {
		// TODO Auto-generated constructor stub
	}
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle=startCycle;
		this.target=target;
	}
	public void setStartCycle(int startCycle) {
		this.startCycle = startCycle;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setTarget(Rescuable target) {
		this.target = target;
	}

}
