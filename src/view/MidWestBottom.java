package view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MidWestBottom extends JPanel{
	private InfoPanel info;
	public MidWestBottom() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setSize(500, 675/2);
		setOpaque(false);
		add(Box.createRigidArea(new Dimension(10,0)));
		
		//Contents
		JTextArea textArea = new JTextArea();
		textArea.setFont(new java.awt.Font("Helvitica",1,14));
		textArea.setEditable(false);
		textArea.setMargin(new java.awt.Insets(12, 12, 12, 12));
		textArea.setSize(310,230);
		textArea.setEditable(false);
		info = new InfoPanel(textArea);
		add(info);
	}
	public InfoPanel getInfo() {
		return info;
	}
}
