package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chatwindow extends JFrame{
	private String ip;
	private int port;
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


	public int getPort() {
		return port;
	}


	public Chatwindow() {
		// variables initialization
		IPinput in=new IPinput();
		this.ip=in.getIp();
		this.port=in.getPort();
		
		//setting the frame
		setSize(800, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//ImageIcon img=new ImageIcon("icons/error.png");
		//setIconImage(img.getImage());
		this.setVisible(true);
		
		//components
		JPanel chat=new JPanel();
		chat.setSize(800,600);
		chat.setLayout(new BoxLayout(chat,BoxLayout.PAGE_AXIS));
		chat.setAlignmentX(CENTER_ALIGNMENT);
		this.chatText=new JTextArea();
		chatText.setSize(800,500);
		chatText.setPreferredSize(chatText.getSize());
		chatText.setMaximumSize(chatText.getSize());
		chatText.setEditable(false);
		JScrollPane c=new JScrollPane();
		c.setSize(chatText.getSize());
		c.setAlignmentX(CENTER_ALIGNMENT);
		c.add(chatText);
		chat.add(c);
		this.inText=new JTextField();
		inText.setSize(800,100);
		inText.setPreferredSize(inText.getSize());
		inText.setMinimumSize(inText.getSize());
		inText.setAlignmentX(CENTER_ALIGNMENT);
		inText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String input=inText.getText();
				chatText.setText(chatText.getText()+"\n"+input);
				inText.setText(null);
			}
		});
		chat.add(inText);
		
		//adding to frame
		this.add(chat);

	}
	public static void main(String[] args) {
		new Chatwindow();
	}
}
