package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MiniFrame extends JFrame{

	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public MiniFrame(String msg) {
		setSize(600, 281);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon img=new ImageIcon("icons/error.png");
		setIconImage(img.getImage());
		setTitle("UNEXPECTED MOVE");
		this.msg=msg;
		
		ImagePanel error = new ImagePanel("icons/Game panel/error.png");
		error.setSize(600,281);
		error.setLayout(new BoxLayout(error, BoxLayout.PAGE_AXIS));
		
		JLabel m1 = new JLabel("< ------- UNEXPECTED MOVE ------- >", JLabel.CENTER);
		m1.setFont(new Font("Verdana",Font.BOLD, 18));
		m1.setAlignmentX(CENTER_ALIGNMENT);
		m1.setOpaque(false);
		
		JLabel m=new JLabel(msg, SwingConstants.CENTER);
		m.setFont(new Font("Verdana",Font.BOLD, 18));
		m.setAlignmentX(CENTER_ALIGNMENT);
		m.setOpaque(false);
		
		JLabel m2 = new JLabel("< --------------------------------------- >", JLabel.CENTER);
		m2.setFont(new Font("Verdana",Font.BOLD, 18));
		m2.setAlignmentX(CENTER_ALIGNMENT);
		m2.setOpaque(false);
		
		error.add(Box.createRigidArea(new Dimension(0,60)));
		error.add(m1);
		error.add(Box.createRigidArea(new Dimension(0,10)));
		error.add(m);
		error.add(Box.createRigidArea(new Dimension(0,10)));
		error.add(m2);
		error.add(Box.createRigidArea(new Dimension(0,100)));

		this.add(error);
		this.setLocationRelativeTo(null);
		pack();
		this.setVisible(true);
	}
	/*public static void main (String[]args) {
		MiniFrame x = new MiniFrame("What are you doing. you can not heal a building");
	}*/
}
