package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import model.infrastructure.ResidentialBuilding;

public class Fire extends Disaster {

	public Fire(int cycle, ResidentialBuilding target) {
		super(cycle,target);
	}
	
	//methods
	public void strike() throws BuildingAlreadyCollapsedException {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		if(target.getStructuralIntegrity()<=0) {
			String message ="The Builging is Already Collapsed";
			throw new BuildingAlreadyCollapsedException(this,message);
		}
		target.struckBy(this);
		this.setActive(true);
		int oldFireDamage=target.getFireDamage();
		target.setFireDamage(oldFireDamage+10);
	}
	public void cycleStep() throws BuildingAlreadyCollapsedException {
		strike();
	}
	public String toString() {
		return "Fire on Building ";
	}

}
