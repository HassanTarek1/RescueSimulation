package view;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import model.people.Citizen;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Selector extends JComboBox<Simulatable> {
	private Rescuable selectedTarget;
	private Unit selectedUnit;
	private MouseListener cont;
	public Selector(MouseListener cont) {
		
		super();
		addItem(null);
		this.cont = cont;
	}
	
	public void addSelected (Simulatable r) {
		if(r instanceof Rescuable) {
			selectedTarget = (Rescuable)r;
		}
		else if(r instanceof Unit) {
			selectedUnit = (Unit)r;
		}
	}

	public MouseListener getCont() {
		return cont;
	}

	public Rescuable getSelectedTarget() {
		return selectedTarget;
	}

	public Unit getSelectedUnit() {
		return selectedUnit;
	}

	public void setSelectedTarget(Rescuable selectedTarget) {
		this.selectedTarget = selectedTarget;
	}

	public void setSelectedUnit(Unit selectedUnit) {
		this.selectedUnit = selectedUnit;
	}

	public void setCont(MouseListener cont) {
		this.cont = cont;
	}
	
	
	
	
}
