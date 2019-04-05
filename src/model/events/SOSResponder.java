package model.events;

import exceptions.IncompatibleTargetException;
import simulation.Rescuable;

public interface SOSResponder {
	public void respond(Rescuable r) throws IncompatibleTargetException;
}
