package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CellContent extends JFrame{
	private File font_file = new File("Fonts/8-Bit Madness.ttf");
	private Font font;
	private HashMap<Integer, String> icons;
	private Container content;
	private JPanel top;
	private JLabel conetntsLabel;
	private JPanel iconPanel;
	public CellContent(ArrayList<Integer> keys) throws FontFormatException, IOException {
		icons = new HashMap<Integer, String>();
		//Fill table	 
		icons.put(0, "icons/Game panel/building.png");
		icons.put(1, "icons/Game panel/BuildingCitizen.png");
		icons.put(2, "icons/Game panel/citizen.png");
		icons.put(3, "icons/Game panel/buildingOnFire.png");
		icons.put(4, "icons/Game panel/gasedBuilding.png");
		icons.put(5, "icons/Game panel/collapse.png");
		icons.put(6, "icons/Game panel/injuredCitizen.png");
		icons.put(7, "icons/Game panel/infectedCitizen.png");
		icons.put(8, "icons/Game panel/ambulance.png");
		icons.put(9, "icons/Game panel/DiseaseControlUnit.png");
		icons.put(10, "icons/Game panel/fire truck.png");
		icons.put(11, "icons/Game panel/GasControlUnit.png");
		icons.put(12, "icons/Game panel/Evacuator.png");
		icons.put(13, "icons/Game panel/fallenBuilding.png");
		icons.put(14, "icons/Game panel/dead citizen.png");
		//------------
		
		setSize(300, 270);
		content = getContentPane();
		setUndecorated(true);
		setBackground(new Color(0,0,0,100));
		
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.setOpaque(false);
		top.setSize(300,50);
		top.add(Box.createRigidArea(new Dimension(5,0)));
		
		conetntsLabel = new JLabel("Contents");
		conetntsLabel.setForeground(Color.WHITE);
		top.add(conetntsLabel);
		top.add(Box.createRigidArea(new Dimension(5,0)));
		
		//font
		font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		Font correct8BitFont = font.deriveFont(20f);
		conetntsLabel.setFont(correct8BitFont);
		
		add(Box.createRigidArea(new Dimension(0,5)));
		content.add(top);
		add(Box.createRigidArea(new Dimension(0,5)));
		
		iconPanel = new JPanel();
		iconPanel.setSize(300,250);
		iconPanel.setOpaque(false);
		iconPanel.setLayout(new GridLayout(2,7));
		
		
				for (int i = 0; i < keys.size(); i++) {
					
					JLabel icon = new JLabel("");
					icon.setIcon(new ImageIcon(icons.get(keys.get(i))));
					iconPanel.add(icon);
				
				}
						
		content.add(iconPanel);
		
		pack();
	}
}
