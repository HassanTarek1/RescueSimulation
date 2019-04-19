package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoicePanel extends ImagePanel {
	private JLabel randomCSV;
	private JLabel defaultCSV;
	private MouseListener menu;
	private Button muteButton;
	private Button backButton;
	public  ChoicePanel(String img,MouseListener menu) {
		super(img);
		this.menu = menu;
		setSize(1280, 720);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		randomCSV = new JLabel();
		randomCSV.setIcon(new ImageIcon("icons/Main menu/RandomCSV.png"));
		randomCSV.setAlignmentX(Component.CENTER_ALIGNMENT);
		randomCSV.setForeground(Color.DARK_GRAY);
		randomCSV.addMouseListener(menu);
		
		defaultCSV = new JLabel();
		defaultCSV.setIcon(new ImageIcon("icons/Main menu/defaultCSV.png"));
		defaultCSV.setAlignmentX(Component.CENTER_ALIGNMENT);
		defaultCSV.setForeground(Color.DARK_GRAY);
		defaultCSV.addMouseListener(menu);
			
		JPanel topLeftContainer = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		topLeftContainer.setLayout(new GridBagLayout());
		topLeftContainer.setOpaque(false);
		topLeftContainer.setSize(1280, 40);
		topLeftContainer.setMaximumSize(topLeftContainer.getSize());
		
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(5, 10, 0, 0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		backButton = new Button("icons/Game panel/BackButton.png");
		backButton.addMouseListener(menu);
		backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		topLeftContainer.add(Box.createRigidArea(new Dimension(15,0)));
		backButton.addMouseListener(menu);
		topLeftContainer.add(backButton);
		backButton.setSize(60,60);
		
		muteButton=new Button("icons/Game panel/mute.png");
		topLeftContainer.add(muteButton,constraints);
		muteButton.addMouseListener(menu);
		muteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(Box.createRigidArea(new Dimension(0,5)));
		add(topLeftContainer);
		add(Box.createRigidArea(new Dimension(0,230)));
		add(randomCSV);
		add(Box.createRigidArea(new Dimension(0,50)));
		add(defaultCSV);
		setVisible(false);
		
		
	}
	public JLabel getRandomCSV() {
		return randomCSV;
	}
	public JLabel getDefaultCSV() {
		return defaultCSV;
	}
	public MouseListener getMenu() {
		return menu;
	}
	public Button getMuteButton() {
		return muteButton;
	}
	public Button getBackButton() {
		return backButton;
	}
}
