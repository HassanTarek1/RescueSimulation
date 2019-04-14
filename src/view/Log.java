package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.TextArea;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class Log extends JScrollPane{
	public Log(JTextArea text) {
		super(text);
		//setSize(290,257);
		setPreferredSize(this.getSize());
		setMaximumSize(this.getPreferredSize());
		setOpaque(false);
		setWheelScrollingEnabled(true);
		
		
		add(Box.createRigidArea(new Dimension(0,50)));
		add(Box.createRigidArea(new Dimension(50,0)));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}

}
