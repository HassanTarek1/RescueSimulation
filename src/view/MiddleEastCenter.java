package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MiddleEastCenter extends JPanel{
	private UnitPanel respondingUnits;
	public MiddleEastCenter() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		
		//Contents
		respondingUnits = new UnitPanel("icons/Game panel/UnitPanel.png");
		add(respondingUnits);
		add(Box.createRigidArea(new Dimension(10,0)));
		
	}
}
