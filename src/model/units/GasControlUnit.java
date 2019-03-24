package model.units;

import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit{

//variables.	
//setters/getters.
					
//constructor(s):
	
	public GasControlUnit(String id, Address location, int stepsPerCycle, WorldListener listener){
		super(id, location, listener, stepsPerCycle);
	}
	
	public GasControlUnit(String id, Address location, int stepsPerCycle){
		super(id, location, stepsPerCycle);
	}
	
	public void treat() {
		ResidentialBuilding X=(ResidentialBuilding)(this.getTarget());
		
		if(X.getDisaster() instanceof GasLeak)
			X.getDisaster().setActive(false);
		
		X.setGasLevel(X.getGasLevel()-10);
		
		
		jobsDone();
	}

	
	public void jobsDone() {
		ResidentialBuilding x = (ResidentialBuilding)this.getTarget();
		if((x.getGasLevel()<=0) || x.getStructuralIntegrity()<=0) {
			this.setState(UnitState.IDLE);
			WorldListener listener=this.getWorldListener();
			if(listener!=null)
				listener.assignAddress(this, 0, 0);
			}
		
		
	}
	
}
