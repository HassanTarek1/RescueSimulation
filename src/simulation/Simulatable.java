package simulation;

import exceptions.DisasterException;

public interface Simulatable {
	public void cycleStep() throws DisasterException;
	
}
