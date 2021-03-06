package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

public class Chatwindow extends JFrame{
	private String ip;
	private JTextField inText;
	private JTextArea chatText;

	public JTextField getInText() {
		return inText;
	}


	public JTextArea getChatText() {
		return chatText;
	}


	public String getIp() {
		return ip;
	}




	public Chatwindow() throws FontFormatException, IOException {
		// variables initialization
		IPinput in=new IPinput();
		this.ip=in.getIp();
		
		//setting the frame
		setSize(800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImagePanel panel=new ImagePanel("icons/background.png");
		panel.setLayout(new BorderLayout());
		panel.setSize(this.getSize());
//		panel.setOpaque(true);
		add(panel);
//		this.setLocationRelativeTo(null);

		
		//components
		//Font f=new Font("Helvetica ", Font.BOLD,18);
		this.chatText=new JTextArea();
		chatText.setSize(800,500);
		chatText.setEditable(false);
		//chatText.setFont(f);
		chatText.setOpaque(true);
		chatText.setText("");
		InfoPanel c =new InfoPanel(chatText);
		c.getTextArea().setBackground(Color.white);
//		c.setSize(chatText.getSize());
		
//		c.setLayout(new ScrollPaneLayout());
//		c.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		c.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		c.setWheelScrollingEnabled(true);
//		c.setAlignmentY(CENTER_ALIGNMENT);
//		c.add(chatText);
//		c.setOpaque(true);
//		c.setVisible(true);
		
		inText= new JTextField();
//		inText.setBounds(0, 500, 800, 100);
		inText.setFont(c.getTextArea().getFont());
//		inText.setOpaque(false);
//		inText.setAlignmentY(CENTER_ALIGNMENT);
//		inText.setText("");
//		inText.setVisible(true);
		inText.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String input=inText.getText();
				chatText.setText(chatText.getText()+"\n"+input);
				chatText.setSize(chatText.getWidth(),chatText.getHeight()+10);
				inText.setText(null);
			}
			
		});
		panel.add(c,BorderLayout.NORTH);
		panel.add(inText);
		pack();
		this.setVisible(true);
		

	}
	public static void main(String[] args) throws FontFormatException, IOException {
		new Chatwindow();
	}
}
