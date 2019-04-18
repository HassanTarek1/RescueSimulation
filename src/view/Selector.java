package view;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import model.people.Citizen;
import simulation.Address;
import simulation.Simulatable;

public class Selector extends JComboBox<Simulatable> {
	private ArrayList<Simulatable> selected = new ArrayList<Simulatable>(2);
	private MouseListener cont;
	public Selector(MouseListener cont) {
		
		super();
		addItem(null);
		this.cont = cont;
	}
	
	public void addSelected (Simulatable r) {
		if(selected.size()<=2) {
			selected.add(r);
		}
	}

	public ArrayList<Simulatable> getSelected() {
		return selected;
	}

	public MouseListener getCont() {
		return cont;
	}
	
	
}
