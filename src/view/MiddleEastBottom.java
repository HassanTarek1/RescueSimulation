package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MiddleEastBottom extends JPanel{
	private UnitPanel treatingUnits;
	public MiddleEastBottom() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		
		//Contents
		treatingUnits = new UnitPanel("icons/Game panel/UnitPanel.png");
		add(treatingUnits);
		add(Box.createRigidArea(new Dimension(10,0)));
	}
}
