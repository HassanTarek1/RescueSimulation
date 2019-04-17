package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.CommandCenter;
import model.units.UnitState;

public class MiddleEastBottom extends JPanel{
	private UnitPanel treatingUnits;
	private MouseListener listener;
	private MouseListener cont;
	private CommandCenter cont2;
	private JButton Ambulance;
	private JButton DiseaseControlUnit;
	private JButton Evacuator;
	private JButton FireTruck;
	private JButton GasControlUnit;
	public MiddleEastBottom(MouseListener listener,MouseListener cont) {
		super();
		
		this.listener = listener;
		this.cont = cont;
		this.cont2 = (CommandCenter) cont;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		
		//Contents
		treatingUnits = new UnitPanel("icons/Game panel/UnitPanel.png");
		treatingUnits.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 0;
		constraints.weighty = 0;
		
		//Bttons
				constraints.gridx = 0;
				constraints.gridy = 0;
				Ambulance = new JButton("X "+cont2.countUnits(model.units.Ambulance.class, UnitState.TREATING));
				Ambulance.setIcon(new ImageIcon("icons/Game panel/ambulance.png"));
				Ambulance.setPreferredSize(new Dimension(90, 50));
				Ambulance.setToolTipText("Ambulance");
				Ambulance.addMouseListener(cont);
				treatingUnits.add(Ambulance,constraints);
				
				constraints.gridx = 1;
				constraints.gridy = 0;
				DiseaseControlUnit = new JButton("X "+cont2.countUnits(model.units.DiseaseControlUnit.class, UnitState.TREATING));
				DiseaseControlUnit.setPreferredSize(new Dimension(90, 50));
				DiseaseControlUnit.setIcon(new ImageIcon("icons/Game panel/DiseaseControlUnit.png"));
				DiseaseControlUnit.setToolTipText("Disease Control Unit");
				DiseaseControlUnit.setToolTipText("DiseaseControlUnit");
				DiseaseControlUnit.addMouseListener(cont);
				treatingUnits.add(DiseaseControlUnit,constraints);
				
				constraints.gridx = 2;
				constraints.gridy = 0;
				Evacuator = new JButton("X "+cont2.countUnits(model.units.Evacuator.class, UnitState.TREATING));
				Evacuator.setToolTipText("Evacuator");
				Evacuator.setIcon(new ImageIcon("icons/Game panel/Evacuator.png"));
				Evacuator.addMouseListener(cont);
				Evacuator.setPreferredSize(new Dimension(90, 50));
				treatingUnits.add(Evacuator,constraints);
				
				constraints.gridx = 0;
				constraints.gridy = 1;
				FireTruck = new JButton("X "+cont2.countUnits(model.units.FireTruck.class, UnitState.TREATING));
				FireTruck.setIcon(new ImageIcon("icons/Game panel/fire truck.png"));
				FireTruck.setToolTipText("Fire truck");
				FireTruck.setPreferredSize(new Dimension(90, 50));
				FireTruck.addMouseListener(cont);
				treatingUnits.add(FireTruck,constraints);
				
				constraints.gridx = 1;
				constraints.gridy = 1;
				GasControlUnit = new JButton("X "+cont2.countUnits(model.units.GasControlUnit.class, UnitState.TREATING));
				GasControlUnit.setToolTipText("Gas control unit");
				GasControlUnit.setIcon(new ImageIcon("icons/Game panel/GasControlUnit.png"));
				GasControlUnit.setPreferredSize(new Dimension(90, 50));
				GasControlUnit.addMouseListener(cont);
				treatingUnits.add(GasControlUnit,constraints);
				
				add(treatingUnits);
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
