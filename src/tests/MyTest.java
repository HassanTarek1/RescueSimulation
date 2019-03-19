package tests;

import java.util.Random;

public class MyTest {
	public static void main(String[] args) {
		Random r=new Random();
		int rValue=(int)(6*r.nextDouble()+5);
		System.out.println(rValue);
	}

}
