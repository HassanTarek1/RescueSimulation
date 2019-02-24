package simulation;

public class Address {
<<<<<<< HEAD
	private int x;
	private int y;
	public Address() {
		// TODO Auto-generated constructor stub
=======

	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	Address(int x, int y){
		this.x = x;
		this.y = y;
>>>>>>> 86b68912815271906b6dfd991f8d0411e17114c6
	}
	public Address(int x, int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}
