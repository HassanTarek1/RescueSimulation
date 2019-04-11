package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {

	  private Image img;
	  private int indxX;
	  private int indxY;
	  


	public int getIndxX() {
		return indxX;
	}

	public void setIndxX(int indxX) {
		this.indxX = indxX;
	}

	public int getIndxY() {
		return indxY;
	}

	public void setIndxY(int indxY) {
		this.indxY = indxY;
	}

	public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
	  
	  public void setImage(ImageIcon img) {
		    this.img = img.getImage();
		    repaint();
		}

}

