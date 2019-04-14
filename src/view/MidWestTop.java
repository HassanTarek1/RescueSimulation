package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MidWestTop extends JPanel{
	private LogPanel log;
	public MidWestTop() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		add(Box.createRigidArea(new Dimension(10,0)));
		
		//Contents
		log = new LogPanel("icons/Game panel/InfoPanel.png");
		add(log);
		
		
	}
	public LogPanel getLog() {
		return log;
	}
}
