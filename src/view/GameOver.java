package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import controller.CommandCenter;

public class GameOver extends JComponent{
	CommandCenter cont;
	JLabel gameovertext;
	JLabel casualties;
	JLabel returnButton;
	private File font_file = new File("Fonts/8-Bit Madness.ttf");
	private Font font;	
	public GameOver(CommandCenter cont) throws FontFormatException, IOException {
		this.cont = cont;
		setSize(1425,802);
		setBackground(new Color(0,0,0,200));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//font
		font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font correct8BitFont = font.deriveFont(72f);
		
		gameovertext = new JLabel();
		gameovertext.setIcon(new ImageIcon("icons/Game panel/Gameover.png"));
		gameovertext.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0,150)));
		add(gameovertext);
		
		casualties = new JLabel("");
		casualties.setFont(correct8BitFont);
		casualties.setForeground(Color.white);
		casualties.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0,100)));
		add(casualties);
		
		returnButton = new JLabel();
		returnButton.setIcon(new ImageIcon("icons/Game panel/Return.png"));
		returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		returnButton.addMouseListener(cont);
		add(Box.createRigidArea(new Dimension(0,100)));
		add(returnButton);
	}
	


	public JLabel getReturnButton() {
		return returnButton;
	}



	public JLabel getCasualties() {
		return casualties;
	}



	protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
	        g.fillRect(0, 0, 1425, 802);
	        super.paintComponent(g);
	    
	}
	
}
