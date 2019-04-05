/**
 * 
 */
package exceptions;

import model.disasters.Disaster;

/**
 * @author Hassan
 *
 */
public abstract class DisasterException extends SimulationException {
	//attributes
	private Disaster disaster;
	
	//getters
	public Disaster getDisaster() {
		return disaster;
	}

	//constructors
	public DisasterException(Disaster disaster) {
		super();
	}
	public DisasterException(Disaster disaster,String message) {
		super(message);
	}
	
	
}
