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
	private JLabel injuredCitizen;
	private JLabel infectedCitizen;
	private JLabel deadCitizen;
	private JLabel Building;
	private JLabel gasedBuilding;
	private JLabel buildingOnFire;
	private JLabel fallenBuilding;
	private JLabel Fire;
	private JLabel GasLeak;
	private JLabel Collapse;
	private boolean disaster = false;
	
	
	public Cell(String img,int X,int Y) {
		super(img);
		indxX = X;
		indxY = Y;
		setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		Citizen = new JLabel();
		injuredCitizen=new JLabel();
		infectedCitizen=new JLabel();
		deadCitizen=new JLabel();
		Building = new JLabel();
		fallenBuilding = new JLabel();
		gasedBuilding=new JLabel();
		buildingOnFire=new JLabel();
		Fire = new JLabel();
		GasLeak = new JLabel();
		Collapse = new JLabel();
		Citizen.setIcon(new ImageIcon("icons/Game panel/citizen.png"));
		infectedCitizen.setIcon(new ImageIcon("icons/Game panel/infectedCitizen.png"));
		injuredCitizen.setIcon(new ImageIcon("icons/Game panel/injuredCitizen.png"));
		deadCitizen.setIcon(new ImageIcon("icons/Game panel/dead citizen.png"));
		Building.setIcon(new ImageIcon("icons/Game panel/building.png"));
		gasedBuilding.setIcon(new ImageIcon("icons/Game panel/gasedBuilding.png"));
		fallenBuilding.setIcon(new ImageIcon("icons/Game panel/fallenBuilding.png"));
		buildingOnFire.setIcon(new ImageIcon("icons/Game panel/buildingOnFire.png"));
		Fire.setIcon(new ImageIcon("icons/Game panel/Fire.png"));
		GasLeak.setIcon(new ImageIcon("icons/Game panel/Gas leak.png"));
		Collapse.setIcon(new ImageIcon("icons/Game panel/collapse.png"));

	}

	public void setDisaster(boolean disaster) {
		this.disaster = disaster;
	}

	public boolean isDisaster() {
		return disaster;
	}

	public JLabel getCitizen() {
		return Citizen;
	}

	public JLabel getBuilding() {
		return Building;
	}

	public JLabel getDeadCitizen() {
		return deadCitizen;
	}

	public JLabel getBuildingOnFire() {
		return buildingOnFire;
	}

	public JLabel getInjuredCitizen() {
		return injuredCitizen;
	}

	public JLabel getInfectedCitizen() {
		return infectedCitizen;
	}

	public JLabel getGasedBuilding() {
		return gasedBuilding;
	}

	public JLabel getFallenBuilding() {
		return fallenBuilding;
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
