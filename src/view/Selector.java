package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import model.people.Citizen;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Selector extends JComboBox<Simulatable> {
	private File font_file = new File("Fonts/8-Bit Madness.ttf");
	private Font font;
	private Rescuable selectedTarget;
	private Unit selectedUnit;
	private MouseListener cont;
	public Selector(MouseListener cont) throws FontFormatException, IOException {
		
		super();
		//addItem(null);
		this.cont = cont;
		
		//font
		font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font correct8BitFont = font.deriveFont(20f);
		setFont(correct8BitFont);
		setForeground(Color.white);
		setBackground(Color.decode("#EDBA61"));
		
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
