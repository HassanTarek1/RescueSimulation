package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class Fire extends Disaster {

	public Fire(int cycle, ResidentialBuilding target) {
		super(cycle,target);
	}
	
	//methods
	public void strike() {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		target.struckBy(this);
		this.setActive(true);
		int oldFireDamage=target.getFireDamage();
		target.setFireDamage(oldFireDamage+10);
	}
	public void cycleStep() {
		strike();
	}

}
