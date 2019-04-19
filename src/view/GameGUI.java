package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle.Control;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.CommandCenter;
import model.infrastructure.ResidentialBuilding;
import model.units.UnitState;

public class GameGUI extends JFrame implements MouseListener{
	private MainPanel panel;
	private view.GameOver GameOver;
	private ImageIcon[] DayNightCycle;
	private int CurrentCycle = 0;
	private Clip GameMusic;
	private CommandCenter controller;
	private String[] endCycleSound = {"sounds/confirm_style_2_echo_001.wav","sounds/confirm_style_2_echo_002.wav"
			,"sounds/confirm_style_2_echo_003.wav","sounds/confirm_style_2_echo_004.wav","sounds/confirm_style_2_echo_005.wav"};
	public String[] getEndCycleSound() {
		return endCycleSound;
	}
	private FloatControl volume;



	public CommandCenter getController() {
		return controller;
	}



	public FloatControl getVolume() {
		return volume;
	}



	public void setVolume(FloatControl volume) {
		this.volume = volume;
	}



	public void setController(CommandCenter controller) {
		this.controller = controller;
	}



	public MainPanel getPanel() {
		return panel;
	}



	


	
	public ImageIcon[] getDayNightCycle() {
		return DayNightCycle;
	}



	
	public int getCurrentCycle() {
		return CurrentCycle;
	}



	
	public Clip getGameMusic() {
		return GameMusic;
	}



	public GameGUI(CommandCenter Controller) throws Exception {
	this.setSize(1425,802);
	this.controller=Controller;
	ImageIcon icon = new ImageIcon("icons/MainIcon.jpg");
	setIconImage(icon.getImage());
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//---------------------------------------------------------------------------	
	//Music
	try {
		GameMusic =PlaySound("sounds/First steps.wav");
		volume= (FloatControl) GameMusic.getControl(FloatControl.Type.MASTER_GAIN); 
		GameMusic.start();
		volume.setValue(-1000.0f);
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
	panel = new MainPanel("icons/Game panel/cycle0.png",this,controller);
	GameOver = new GameOver(controller);
	GameOver.addMouseListener(controller);
	GameOver.addMouseListener(this);
//---------------------------------------------------------------------------	
	
	//fill day/night cycle images
	DayNightCycle = new ImageIcon[8];
	for (int i = 0; i < 8; i++) 
		DayNightCycle[i] = new ImageIcon("icons/Game panel/cycle"+i+".png");
	
	//visibility
	this.setGlassPane(GameOver);
	GameOver.repaint();
	//GameOver.setVisible(true);
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
		if(e.getSource() == panel.getTopBar().getBackButton()) {
			
			GameMusic.stop();
			
			
			try {
				CommandCenter newc = new CommandCenter();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			this.dispose();
		}
		else if(e.getSource() == panel.getTopBar().getMuteButton()) {
			Button mute=panel.getTopBar().getMuteButton();
			if(GameMusic.isActive()) {
				mute.setIcon(new ImageIcon("icons/Game panel/mute1.png"));
				GameMusic.stop();
			}
			else {
				mute.setIcon(new ImageIcon("icons/Game panel/mute.png"));
				GameMusic.start();
				GameMusic.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == panel.getTopBar().getBackButton()) {
			panel.getTopBar().getBackButton().setIcon(new ImageIcon("icons/Game panel/BackButton1.png"));
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
		else if(e.getSource() == panel.getTopBar().getMuteButton()) {
			if(GameMusic.isActive())
				panel.getTopBar().getMuteButton().setIcon(new ImageIcon("icons/Game panel/muteB.png"));
			else
				panel.getTopBar().getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute1B.png"));
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == panel.getTopBar().getBackButton()) {
			panel.getTopBar().getBackButton().setIcon(new ImageIcon("icons/Game panel/BackButton.png"));
		}
		else if(e.getSource() == panel.getTopBar().getMuteButton()) {
			if(GameMusic.isActive())
				panel.getTopBar().getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute.png"));
			else
				panel.getTopBar().getMuteButton().setIcon(new ImageIcon("icons/Game panel/mute1.png"));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
	public void nextCycleGUI() {
		this.CurrentCycle++;
		UpdateCycleImg(panel);
		controller.updateCitizens(this);
		controller.updateBuildings(this);
		controller.updateUnitCount(UnitState.IDLE);
		controller.updateUnitCount(UnitState.RESPONDING);
		controller.updateUnitCount(UnitState.TREATING);
		controller.updatetopBar();
		if(controller.getEngine().checkGameOver()) {
			controller.updatetopBar();
			GameOver.getCasualties().setText("Casualties: "+controller.getEngine().calculateCasualties());
			GameOver.setVisible(true);
			
			this.GameMusic.stop();
			
			try {
				PlaySound("sounds/Devlin Bataric - Game Over - Repeating Dream.wav").start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
	}



	public view.GameOver getGameOver() {
		return GameOver;
	}

}

