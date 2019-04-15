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
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

public class LogPanel extends JScrollPane{
private JTextArea textArea;
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public LogPanel(JTextArea textArea) {
		super(textArea);
		this.textArea=textArea;
		this.setSize(textArea.getSize());
		this.setPreferredSize(this.getSize());
		setMaximumSize(this.getPreferredSize());
		this.setLayout(new ScrollPaneLayout());
		this.setWheelScrollingEnabled(true);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		//new
	}


}
