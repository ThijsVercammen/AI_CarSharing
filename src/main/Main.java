package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File(args[0]);
		Scanner scan = null;
		Map<String, ArrayList<String>> zones = new HashMap<>();
		
		// check if file exists
		//System.out.println(f.exists());
		
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.useDelimiter("\n");   //sets the delimiter pattern  

		while (scan.hasNext()) {  
			String line = scan.next();
			if(line.contains("+")) {
				String[] word = line.split(": ");
				
				if(word[0].equals("+Requests")) {
					//TODO
				}
				
				// add zones to hashmap
				if(word[0].equals("+Zones")) {
					for (int i = 0; i< Integer.parseInt(word[1].replaceAll("\\D+","")); i++) {
						zones.put("z"+i, new ArrayList<String>());
	
						line = scan.next();
						String[] z1 = line.split(";");
						ArrayList<String> aan_z = zones.get(z1[0]);
						String[] z2 = z1[1].split(",");
						
						for (int j = 0; j< z2.length; j++) {
							aan_z.add(z2[j]);
						}
					}
				}
				
				if(word[0].equals("+Vehicles")) {
					//TODO
				}
				
				if(word[0].equals("+Days")) {
					//TODO
				}
			}
			//System.out.print("++++++" + zones.get("z3") + "\n");  //find and returns the next complete token from this scanner  
		}   
		scan.close();  //closes the scanner  
	}

}
