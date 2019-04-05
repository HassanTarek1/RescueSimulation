package View;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Game extends JFrame {
	
	private ImagePanel panel;
	private ImageIcon[] DayNightCycle;
	private int CurrentCycle = 0;
	
	public Game() {
		
	
	this.setSize(1425,802);
	ImageIcon icon = new ImageIcon("icons/MainIcon.jpg");
	setIconImage(icon.getImage());
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	
	panel = new ImagePanel("icons/cycle0.png");
	
	panel.setSize(1425,802);
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	
	//fill images
	DayNightCycle = new ImageIcon[8];
	for (int i = 0; i < 8; i++) 
		DayNightCycle[i] = new ImageIcon("icons/cycle"+i+".png");
	
	
	//visibility
	add(panel);
	setVisible(true);
	setResizable(false);
	
	}
	
	public void UpdateCycleImg(ImagePanel p) {
		int indx = CurrentCycle%8;
		p.setImage(DayNightCycle[indx]);
	}

}
