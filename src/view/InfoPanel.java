package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import javafx.geometry.Insets;
import javafx.scene.text.Font;


public class InfoPanel extends JScrollPane{
	private JTextArea textArea;
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public InfoPanel(JTextArea textArea) {
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
		
		//Contents
		
	}
}
