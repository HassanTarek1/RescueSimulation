package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TopBar extends ImagePanel{
	private MouseListener listener;
	private Button muteButton;
	private Button backButton;
	private Button endCycle;
	private MouseListener cont;
	public TopBar(String img,MouseListener e,MouseListener cont) {
		super(img);
		listener = e;
		setSize(1425,60);
		setLayout(null);
		this.cont=cont;
		
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
			muteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(Box.createRigidArea(new Dimension(15,0)));
			add(muteButton);
			muteButton.setLocation(70,10);
			muteButton.setSize(40,40);
			
			//endCycle
			endCycle=new Button("icons/Game panel/endCycle.png");
			//endCycle.addMouseListener(listener);
			endCycle.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(Box.createRigidArea(new Dimension(15,0)));
			this.add(endCycle);
			endCycle.setLocation(1150,17);
			endCycle.setSize(135,25);;
			endCycle.addMouseListener(cont);
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
}
