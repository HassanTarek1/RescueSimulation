package view;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import model.people.Citizen;
import simulation.Address;

public class Selector extends JComboBox<Citizen> {
	
	private MouseListener cont;
	public Selector(MouseListener cont) {
		
		super();
		this.cont = cont;
	}
	
}
