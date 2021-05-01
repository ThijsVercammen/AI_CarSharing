package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
	

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//Get input-filename
		File f = new File(args[0]);
		System.out.println("Input file: " + args[0] + "\n");
		
		//Get output-filename
		String sol = args[1];
		System.out.println("Output file: " + args[1] + "\n");
		
		//Get max. runningtime
		int totalTime  = Integer.parseInt(args[2]);
		System.out.println("Time : " + args[2] + " seconds \n");
		
		//Random seed
		int random_seed = Integer.parseInt(args[3]);
		System.out.println("Random seed : " + random_seed + " (In dit algoritme maken we hier geen gebruik van)\n");
		
		//Get number of threads
		int threads = Integer.parseInt(args[4]);
		System.out.println("Aantal threads : " + threads + "\n");
		if(threads < 1 || threads > 2) {
			System.out.println("Aantal threads moet 1 of 2 zijn. Wordt nu met 1 thread uitgevoerd.\n");
			threads = 1;
		}
		
		//Create datastructures & filescanner
		Scanner scan = null;
		ArrayList<Auto> autos = new ArrayList<Auto>();
		ArrayList<Request> requests = new ArrayList<Request>();
		Map<String, ArrayList<String>> zones = new HashMap<>();
		int days = 0;
		
		//Try to open file
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scan.useDelimiter("\n");   //sets the delimiter pattern  

		//Read file and populate datastructures
		while (scan.hasNext()) {  
			String line = scan.next();
			if(line.contains("+")) {
				String[] word = line.split(": ");
				
				//Read requests
				if(word[0].equals("+Requests")) {
					for (int i = 0; i< Integer.parseInt(word[1].replaceAll("\\D+","")); i++) {
						Request r = new Request();
						
						line = scan.next();
						String[] r1 = line.split(";");
						
						r.setId(r1[0]);
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
				
				//Add zones to hashmap
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
				
				//Read vehicles
				if(word[0].equals("+Vehicles")) {
					for (int i = 0; i< Integer.parseInt(word[1].replaceAll("\\D+","")); i++) {
						line = scan.next();
						autos.add(new Auto("car"+i, 0));
					}
				}
				
				//Read in days
				if(word[0].equals("+Days")) {
					days = Integer.parseInt(word[1].replaceAll("\\D+",""));
				}
			}
		}   
		scan.close();  //closes the scanner  
		
		
		Oplossing o1 = null;
		Oplossing o2 = null;
		
		ExecutorService ex = Executors.newCachedThreadPool();
		ArrayList<Future<Oplossing>> t1 = new ArrayList<Future<Oplossing>>();
		
		// run threads
		for(int l = 0; l<threads; l++) {
			ArrayList<Auto> a2 = cloneAutos(autos);
			ArrayList<Request> r2 = cloneRequest(requests);
			Algoritme algo1 = new Algoritme(a2, r2, zones, days);
			Future<Oplossing> fCall = ex.submit(new OplThread(algo1, totalTime,l));
			t1.add(fCall);
		}
		
		//Get solution from threads
		int th = 0;
		for(Future<Oplossing> future : t1) {
			th++;
			o1 = future.get();
			System.out.println("Thread " + th + " : " + o1.getKost());
			if(o2 == null) {
				o2 = future.get();
			} else if(o1.getKost() < o2.getKost()) {
				o2 = future.get();
			}
		}
		
		//print best solution
		o2.printOplossing(sol);
		
		// shutdown threads
		ex.shutdown();
		
	}
	
	// copy auto and request list for each thread
	public static ArrayList<Auto> cloneAutos(ArrayList<Auto> list) {
	    ArrayList<Auto> clone = new ArrayList<Auto>(list.size());
	    for (Auto auto : list) {
	    	clone.add(new Auto(auto));
	    }
	    return clone;
	}
	public static ArrayList<Request> cloneRequest(ArrayList<Request> list) {
	    ArrayList<Request> clone = new ArrayList<Request>(list.size());
	    for (Request req : list) {
	    	clone.add(new Request(req));
	    }
	    return clone;
	}

}
