package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TopBar extends ImagePanel{
	private MouseListener listener;
	private Button muteButton;
	private Button backButton;
	public TopBar(String img,MouseListener e) {
		super(img);
		listener = e;
		setSize(1425,60);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		//Buttons
			//Back
			backButton = new Button("icons/Game panel/BackButton.png");
			backButton.addMouseListener(listener);
			backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(Box.createRigidArea(new Dimension(15,0)));
			add(backButton);
			
			//Mute
			muteButton=new Button("icons/Game panel/Mute.png");
			//muteButton.addMouseListener(this);
			muteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			add(Box.createRigidArea(new Dimension(15,0)));
			add(muteButton);
	}
	public Button getMuteButton() {
		return muteButton;
	}
	public Button getBackButton() {
		return backButton;
	}
}
