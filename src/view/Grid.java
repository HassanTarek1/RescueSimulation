package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Grid extends JPanel{
	private Cell[][] Cells;
	private MouseListener listener; 
	private MouseListener cont;
	public Cell[][] getCells() {
		return Cells;
	}
	
	private GridBagConstraints constraints;
	public Grid(MouseListener e,MouseListener cont) {
		super();
		
		listener = e;
		this.cont=cont;
		Cells = new Cell[10][10];
		
		setOpaque(false);
		setSize(675,675);
		setLayout(new GridBagLayout());
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(1, 1, 1, 1);
		
		for (int i = 0; i < 10; i++) {
			
			for (int j = 0; j < 10; j++) {
				Cell cell;
				if (i == 0 && j == 0) {
					cell = new Cell("icons/Game panel/blue.png",j,i);
					cell.add(cell.getBase());
				}
				else {
					cell = new Cell("icons/Game panel/grey.png",j,i);
				}
				
				cell.addMouseListener(cont);
				//TODO add properties to each cell later
				constraints.gridx = i;
				constraints.gridy = j;
				add(cell,constraints);
				Cells[j][i] = cell;
			}
		}
	}
}
