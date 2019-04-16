package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MiddleEast extends JPanel{
	private MouseListener listener;
	private MouseListener cont;
	private WrappedLabel availableUnits; 
	private WrappedLabel respondingUnits;
	private WrappedLabel treatingUnits;
	private MiddleEastTop top;
	private Selector citizenSelector;
	private MiddleEastCenter center;
	private MiddleEastBottom bottom;
	public MiddleEast(MouseListener listener,MouseListener cont) {
		super();
		
		setSize(500, 675);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0,0)));
		this.listener = listener;
		this.cont = cont;
		
		//Contents
		citizenSelector = new Selector(cont);
		citizenSelector.addActionListener((ActionListener) cont);
		add(citizenSelector);
		
		availableUnits = new WrappedLabel("","icons/Game panel/Available Units.png");
		add(availableUnits);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		top = new MiddleEastTop(this.listener,this.cont);
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
	public Selector getCitizenSelector() {
		return citizenSelector;
	}
	public MiddleEastTop getTop() {
		return top;
	}
	public MiddleEastCenter getCenter() {
		return center;
	}
	public MiddleEastBottom getBottom() {
		return bottom;
	}
}
