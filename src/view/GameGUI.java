package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import controller.CommandCenter;

public class GameGUI extends JFrame implements MouseListener{
	private MainPanel panel;
	private ImageIcon[] DayNightCycle;
	private int CurrentCycle = 0;
	private Clip GameMusic;
	private CommandCenter controller;
	
	public CommandCenter getController() {
		return controller;
	}



	public void setController(CommandCenter controller) {
		this.controller = controller;
	}



	public GameGUI(CommandCenter Controller) throws Exception {
	this.setSize(1425,802);
	this.setController(controller);
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
	panel = new MainPanel("icons/Game panel/cycle0.png",this);
//---------------------------------------------------------------------------	
	
	//fill day/night cycle images
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
		if(e.getSource() == panel.getTopBar().getBackButton()) {
			GameMusic.stop();
			MainMenu menu=new MainMenu(this.getController());
			this.dispose();
		}		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == panel.getTopBar().getBackButton()) {
			panel.getTopBar().getBackButton().setIcon(new ImageIcon("icons/Game panel/BackButton1.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == panel.getTopBar().getBackButton()) {
			panel.getTopBar().getBackButton().setIcon(new ImageIcon("icons/Game panel/BackButton.png"));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getSource() instanceof Cell)
			((ImagePanel) e.getSource()).setImage(new ImageIcon("icons/Game panel/green_pressed.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() instanceof Cell)
			((ImagePanel) e.getSource()).setImage(new ImageIcon("icons/Game panel/green.png"));
		
	}
	
		

}

