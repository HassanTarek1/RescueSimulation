package exceptions;

import model.units.Unit;
import simulation.Rescuable;

public abstract class UnitException extends SimulationException {

	//Attributes
	private Unit unit;
	private Rescuable target;
	
	//getters
	public Unit getUnit() {
		return unit;
	}
	public Rescuable getTarget() {
		return target;
	}
	
	//constructors
	public UnitException(Unit unit, Rescuable target) {
		super();
		this.unit = unit;
		this.target = target;
	}
	public UnitException(Unit unit, Rescuable target, String message) {
		super(message);
		this.unit = unit;
		this.target = target;
	}
}
