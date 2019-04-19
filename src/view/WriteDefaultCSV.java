package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WriteDefaultCSV {
	public WriteDefaultCSV() {
		try (PrintWriter writer = new PrintWriter(new File("buildings.csv"))) {

		      writer.write("2,2\n" + "5,3\n" + "7,1");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		
		try (PrintWriter writer = new PrintWriter(new File("citizens.csv"))) {

		      writer.write("6,3,123456789,Test1,20\n" + "5,3,963852741,Test2,30\n" + "1,7,789456123,Test3,25\n" + 
		    		  "2,2,78946123,Test4,25\n" + "2,2,7856123,Test5,25");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		
		try (PrintWriter writer = new PrintWriter(new File("disasters.csv"))) {

		      writer.write("2,FIR,2,2\n" + "3,INJ,123456789\n" + "5,GLK,5,3\n" + "5,INF,789456123");


		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		
		try (PrintWriter writer = new PrintWriter(new File("units.csv"))) {

		      writer.write("AMB,1,2\n" + "DCU,2,4\n" + "EVC,3,6,8\n" + "FTK,4,5\n" + "GCU,5,4");


		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	}
}
