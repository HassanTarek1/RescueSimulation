package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.SpringLayout.Constraints;

import controller.CommandCenter;
import model.units.UnitState;

public class MiddleEastTop extends JPanel {
	private UnitPanel availableUnits;
	private MouseListener listener;
	private MouseListener cont;
	private CommandCenter cont2;
	private JButton Ambulance;
	private JButton DiseaseControlUnit;
	private JButton Evacuator;
	private JButton FireTruck;
	private JButton GasControlUnit;
	public MiddleEastTop(MouseListener listener,MouseListener cont) {
		super();
		this.listener = listener;
		this.cont = cont;
		this.cont2 = (CommandCenter) cont;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		
		//Contents
		availableUnits = new UnitPanel("icons/Game panel/UnitPanel.png");
		availableUnits.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 0;
		constraints.weighty = 0;
		//Buttons
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		Ambulance = new JButton("X "+cont2.countUnits(model.units.Ambulance.class, UnitState.IDLE));
		Ambulance.setIcon(new ImageIcon("icons/Game panel/ambulance.png"));
		Ambulance.setPreferredSize(new Dimension(90, 50));
		Ambulance.setToolTipText("Ambulance");
		availableUnits.add(Ambulance,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		DiseaseControlUnit = new JButton("X "+cont2.countUnits(model.units.DiseaseControlUnit.class, UnitState.IDLE));
		DiseaseControlUnit.setPreferredSize(new Dimension(90, 50));
		Ambulance.setToolTipText("Ambulance");
		DiseaseControlUnit.setToolTipText("DiseaseControlUnit");
		availableUnits.add(DiseaseControlUnit,constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		Evacuator = new JButton("X "+cont2.countUnits(model.units.Evacuator.class, UnitState.IDLE));
		Evacuator.setToolTipText("Evacuator");
		Evacuator.setPreferredSize(new Dimension(90, 50));
		availableUnits.add(Evacuator,constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		FireTruck = new JButton("X "+cont2.countUnits(model.units.FireTruck.class, UnitState.IDLE));
		FireTruck.setIcon(new ImageIcon("icons/Game panel/fire truck.png"));
		FireTruck.setToolTipText("Fire truck");
		FireTruck.setPreferredSize(new Dimension(90, 50));
		availableUnits.add(FireTruck,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		GasControlUnit = new JButton("X "+cont2.countUnits(model.units.GasControlUnit.class, UnitState.IDLE));
		GasControlUnit.setToolTipText("Gas control unit");
		GasControlUnit.setPreferredSize(new Dimension(90, 50));
		availableUnits.add(GasControlUnit,constraints);
		
		add(availableUnits);
		add(Box.createRigidArea(new Dimension(10,0)));
		
	}
	public JButton getAmbulance() {
		return Ambulance;
	}
	public JButton getDiseaseControlUnit() {
		return DiseaseControlUnit;
	}
	public JButton getEvacuator() {
		return Evacuator;
	}
	public JButton getFireTruck() {
		return FireTruck;
	}
	public JButton getGasControlUnit() {
		return GasControlUnit;
	}	
	
}