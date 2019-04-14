package simulation;

public class Address {
	
	//variables
	private int x;
	private int y;
	
	
	//getters and setters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//constructors
	public Address(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public String toString() {
		return x+" , "+y;
	}
}
