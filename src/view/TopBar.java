package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TopBar extends ImagePanel{
	private File font_file = new File("Fonts/8-Bit Madness.ttf");
	private Font font;	
	private MouseListener listener;
	private Button muteButton;
	private Button backButton;
	private JLabel target;
	private JLabel unit;
	private Button endCycle;
	private Button treat;
	private MouseListener cont;
	private JLabel topInfo;
	public TopBar(String img,MouseListener e,MouseListener cont) throws FontFormatException, IOException {
		super(img);
		listener = e;
		setSize(1425,60);
		setLayout(null);
		this.cont=cont;
		
		//Font
		font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font correct8BitFont = font.deriveFont(25f);
	
		topInfo=new JLabel("",SwingConstants.LEFT);
		topInfo.setBackground(Color.black);
		topInfo.setLocation(125, 5);
		topInfo.setSize(420, 50);
		topInfo.setVisible(true);
		topInfo.setFont(correct8BitFont);
		this.add(topInfo);
		
		//Buttons
			//Back
			backButton = new Button("icons/Game panel/BackButton.png");
			backButton.addMouseListener(listener);
			backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(Box.createRigidArea(new Dimension(15,0)));
			add(backButton);
			backButton.setLocation(10, 0);
			backButton.setSize(60,60);
			
			//Mute
			muteButton=new Button("icons/Game panel/mute.png");
			muteButton.addMouseListener(listener);
			add(Box.createRigidArea(new Dimension(15,0)));
			add(muteButton);
			muteButton.setLocation(70,10);
			muteButton.setSize(40,40);
			
			//endCycle
			endCycle=new Button("icons/Game panel/Next Cycle.png");
			endCycle.setAlignmentX(Component.RIGHT_ALIGNMENT);
			this.add(endCycle);
			endCycle.setLocation(1278,22);
			endCycle.setSize(135,16);;
			endCycle.addMouseListener(cont);
			
			//treat
			treat=new Button("icons/Game panel/Respond.png");
			treat.setAlignmentX(Component.RIGHT_ALIGNMENT);
			add(Box.createRigidArea(new Dimension(15,0)));
			this.add(treat);
			treat.setLocation(1150,19);
			treat.setSize(135,22);
			treat.addMouseListener(cont);
			
			//Target
			Font correct8BitFontSmall = font.deriveFont(20f);
			
			target=new JLabel("Target: Empty",SwingConstants.RIGHT);
			target.setBackground(Color.black);
			target.setLocation(760, -5);
			target.setSize(380, 50);
			target.setVisible(true);
			target.setFont(correct8BitFontSmall);
			this.add(target);
			
			//unit		
			unit =new JLabel("Unit: Empty",SwingConstants.RIGHT);
			unit.setBackground(Color.black);
			unit.setLocation(760, 15);
			unit.setSize(380, 50);
			unit.setVisible(true);
			unit.setFont(correct8BitFontSmall);
			this.add(unit);
			
	}
	public JLabel getUnit() {
		return unit;
	}
	public JLabel getTarget() {
		return target;
	}
	public Button getMuteButton() {
		return muteButton;
	}
	public Button getBackButton() {
		return backButton;
	}
	public MouseListener getListener() {
		return listener;
	}
	public Button getEndCycle() {
		return endCycle;
	}
	public MouseListener getCont() {
		return cont;
	}
	public JLabel getTopInfo() {
		return topInfo;
	}
	public Button getTreat() {
		return treat;
	}
}
