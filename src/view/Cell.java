package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Cell extends ImagePanel{
	private int indxX;
	private int indxY;
	private JLabel Citizen;
	private JLabel Building;
	private JLabel Fire;
	private JLabel GasLeak;
	private JLabel Collapse;
	
	public Cell(String img,int X,int Y) {
		super(img);
		indxX = X;
		indxY = Y;
		setLayout(new BorderLayout());
		//setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Citizen = new JLabel();
		Building = new JLabel();
		Fire = new JLabel();
		GasLeak = new JLabel();
		Collapse = new JLabel();
		Citizen.setIcon(new ImageIcon("icons/Game panel/citizen.png"));
		Building.setIcon(new ImageIcon("icons/Game panel/building.png"));
		Fire.setIcon(new ImageIcon("icons/Game panel/Fire.png"));
		GasLeak.setIcon(new ImageIcon("icons/Game panel/Gas leak.png"));
		Collapse.setIcon(new ImageIcon("icons/Game panel/collapse.png"));
//		add(Citizen,BorderLayout.CENTER);
//		add(Building,BorderLayout.CENTER);
//		Citizen.setOpaque(false);
//		Citizen.setVisible(false);
//
//		Building.setOpaque(false);
//		Building.setVisible(false);
	}

	public JLabel getCitizen() {
		return Citizen;
	}

	public JLabel getBuilding() {
		return Building;
	}

	public JLabel getFire() {
		return Fire;
	}

	public JLabel getGasLeak() {
		return GasLeak;
	}

	public JLabel getCollapse() {
		return Collapse;
	}

	public int getIndxX() {
		return indxX;
	}

	public int getIndxY() {
		return indxY;
	}
}
