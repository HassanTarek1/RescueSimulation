package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;


public class WriteCSV {
	private boolean[][] taken;
	private String[] iDs;
	private boolean[] takenIds;
	public WriteCSV(int Buildings,int Citizens, int Disasters,int Units) {
		taken = new boolean[10][10];
		takenIds = new boolean[Citizens];
		iDs = new String[Citizens];
		System.out.println(taken.length);
	    try (PrintWriter writer = new PrintWriter(new File("buildings.csv"))) {

	      StringBuilder sb = new StringBuilder();
	      
	      //buildings
	      for (int i = 0; i < Buildings && !allTaken(taken); i++) {
			int x = Random0to9();
			int y = Random0to9();
				
			while(taken[x][y]) {
				x = Random0to9();
				y = Random0to9();
			}
			
			taken[x][y] = true; 
			sb.append(x+","+y+"\n");
		}
	     writer.write(sb.toString());

	    } catch (FileNotFoundException e) {
	      System.out.println(e.getMessage());
	    }
	    //---------------
	    
	    //citizens
	    try (PrintWriter writer = new PrintWriter(new File("citizens.csv"))) {

		      StringBuilder sb = new StringBuilder(); 
		      
		      for (int i = 0; i < Citizens; i++) {
					int x = Random0to9();
					int y = Random0to9();
					iDs[i] = RandomNationalId();
					sb.append(x+","+y+","+iDs[i]+","+RandomName()+","+RandomAge()+"\n");
				}
		      writer.write(sb.toString());
		      
			
	    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	    //------------
	    //Disasters
	    try (PrintWriter writer = new PrintWriter(new File("disasters.csv"))) {
	    	Random random = new Random();
		      StringBuilder sb = new StringBuilder(); 
		      
		      for (int i = 0; i < Disasters; i++) {
		    	  	int type = random.nextInt(2);
		    	  	if(type == 0 && !allIdsTaken(takenIds)) {
		    	  		
		    	  		int indx = random.nextInt(Citizens);
		    	 	
		    	  		while(takenIds[indx]) {
							indx = random.nextInt(Citizens);
						}
		    	  		
		    	  		takenIds[indx] = true;
		    	  		
		    	  		
		    	  		
					sb.append(RandomStartCycle()+","+RandomDisaster(type)+","+iDs[indx]+"\n");
		    	  	}
		    	 
		    	  	if(type == 1) {
	  	
		    	  		int x = Random0to9();
		    	  		int y = Random0to9();
		    	  		
		    	  		while(!taken[x][y]) {
							x = Random0to9();
							y = Random0to9();
						}
		    	  			    	  		
					sb.append(RandomStartCycle()+","+RandomDisaster(type)+","+x+","+y+"\n");
		    	  	}
				}
		      
		      writer.write(sb.toString());      
			
	    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	    //-------------
	    //Units
	    try (PrintWriter writer = new PrintWriter(new File("units.csv"))) {
	    	Random random = new Random();
		      StringBuilder sb = new StringBuilder(); 
		      
		      for (int i = 0; i < Units; i++) {
		    	  	String unit = RandomUnit();
		    	  	if (unit.equals("EVC")) {
		    	  		sb.append(unit+","+RandomUnitId()+","+(random.nextInt(10)+1)+","+(random.nextInt(14)+2)+"\n");
					}
		    	  	else {
		    	  		sb.append(unit+","+RandomUnitId()+","+(random.nextInt(10)+1)+"\n");
					}
					
				}
		      writer.write(sb.toString());
		      
			
	    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	    
	    

	  }
	
	public static int Random0to9() {
		Random r = new Random();
		return r.nextInt(10);
	}
	
	public static int RandomAge() {
		Random r = new Random();
		return r.nextInt(86) + 1;
	}
	
	public static boolean AllNotTaken(boolean[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static String RandomDisaster(int type) {
		Random r = new Random();
		int i = r.nextInt(2);

		if (type == 0) {
			switch (i) {
			case 0: return "INJ";
			case 1: return "INF";
			default:
			break;
			}
		}
		if (type == 1) {
			switch (i) {
			case 0: return "FIR";
			case 1: return "GLK";
			default:
			break;
			}
		}
	
		
		
		return "INJ";
	}
	
	public static String RandomUnit() {
		Random r = new Random();
		int i = r.nextInt(5);
		
		switch (i) {
		case 0: return "AMB";
		case 1: return "DCU";
		case 2: return "EVC";
		case 3: return "FTK";
		case 4: return "GCU";
		default:
		break;
		}
		
		return "FTK";
	}
	
	public static boolean  allIdsTaken(boolean[] arr) {
		for (boolean b : arr) {
			if (!b) {
				return false;
			}
		}
		return true;
	}
	
	public static String RandomName() {
		Random r = new Random();
		String name = "" + (char) (r.nextInt(26) + 65);
		for (int i = 0; i < 4; i++) {
			name+= (char) (r.nextInt(26) + 97);
		}
		return name;
	}
	
	public static int RandomStartCycle() {
		Random r = new Random();
		return r.nextInt(21);
	}
	
	public static String RandomNationalId() {
		String ret = "";
		for (int i = 0; i < 9; i++) {
			ret+= Random0to9();
		}
		return ret;
	}
	
	public static String RandomUnitId() {
		String ret = "";
		for (int i = 0; i < 5; i++) {
			ret+= Random0to9();
		}
		return ret;
	}
	
	public static boolean allTaken(boolean[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (!arr[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}
