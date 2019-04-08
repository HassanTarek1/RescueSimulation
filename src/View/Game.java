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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



public class Game extends JFrame implements MouseListener{
	
	private ImagePanel panel;
	private JPanel TopBar;
	private JPanel MidArea;
	private JPanel Grid;
	private ImageIcon[] DayNightCycle;
	private ImagePanel[][] GridCells;
	private int CurrentCycle = 0;
	private JLabel backButton;
	private Clip GameMusic;
	
	public Game() {
		
	
	this.setSize(1425,802);
	ImageIcon icon = new ImageIcon("icons/MainIcon.jpg");
	setIconImage(icon.getImage());
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	
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
	
	//Main panel (background)(parent)
	panel = new ImagePanel("icons/cycle0.png");
	panel.setSize(1425,802);
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
	//Top Bar	
	TopBar = new JPanel();
	TopBar.setSize(1425,50);
	
	
	//
	//TODO remove later
	TopBar.setBackground(Color.red);
	TopBar.setLayout(new BorderLayout());
	//
	panel.add(TopBar);
	panel.add(Box.createRigidArea(new Dimension(0,25)));
	
	//Mid Area
	MidArea = new JPanel();
	MidArea.setSize(1425, 502);
	MidArea.setOpaque(false);
	MidArea.setLayout(new BoxLayout(MidArea, BoxLayout.LINE_AXIS));
	MidArea.setBackground(Color.black);
	panel.add(MidArea);
	
	//Grid
	GridCells = new ImagePanel[10][10];
	
	Grid = new JPanel();
	Grid.setOpaque(false);
	Grid.setSize(800,502);
	Grid.setLayout(new GridBagLayout());
	GridBagConstraints constraints = new GridBagConstraints();
	constraints.insets = new Insets(1, 1, 1, 1);
	
	for (int i = 0; i < 10; i++) {
		
		for (int j = 0; j < 10; j++) {
			ImagePanel cell = new ImagePanel("icons/Cell.png");
			//TODO add properties to each cells later
			constraints.gridx = i;
			constraints.gridy = j;
			Grid.add(cell,constraints);
			GridCells[i][j] = cell;
			constraints.gridy++;
		}
	}
	MidArea.add(Grid);
	
	
	
	
	
			
	//fill images
	DayNightCycle = new ImageIcon[8];
	for (int i = 0; i < 8; i++) 
		DayNightCycle[i] = new ImageIcon("icons/cycle"+i+".png");
	
	
	//visibility
	add(panel);
	setVisible(true);
	setResizable(false);
	
	//buttons
	ImageIcon backI=new ImageIcon("icons/BackButton.png");
	backButton=new JLabel("",backI , SwingConstants.LEFT);
	backButton.addMouseListener(this);
	
	TopBar.add(backButton);
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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==backButton) {
			GameMusic.stop();
			MainMenu menu=new MainMenu();
			this.dispose();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==backButton) {
			backButton.setIcon(new ImageIcon("icons/BackButton1.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==backButton) {
			backButton.setIcon(new ImageIcon("icons/BackButton.png"));
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

