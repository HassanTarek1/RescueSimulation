package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MiddleEast extends JPanel{
	private WrappedLabel availableUnits; 
	private WrappedLabel respondingUnits;
	private WrappedLabel treatingUnits;
	private MiddleEastTop top;
	private MiddleEastCenter center;
	private MiddleEastBottom bottom;
	public MiddleEast() {
		super();
		
		setSize(500, 675);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0,0)));
		
		//Contents
		availableUnits = new WrappedLabel("","icons/Game panel/Available Units.png");
		add(availableUnits);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		top = new MiddleEastTop();
		add(top);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		respondingUnits = new WrappedLabel("","icons/Game panel/respondingunits.png");
		add(respondingUnits);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		center = new MiddleEastCenter();
		add(center);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		treatingUnits = new WrappedLabel("","icons/Game panel/treatingunits.png");
		add(treatingUnits);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		bottom = new MiddleEastBottom();
		add(bottom);
	}
}
