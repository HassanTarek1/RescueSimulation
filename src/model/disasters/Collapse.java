package model.disasters;


import exceptions.BuildingAlreadyCollapsedException;
import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster{
	
	//constructors
	public Collapse(int cycle, ResidentialBuilding target) {
		super(cycle,target);
	}

	
	//methods
	
	public void strike() throws BuildingAlreadyCollapsedException {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		if(target.getStructuralIntegrity()<=0) {
			String message ="The Builging is Already Collapsed";
			throw new BuildingAlreadyCollapsedException(this,message);
		}
		int oldFoundDamage=target.getFoundationDamage();
		target.setFoundationDamage(oldFoundDamage+10);
		target.struckBy(this);
		this.setActive(true);
	}
	public void cycleStep() throws BuildingAlreadyCollapsedException {
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}
	
	public String toString() {
		return "Collapse on Building ";
	}
	
}
