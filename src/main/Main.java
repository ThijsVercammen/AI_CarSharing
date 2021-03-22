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
		ArrayList<Auto> autos = new ArrayList<Auto>();
		ArrayList<Request> requests = new ArrayList<Request>();
		Map<String, ArrayList<String>> zones = new HashMap<>();
		int days = 0;
		
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
					for (int i = 0; i< Integer.parseInt(word[1].replaceAll("\\D+","")); i++) {
						Request r = new Request();
						
						line = scan.next();
						String[] r1 = line.split(";");
						
						r.setId(r1[0]);
						//System.out.println("ID: " + r1[0] + "\n");
						r.setZone(r1[1]);
						r.setDag(Integer.parseInt(r1[2]));
						r.setStart(Integer.parseInt(r1[3]));
						r.setDuur(Integer.parseInt(r1[4]));
						r.setP1(Integer.parseInt(r1[6]));
						r.setP2(Integer.parseInt(r1[7].replaceAll("\\D+","")));
						ArrayList<String> a = new ArrayList<String>();
						String[] r2 = r1[5].split(",");
						
						for (int j = 0; j< r2.length; j++) {
							a.add(r2[j]);
						}
						r.setAutos(a);
						requests.add(r);
					}
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
					for (int i = 0; i< Integer.parseInt(word[1].replaceAll("\\D+","")); i++) {
						line = scan.next();
						autos.add(new Auto("car"+i, 0));
					}
				}
				
				if(word[0].equals("+Days")) {
					days = Integer.parseInt(word[1].replaceAll("\\D+",""));
				}
			}
			//System.out.print("++++++" + zones.get("z3") + "\n");  //find and returns the next complete token from this scanner  
		}   
		scan.close();  //closes the scanner  
		
		Algoritme algo = new Algoritme(autos, requests, zones, days);
		Oplossing finaal = algo.lokaalZoeken();
		finaal.printOplossing();
	}
	
	

}
