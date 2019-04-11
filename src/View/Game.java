package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import controller.CommandCenter;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Simulator;



public class Game extends JFrame implements MouseListener{
	
	private ImagePanel panel;
	private JPanel TopBar;
	private JPanel MidArea;
	private JPanel midWest;
	private JPanel midEast;
	private JPanel Grid;
	private JTextPane log;
	private ImagePanel currCell;
	private JPanel label1;
	private JPanel label2;
	private JPanel label3;
	private JPanel label4;
	private JPanel label5;
	private JPanel midEastCenter;
	private JPanel midWestTop;
	private JPanel midWestBottom;
	private JPanel midEastTop;
	private JPanel midEastBottom;
	private ImagePanel infoPanel;
	private ImagePanel contentPanel;
	private ImagePanel unitPanel;
	private ImagePanel unitPanel2;
	private ImagePanel unitPanel3;
	private JLabel availableUnits;
	private JLabel respondingunits;
	private ImageIcon[] DayNightCycle;
	private ImagePanel[][] GridCells;
	private int CurrentCycle = 0;
	private JLabel backButton;
	private JLabel mutemusic;
	private Clip GameMusic;
	
	public Game() throws Exception {
		
	this.setSize(1425,802);
	ImageIcon icon = new ImageIcon("icons/MainIcon.jpg");
	setIconImage(icon.getImage());
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//---------------------------------------------------------------------------	
	//Music
	try {
		GameMusic =PlaySound("sounds/Reach the summit.wav");
		FloatControl volume= (FloatControl) GameMusic.getControl(FloatControl.Type.MASTER_GAIN); 
		volume.setValue(-25.0f);
		GameMusic.start();
		GameMusic.loop(Clip.LOOP_CONTINUOUSLY);
	} catch (UnsupportedAudioFileException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (LineUnavailableException e) {
		e.printStackTrace();
	}
//---------------------------------------------------------------------------
	//Main panel (background)(parent)
	panel = new ImagePanel("icons/Game panel/cycle0.png");
	panel.setSize(1425,802);
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//---------------------------------------------------------------------------		
	//Top Bar	
	TopBar = new ImagePanel("icons/Game panel/Bar5.png");
	TopBar.setSize(1425,60);
	TopBar.setLayout(new BoxLayout(TopBar, BoxLayout.LINE_AXIS));
	panel.add(TopBar);
	panel.add(Box.createRigidArea(new Dimension(0,10)));
		//Top Bar buttons
		ImageIcon backI=new ImageIcon("icons/Game panel/BackButton.png");
		backButton=new JLabel(backI);
		backButton.addMouseListener(this);
		backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		TopBar.add(Box.createRigidArea(new Dimension(15,0)));
		TopBar.add(backButton);
		
		ImageIcon MuteI=new ImageIcon("icons/Game panel/Mute.png");
		mutemusic=new JLabel(MuteI);
		mutemusic.addMouseListener(this);
		mutemusic.setAlignmentX(Component.LEFT_ALIGNMENT);
		TopBar.add(Box.createRigidArea(new Dimension(15,0)));
		TopBar.add(mutemusic);
//---------------------------------------------------------------------------
		
	//Mid Area
	MidArea = new JPanel();
	MidArea.setSize(1425, 502);
	MidArea.setOpaque(false);
	MidArea.setLayout(new BoxLayout(MidArea, BoxLayout.LINE_AXIS));
	MidArea.setBackground(Color.black);
	panel.add(MidArea);
	
	//Mid west
	midWest = new JPanel();
	//midWest.setSize(500, 675);
	midWest.setOpaque(false);
	midWest.setLayout(new BoxLayout(midWest, BoxLayout.Y_AXIS));
	midWest.setBackground(Color.black);
	MidArea.add(midWest);
	//midWest.add(Box.createRigidArea(new Dimension(0,10)));
		
	
		//Mid west contents
	    label4 = new JPanel();
	    label4.setLayout(new BoxLayout(label4, BoxLayout.LINE_AXIS));
	    label4.setOpaque(false);
	    label4.setSize(300, 16);
	    midWest.add(label4);
	    respondingunits = new JLabel("",JLabel.LEFT);
	    ImageIcon DIcon = new ImageIcon("icons/Game panel/log.png");
	    respondingunits.setIcon(DIcon);
	    respondingunits.setAlignmentX(Component.LEFT_ALIGNMENT);
	    label4.add(respondingunits);
	    midWest.add(Box.createRigidArea(new Dimension(0,10)));
	    
		midWestTop = new JPanel();
		midWestTop.setLayout(new BoxLayout(midWestTop, BoxLayout.LINE_AXIS));
		midWestTop.setSize(500, 675/2);
		midWestTop.setOpaque(false);
		midWestTop.add(Box.createRigidArea(new Dimension(10,0)));
		midWest.add(midWestTop);
		
		midWest.add(Box.createRigidArea(new Dimension(0,25)));
		
		label5 = new JPanel();
	    label5.setLayout(new BoxLayout(label5, BoxLayout.LINE_AXIS));
	    label5.setOpaque(false);
	    label5.setSize(300, 16);
	    midWest.add(label5);
	    respondingunits = new JLabel("",JLabel.LEFT);
	    ImageIcon EIcon = new ImageIcon("icons/Game panel/info.png");
	    respondingunits.setIcon(EIcon);
	    respondingunits.setAlignmentX(Component.LEFT_ALIGNMENT);
	    label5.add(respondingunits);
	    midWest.add(Box.createRigidArea(new Dimension(0,10)));
		
		midWestBottom = new JPanel();
		midWestBottom.setLayout(new BoxLayout(midWestBottom, BoxLayout.LINE_AXIS));
		midWestBottom.setSize(500, 675/2);
		midWestBottom.setOpaque(false);
		midWestBottom.add(Box.createRigidArea(new Dimension(10,0)));
		midWest.add(midWestBottom);
		
			//Mid west Top contents
			infoPanel = new ImagePanel("icons/Game panel/InfoPanel.png");
			infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
			infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			midWestTop.add(infoPanel);
				//info panel contents
				log = new JTextPane();
				log.setSize(500, 216);
				log.setPreferredSize(log.getSize());
				log.setMaximumSize(log.getPreferredSize());
				log.add(Box.createRigidArea(new Dimension(0,50)));
				log.add(Box.createRigidArea(new Dimension(50,0)));
				infoPanel.add(log);
				log.setAlignmentX(CENTER_ALIGNMENT);
			
			//Mid west Bottom contents
			contentPanel = new ImagePanel("icons/Game panel/InfoPanel.png");
			contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			midWestBottom.add(contentPanel);
			
			
		//Grid (Mid center)
		GridCells = new ImagePanel[10][10];
		
		Grid = new JPanel();
		Grid.setOpaque(false);
		Grid.setSize(675,675);
		Grid.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(1, 1, 1, 1);
		
		for (int i = 0; i < 10; i++) {
			
			for (int j = 0; j < 10; j++) {
				ImagePanel cell = new ImagePanel("icons/Game panel/green.png");
				cell.addMouseListener(this);
				//TODO add properties to each cell later
				constraints.gridx = i;
				constraints.gridy = j;
				cell.setXI(i);
				cell.setYI(j);
				Grid.add(cell,constraints);
				GridCells[i][j] = cell;
			}
		}
	MidArea.add(Grid);
		
	MidArea.add(Grid);
		
	//Middle east 
	midEast = new JPanel();
	midEast.setSize(500, 675);
	midEast.setOpaque(false);
	midEast.setLayout(new BoxLayout(midEast, BoxLayout.Y_AXIS));
	midEast.setBackground(Color.black);
	MidArea.add(midEast);
	midEast.add(Box.createRigidArea(new Dimension(0,10)));
		//Middle east contents
		label1 = new JPanel();
		label1.setLayout(new BoxLayout(label1, BoxLayout.LINE_AXIS));
		label1.setOpaque(false);
		label1.setSize(300, 16);
		midEast.add(label1);
		availableUnits = new JLabel("",JLabel.LEFT);
		ImageIcon AIcon = new ImageIcon("icons/Game panel/Available Units.png");
		availableUnits.setIcon(AIcon);
		availableUnits.setAlignmentX(Component.LEFT_ALIGNMENT);
		label1.add(availableUnits);
		midEast.add(Box.createRigidArea(new Dimension(0,10)));
	
		midEastTop = new JPanel();
		midEastTop.setLayout(new BoxLayout(midEastTop, BoxLayout.LINE_AXIS));
		midEastTop.setSize(500, 675/2);
		midEastTop.setOpaque(false);
		midEast.add(midEastTop);
		
		midEast.add(Box.createRigidArea(new Dimension(0,30)));
		
		label2 = new JPanel();
		label2.setLayout(new BoxLayout(label2, BoxLayout.LINE_AXIS));
		label2.setOpaque(false);
		label2.setSize(300, 16);
		midEast.add(label2);
		respondingunits = new JLabel("",JLabel.LEFT);
		ImageIcon BIcon = new ImageIcon("icons/Game panel/respondingunits.png");
		respondingunits.setIcon(BIcon);
		respondingunits.setAlignmentX(Component.LEFT_ALIGNMENT);
		label2.add(respondingunits);
		midEast.add(Box.createRigidArea(new Dimension(0,10)));
		
		midEastCenter = new JPanel();
		midEastCenter.setLayout(new BoxLayout(midEastCenter, BoxLayout.LINE_AXIS));
		midEastCenter.setSize(500, 675/2);
		midEastCenter.setOpaque(false);
		midEast.add(midEastCenter);
		
		midEast.add(Box.createRigidArea(new Dimension(0,30)));
		
		label3 = new JPanel();
		label3.setLayout(new BoxLayout(label3, BoxLayout.LINE_AXIS));
		label3.setOpaque(false);
		label3.setSize(300, 16);
		midEast.add(label3);
		respondingunits = new JLabel("",JLabel.LEFT);
		ImageIcon CIcon = new ImageIcon("icons/Game panel/treatingunits.png");
		respondingunits.setIcon(CIcon);
		respondingunits.setAlignmentX(Component.LEFT_ALIGNMENT);
		label3.add(respondingunits);
		midEast.add(Box.createRigidArea(new Dimension(0,10)));
		
		midEastBottom = new JPanel();
		midEastBottom.setLayout(new BoxLayout(midEastBottom, BoxLayout.LINE_AXIS));
		midEastBottom.setSize(500, 675/2);
		midEastBottom.setOpaque(false);
		midEast.add(midEastBottom);
		
			//Middle east Top contents
			unitPanel = new ImagePanel("icons/Game panel/UnitPanel.png");
			unitPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			midEastTop.add(unitPanel);
			midEastTop.add(Box.createRigidArea(new Dimension(10,0)));
			
			//Middle east Center contents
			unitPanel3 = new ImagePanel("icons/Game panel/UnitPanel.png");
			unitPanel3.setAlignmentX(Component.RIGHT_ALIGNMENT);
			midEastCenter.add(unitPanel3);
			midEastCenter.add(Box.createRigidArea(new Dimension(10,0)));
			
			//Middle east Bottom contents
			unitPanel2 = new ImagePanel("icons/Game panel/UnitPanel.png");
			unitPanel2.setAlignmentX(Component.RIGHT_ALIGNMENT);
			midEastBottom.add(unitPanel2);
			midEastBottom.add(Box.createRigidArea(new Dimension(10,0)));


	//---------------------------------------------------------------------------	
		
	//fill images
	DayNightCycle = new ImageIcon[8];
	for (int i = 0; i < 8; i++) 
		DayNightCycle[i] = new ImageIcon("icons/Game panel/cycle"+i+".png");
	//visibility
	add(panel);
	setVisible(true);
	setResizable(false);
	}	
	
	
	
	public void UpdateCycleImg(ImagePanel p) {
		int indx = CurrentCycle%8;
		p.setImage(DayNightCycle[indx]);
	}
	
	public Clip PlaySound(String dir) throws UnsupportedAudioFileException, IOException, LineUnavailableException {    
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(dir).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		return clip;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==backButton) {
			GameMusic.stop();
			MainMenu menu=new MainMenu();
			this.dispose();
		}		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==backButton) {
			backButton.setIcon(new ImageIcon("icons/Game panel/BackButton1.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==backButton) {
			backButton.setIcon(new ImageIcon("icons/Game panel/BackButton.png"));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getSource() instanceof ImagePanel)
			((ImagePanel) e.getSource()).setImage(new ImageIcon("icons/Game panel/green_pressed.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof ImagePanel)
			((ImagePanel) e.getSource()).setImage(new ImageIcon("icons/Game panel/green.png"));
		
	}
	
		

}

