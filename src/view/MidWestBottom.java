package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MidWestBottom extends JPanel{
	private InfoPanel info;
	public MidWestBottom() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		add(Box.createRigidArea(new Dimension(10,0)));
		
		//Contents
		info = new InfoPanel("icons/Game panel/InfoPanel.png");
		add(info);
	}
}
