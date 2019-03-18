package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster{
	
	//constructors
	public Collapse(int cycle, ResidentialBuilding target) {
		super(cycle,target);
	}

	
	//methods
	
	public void strike() {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		int oldFoundDamage=target.getFoundationDamage();
		target.setFoundationDamage(oldFoundDamage+10);
	}
	public void cycleStep() {
		strike();
	}
	
}
