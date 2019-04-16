package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.msg=msg;
		JLabel m=new JLabel(msg);
		ImageIcon img=new ImageIcon("icons/error.png");
		setIconImage(img.getImage());
		m.setFont(new Font("Verdana",Font.BOLD, 16));
		this.add(m);
		m.setSize(this.getSize());
		
	}

}
