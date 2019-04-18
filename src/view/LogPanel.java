package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

public class LogPanel extends JScrollPane{
	private JTextArea textArea;
	private File font_file = new File("Fonts/8-Bit Madness.ttf");
	private Font font;	

	public JTextArea getTextArea() {
		return textArea;
	}

	public LogPanel(JTextArea textArea) throws FontFormatException, IOException {
		super(textArea);
		this.textArea=textArea;
		this.setSize(textArea.getSize());
		
		//font
		font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font correct8BitFont = font.deriveFont(20f);
		textArea.setFont(correct8BitFont);
		
		textArea.setBackground(Color.decode("#EDBA61"));
		
		this.setPreferredSize(this.getSize());
		setMaximumSize(this.getPreferredSize());
		this.setLayout(new ScrollPaneLayout());
		this.setWheelScrollingEnabled(true);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	}


}
