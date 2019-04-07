package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class About extends ImagePanel{
	public About() {
		
		//panel
		super("icons/Main Menu Background(empty).png");
		setSize(1280, 720);
		setVisible(false);
		
		//text area 
		TextArea about = new TextArea();
		add(about);
		
	}
}
