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
		setSize(500, 281);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon img=new ImageIcon("icons/error.png");
		setIconImage(img.getImage());
		this.setVisible(true);
		this.msg=msg;
		
		
		JLabel m=new JLabel(msg, SwingConstants.CENTER);
		m.setFont(new Font("Verdana",Font.BOLD, 18));
		m.setAlignmentX(CENTER_ALIGNMENT);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.add(Box.createRigidArea(new Dimension(500,100)));
		getContentPane().add(m);
		this.add(Box.createRigidArea(new Dimension(500,100)));

		pack();
		
		
	}
}
