package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout.Constraints;

import controller.CommandCenter;
import simulation.Simulator;


//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
//import sun.audio.AudioData;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
//import sun.audio.ContinuousAudioDataStream;

@SuppressWarnings("restriction")
public class MainMenu extends JFrame implements MouseListener{
	private CommandCenter controller;
	private ImagePanel panel;
	private ChoicePanel choicePanel;
	private About AboutPanel;
	private JLabel Title;
	private JLabel NewGame;
	private JLabel Help;
	private JLabel About;
	private JLabel Quit;
	private Clip intro;
	private int click = 0;
	private GameGUI game;
	private Button muteButton;
	
	public GameGUI getGame() {
		return game;
	}

	public CommandCenter getController() {
		return controller;
	}

	public void setController(CommandCenter controller) {
		this.controller = controller;
	}
	
	@SuppressWarnings("resource")
	public MainMenu(CommandCenter Controller) {
		
		//Frame
		setSize(1280, 720);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("icons/MainIcon.jpg");
		setIconImage(icon.getImage());
		this.controller = Controller;	
		
		//Main Panel
		panel = new ImagePanel("icons/Main menu/Main Menu Background(empty).png");
		panel.setSize(1280, 720);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		//choice panel
		choicePanel = new ChoicePanel("icons/Main menu/Main Menu Background(empty).png", this);
		add(choicePanel);
		
		//Title
		Title = new JLabel("",JLabel.CENTER);
		ImageIcon tIcon = new ImageIcon("icons/Main menu/Title.png");
		Title.setIcon(tIcon);
		Title.setAlignmentX(Component.CENTER_ALIGNMENT);
		Title.setForeground(Color.DARK_GRAY);
		Title.addMouseListener(this);
		
		//New Game Button
		NewGame = new JLabel("",JLabel.CENTER);
		ImageIcon nIcon = new ImageIcon("icons/Main menu/new game.png");
		NewGame.setIcon(nIcon);
		NewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		NewGame.setForeground(Color.DARK_GRAY);
		NewGame.addMouseListener(this);
		
		//Help Button
		Help = new JLabel("",JLabel.CENTER);
		ImageIcon hIcon = new ImageIcon("icons/Main menu/Help.png");
		Help.setIcon(hIcon);
		Help.setAlignmentX(Component.CENTER_ALIGNMENT);
		Help.setForeground(Color.DARK_GRAY);
		Help.addMouseListener(this);
		
		//About Button
		About = new JLabel("",JLabel.CENTER);
		ImageIcon aIcon = new ImageIcon("icons/Main menu/About.png");
		About.setIcon(aIcon);
		About.setAlignmentX(Component.CENTER_ALIGNMENT);
		About.setForeground(Color.DARK_GRAY);
		About.addMouseListener(this);
		
		//About panel
		AboutPanel = new About();
		
		
		//Quit Button
		Quit = new JLabel("",JLabel.CENTER);
		ImageIcon qIcon = new ImageIcon("icons/Main menu/Quit.png");
		Quit.setIcon(qIcon);
		Quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		Quit.setForeground(Color.DARK_GRAY);
		Quit.addMouseListener(this);
		
		//muteButton
		
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
		
		muteButton=new Button("icons/Game panel/mute.png");
		topLeftContainer.add(muteButton,constraints);
		muteButton.addMouseListener(this);
		muteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//Layout
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(topLeftContainer);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(Title);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(NewGame);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(Help);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(About);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(Quit);
		
		
		
		
		//visibility
		try {
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		add(AboutPanel);
		add(panel);
		
		setVisible(true);
		this.setLocationRelativeTo(null);
		
		//music
		try {
			intro =PlaySound("sounds/intro.wav");
			FloatControl volume= (FloatControl) intro.getControl(FloatControl.Type.MASTER_GAIN); 
			volume.setValue(-30.0f);
			intro.start();
			intro.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		JLabel temp = (JLabel) e.getSource();
		
		if(temp == Title) {
			click++;
			
			if(click == 5) {
				ImageIcon tIcon = new ImageIcon("icons/main menu/5.png");
				Title.setIcon(tIcon);
			}
			
			if(click == 10) {
				ImageIcon tIcon = new ImageIcon("icons/main menu/10.png");
				Title.setIcon(tIcon);
			}
			
			if(click == 15) {
				System.exit(0);
			}
		}
		
		else if(e.getSource() == choicePanel.getDefaultCSV()) {
			try {
				intro.stop();
				new WriteDefaultCSV();
				Simulator engine = new Simulator(controller);
				controller.setEngine(engine);
				controller.setEmergencyUnits(engine.getEmergencyUnits());
				game= new GameGUI(controller);
				controller.updatetopBar();
				this.dispose();
				//game.getGameMusic().stop();
				//game.setVisible(true);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		else if(e.getSource() == choicePanel.getRandomCSV()) {
			try {
				intro.stop();
				new WriteCSV(20,30,25,25);
				Simulator engine = new Simulator(controller);
				controller.setEngine(engine);
				controller.setEmergencyUnits(engine.getEmergencyUnits());
				game= new GameGUI(controller);
				controller.updatetopBar();
				this.dispose();
				//game.getGameMusic().stop();
				//game.setVisible(tr);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		else if(temp == NewGame) {
			//intro.stop();
			choicePanel.setVisible(true);
			panel.setVisible(false);
			
//			try {
//				game.setVisible(true);
//				game.getGameMusic().start();
//				game.getVolume().setValue(-25.0f);
//				game.getGameMusic().loop(Clip.LOOP_CONTINUOUSLY);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			this.dispose();
			
		}
		else if (temp == About) {
			panel.setVisible(false);
			AboutPanel.setVisible(true);
			
		}
		
		else if (temp == Help) {
			
		}
		
		else if (temp == Quit) {
			System.exit(0);
		}
		
		if(e.getSource() == choicePanel.getBackButton()) {
			choicePanel.setVisible(false);
			panel.setVisible(true);
		}
		
		else if(e.getSource() == muteButton || e.getSource() == choicePanel.getMuteButton()) {
			if(intro.isActive()) {
				if(e.getSource() == muteButton || e.getSource() == choicePanel.getMuteButton())
					
					try {
						PlaySound("sounds/confirm 1.wav").start();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
					}
				
				muteButton.setIcon(new ImageIcon("icons/Game panel/mute1.png"));
				choicePanel.getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute1.png"));
				intro.stop();
			}
			else {
				
				try {
					PlaySound("sounds/cancel 1.wav").start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}
				
				muteButton.setIcon(new ImageIcon("icons/Game panel/mute.png"));
				choicePanel.getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute.png"));
				intro.start();
				intro.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		

		if(temp == Title) {
			try {
				PlaySound("sounds/Quack.wav").start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getSource() != Title) {
			try {
				if(e.getSource() != muteButton)
					PlaySound("sounds/Hero.wav").start();	
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		
		if(temp == Title) {
			
		}
		
		else if(e.getSource() == choicePanel.getBackButton()) {
			choicePanel.getBackButton().setIcon(new ImageIcon("icons/Game panel/BackButton1.png"));
			try {
				PlaySound("sounds/Morse.wav").start();
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == choicePanel.getDefaultCSV()) {
			choicePanel.getDefaultCSV().setIcon(new ImageIcon("icons/Main menu/defaultCSV1.png"));
		}
		
		else if(e.getSource() == choicePanel.getRandomCSV()) {
			choicePanel.getRandomCSV().setIcon(new ImageIcon("icons/Main menu/RandomCSV1.png"));
		}
		
		else if(temp == NewGame) {
			ImageIcon nIcon = new ImageIcon("icons/Main menu/new game1.png");
			NewGame.setIcon(nIcon);
		}
		else if (temp == About) {
			ImageIcon aIcon = new ImageIcon("icons/Main menu/About1.png");
			About.setIcon(aIcon);
		}
		
		else if (temp == Help) {
			ImageIcon hIcon = new ImageIcon("icons/Main menu/Help1.png");
			Help.setIcon(hIcon);
		}
		
		else if (temp == Quit) {
			ImageIcon qIcon = new ImageIcon("icons/Main menu/Quit1.png");
			Quit.setIcon(qIcon);
		}
		else if(e.getSource() == muteButton || e.getSource() == choicePanel.getMuteButton()) {
			if(intro.isActive()) {
				muteButton.setIcon(new ImageIcon("icons/Game panel/muteB.png"));
				choicePanel.getMuteButton().setIcon(new ImageIcon("icons/Game panel/muteB.png"));
			}
			else {
				muteButton.setIcon(new ImageIcon("icons/Game panel/mute1B.png"));
				choicePanel.getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute1B.png"));
			}
		}
		
		if(temp != Title) {
			try {
				PlaySound("sounds/bottle.wav").start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		
		if(temp == NewGame) {
			ImageIcon nIcon = new ImageIcon("icons/Main menu/new game.png");
			NewGame.setIcon(nIcon);
		}
		
		else if(e.getSource() == choicePanel.getBackButton()) {
			choicePanel.getBackButton().setIcon(new ImageIcon("icons/Game panel/BackButton.png"));
		}
		
		else if(e.getSource() == choicePanel.getDefaultCSV()) {
			choicePanel.getDefaultCSV().setIcon(new ImageIcon("icons/Main menu/defaultCSV.png"));
		}
		
		else if(e.getSource() == choicePanel.getRandomCSV()) {
			choicePanel.getRandomCSV().setIcon(new ImageIcon("icons/Main menu/RandomCSV.png"));
		}
		
		else if (temp == About) {
			ImageIcon aIcon = new ImageIcon("icons/Main menu/About.png");
			About.setIcon(aIcon);
		}
		
		else if (temp == Help) {
			ImageIcon hIcon = new ImageIcon("icons/Main menu/Help.png");
			Help.setIcon(hIcon);
		}
		
		else if (temp == Quit) {
			ImageIcon qIcon = new ImageIcon("icons/Main menu/Quit.png");
			Quit.setIcon(qIcon);
		}
		else if(e.getSource() == muteButton || e.getSource() == choicePanel.getMuteButton()) {
			if(intro.isActive()) {
				muteButton.setIcon(new ImageIcon("icons/Game panel/mute.png"));
				choicePanel.getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute.png"));
			}
			else {
				muteButton.setIcon(new ImageIcon("icons/Game panel/mute1.png"));
				choicePanel.getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute1.png"));
			}
			
		}
	
	
	}
	
	
	public Clip PlaySound(String dir) throws UnsupportedAudioFileException, IOException, LineUnavailableException {    
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(dir).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		return clip;
	}
}