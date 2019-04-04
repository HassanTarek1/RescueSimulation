package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame implements MouseListener{
	
	private JPanel panel;
	private JLabel NewGame;
	private JLabel Help;
	private JLabel About;
	private JLabel Quit;
	
	public MainMenu() {
		
		//Frame
		setSize(1250, 725);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("MainIcon.jpg");
		setIconImage(icon.getImage());
		
		
		//Panel
		panel = new ImagePanel("Main Menu Background(title).png");
		panel.setSize(1250, 725);
		panel.setBackground(Color.white);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
	
		//New Game
		NewGame = new JLabel("",JLabel.CENTER);
		ImageIcon nIcon = new ImageIcon("new game.png");
		NewGame.setIcon(nIcon);
		NewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		NewGame.setForeground(Color.DARK_GRAY);
		NewGame.addMouseListener(this);
		
		//Help
		Help = new JLabel("",JLabel.CENTER);
		ImageIcon hIcon = new ImageIcon("Help.png");
		Help.setIcon(hIcon);
		Help.setAlignmentX(Component.CENTER_ALIGNMENT);
		Help.setForeground(Color.DARK_GRAY);
		Help.addMouseListener(this);
		
		//About
		About = new JLabel("",JLabel.CENTER);
		ImageIcon aIcon = new ImageIcon("About.png");
		About.setIcon(aIcon);
		About.setAlignmentX(Component.CENTER_ALIGNMENT);
		About.setForeground(Color.DARK_GRAY);
		About.addMouseListener(this);
		
		//About
		Quit = new JLabel("",JLabel.CENTER);
		ImageIcon qIcon = new ImageIcon("Quit.png");
		Quit.setIcon(qIcon);
		Quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		Quit.setForeground(Color.DARK_GRAY);
		Quit.addMouseListener(this);
		
		
		//Layout
		panel.add(Box.createRigidArea(new Dimension(0,200)));
		panel.add(NewGame);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(Help);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(About);
		panel.add(Box.createRigidArea(new Dimension(0,50)));
		panel.add(Quit);
		
		
		
		//visibility
		add(panel);
		setVisible(true);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		
		if(temp == NewGame) {
			
		}
		else if (temp == About) {
			
		}
		
		else if (temp == Help) {
			
		}
		
		else if (temp == Quit) {
			dispose();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}

class ImagePanel extends JPanel {

	  private Image img;
	  
	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}

