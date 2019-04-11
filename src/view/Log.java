package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Log extends JTextArea{
	public Log() {
		super();
		setSize(500, 216);
		setEditable(false);
		setPreferredSize(this.getSize());
		setMaximumSize(this.getPreferredSize());
		add(Box.createRigidArea(new Dimension(0,50)));
		add(Box.createRigidArea(new Dimension(50,0)));
		setAlignmentX(Component.CENTER_ALIGNMENT);
	}

}
