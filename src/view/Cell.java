package view;

public class Cell extends ImagePanel{
	private int indxX;
	private int indxY;
	
	public Cell(String img,int X,int Y) {
		super(img);
		indxX = X;
		indxY = Y;
	}

	public int getIndxX() {
		return indxX;
	}

	public int getIndxY() {
		return indxY;
	}
}
