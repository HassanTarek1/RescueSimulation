package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;



public class InfoPanel extends JScrollPane{
	private JTextArea textArea;
	private File font_file = new File("Fonts/8-Bit Madness.ttf");
	private Font font;
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public InfoPanel(JTextArea textArea) throws FontFormatException, IOException {
		super(textArea);
		this.textArea=textArea;
		
		//font
		font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font correct8BitFont = font.deriveFont(20f);
		textArea.setFont(correct8BitFont);
		
		textArea.setBackground(Color.decode("#EDBA61"));
		
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
