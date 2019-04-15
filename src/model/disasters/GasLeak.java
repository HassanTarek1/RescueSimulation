
package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import model.infrastructure.ResidentialBuilding;

public class GasLeak extends Disaster{

	public GasLeak(int cycle, ResidentialBuilding target) {
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
		int oldGasLevel=target.getGasLevel();
		target.setGasLevel(oldGasLevel+10);
	}
	public void cycleStep() {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		int oldGasLevel=target.getGasLevel();
		target.setGasLevel(oldGasLevel+15);
	}
	public String toString() {
		return "Gas leak on Building ";
	}
}
