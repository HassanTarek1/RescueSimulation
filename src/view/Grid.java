package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Grid extends JPanel{
	private Cell[][] Cells;
	private MouseListener listener; 
	
	public Cell[][] getCells() {
		return Cells;
	}
	
	private GridBagConstraints constraints;
	public Grid(MouseListener e) {
		super();
		
		listener = e;
		
		Cells = new Cell[10][10];
		
		setOpaque(false);
		setSize(675,675);
		setLayout(new GridBagLayout());
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(1, 1, 1, 1);
		
		for (int i = 0; i < 10; i++) {
			
			for (int j = 0; j < 10; j++) {
				Cell cell = new Cell("icons/Game panel/green.png",i,j);
				cell.addMouseListener(listener);
				//TODO add properties to each cell later
				constraints.gridx = i;
				constraints.gridy = j;
				add(cell,constraints);
				Cells[i][j] = cell;
			}
		}
	}
}
