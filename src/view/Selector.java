package view;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import model.people.Citizen;
import simulation.Address;
import simulation.Simulatable;

public class Selector extends JComboBox<Simulatable> {
	
	private MouseListener cont;
	public Selector(MouseListener cont) {
		
		super();
		addItem(null);
		this.cont = cont;
	}
	
}
