package view;

import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class MainPanel extends ImagePanel{
	private MouseListener listener;
	private TopBar TopBar;
	private MidArea midArea;
	public TopBar getTopBar() {
		return TopBar;
	}
	public MidArea getMidArea() {
		return midArea;
	}
	public MainPanel(String img,MouseListener e) {
		super(img);
		listener = e;
		setSize(1425,802);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//TopBar
		TopBar = new TopBar("icons/Game panel/Bar5.png",listener);
		add(TopBar);
		add(Box.createRigidArea(new Dimension(0,10)));

		//Mid Area
		midArea = new MidArea(listener);
		add(midArea);
		
	}
}
