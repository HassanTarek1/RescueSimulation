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
		add(Box.createRigidArea(new Dimension(0,10)));
		
		//Contents
		Log = new WrappedLabel("", "icons/Game panel/log.png");
		add(Log);
		add(Box.createRigidArea(new Dimension(0,10)));
		
		top = new MidWestTop();
		add(top);
		add(Box.createRigidArea(new Dimension(0,25)));
		
		
		info = new WrappedLabel("", "icons/Game panel/info.png");
		add(info);
		add(Box.createRigidArea(new Dimension(0,10)));
		
		bottom = new MidWestBottom();
		add(bottom);
		
	}
}
