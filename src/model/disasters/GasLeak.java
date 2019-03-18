package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class GasLeak extends Disaster{

	public GasLeak(int cycle, ResidentialBuilding target) {
		super(cycle,target);
	}

	//methods 
	public void strike() {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		int oldGasLevel=target.getGasLevel();
		target.setGasLevel(oldGasLevel+10);
	}
	public void cycleStep() {
		ResidentialBuilding target=(ResidentialBuilding)this.getTarget();
		int oldGasLevel=target.getGasLevel();
		target.setGasLevel(oldGasLevel+15);
	}
}
