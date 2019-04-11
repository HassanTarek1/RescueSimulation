package view;

import java.awt.Component;

import javax.swing.BoxLayout;

public class InfoPanel extends ImagePanel{
	public InfoPanel(String img) {
		super(img);
		
		//new
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.LEFT_ALIGNMENT);
	}
}
