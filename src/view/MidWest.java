package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MidWest extends JPanel{
	private MidWestTop top;
	private MidWestBottom bottom;
	private WrappedLabel Log;
	private WrappedLabel info;
	public MidWest() {
		super();
		setSize(500, 675);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0,0)));
		
		//Contents
		Log = new WrappedLabel("", "icons/Game panel/log.png");
		add(Log);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		top = new MidWestTop("icons/Game panel/InfoPanel.png");
		add(Box.createRigidArea(new Dimension(10,0)));
		add(top);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		
		info = new WrappedLabel("", "icons/Game panel/info.png");
		add(info);
		add(Box.createRigidArea(new Dimension(0,0)));
		
		bottom = new MidWestBottom("icons/Game panel/InfoPanel.png");
		add(Box.createRigidArea(new Dimension(10,0)));
		add(bottom);
		
	}
	public MidWestTop getTop() {
		return top;
	}
	public MidWestBottom getBottom() {
		return bottom;
	}
	public WrappedLabel getLog() {
		return Log;
	}
	public WrappedLabel getInfo() {
		return info;
	}
}
