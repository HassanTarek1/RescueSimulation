package view;

import java.awt.Component;

import javax.swing.BoxLayout;

public class LogPanel extends ImagePanel{
	private Log log;
	public LogPanel(String img) {
		super(img);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//Contents
		log = new Log();
		//add(log);
	}
}
