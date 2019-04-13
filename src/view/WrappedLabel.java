package view;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WrappedLabel extends JPanel{
	private JLabel label;
	public WrappedLabel(String text,String Img) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	    setOpaque(false);
	    setSize(300, 16);
	    
	    label = new JLabel(text,JLabel.LEFT);
	    label.setIcon(new ImageIcon(Img));
	    label.setAlignmentX(Component.LEFT_ALIGNMENT);
	    add(label);
	}
	public JLabel getLabel() {
		return label;
	}
}
