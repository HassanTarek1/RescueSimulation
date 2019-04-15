package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MidWestTop extends ImagePanel{
	private LogPanel log;
	public MidWestTop(String img) {
		super(img);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		add(Box.createRigidArea(new Dimension(7,10)));
		
		//Contents
		JTextArea textArea = new JTextArea();
		textArea.setFont(new java.awt.Font("Helvitica",1,14));
		textArea.setEditable(false);
		textArea.setMargin(new java.awt.Insets(12, 12, 12, 12));
		textArea.setSize(320,250);
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setForeground(Color.red);
		log = new LogPanel(textArea);
		add(log);
		add(Box.createRigidArea(new Dimension(7,10)));
		
		
	}
	public LogPanel getLog() {
		return log;
	}
}
