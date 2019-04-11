package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MiddleEastTop extends JPanel{
	private UnitPanel availableUnits;
	public MiddleEastTop() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		
		//Contents
		availableUnits = new UnitPanel("icons/Game panel/UnitPanel.png");
		add(availableUnits);
		add(Box.createRigidArea(new Dimension(10,0)));
		
	}
}