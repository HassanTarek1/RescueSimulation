package view;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class MainPanel extends ImagePanel{
	private MouseListener listener;
	private TopBar TopBar;
	private MidArea midArea;
	private MouseListener cont;
	
	//getters
	public TopBar getTopBar() {
		return TopBar;
	}
	public MidArea getMidArea() {
		return midArea;
	}
	
	public MouseListener getListener() {
		return listener;
	}
	public MainPanel(String img,MouseListener e,MouseListener cont) throws FontFormatException, IOException {
		super(img);
		listener = e;
		setSize(1425,802);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.cont=cont;
		//TopBar
		TopBar = new TopBar("icons/Game panel/Bar5.png",listener,cont);
		add(TopBar);
		add(Box.createRigidArea(new Dimension(0,10)));

		//Mid Area
		midArea = new MidArea(listener,cont);
		add(midArea);
		
	}
}
