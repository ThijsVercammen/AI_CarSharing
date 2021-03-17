package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Algoritme {
	
	ArrayList<Auto> autos = new ArrayList<Auto>();
	ArrayList<Request> requests = new ArrayList<Request>();
	Map<String, ArrayList<String>> zones = new HashMap<>();
	int days = 0;

	public Algoritme(ArrayList<Auto> autos, ArrayList<Request> requests, Map<String, ArrayList<String>> zones,
			int days) {
		this.autos = autos;
		this.requests = requests;
		this.zones = zones;
		this.days = days;
	}

	public void lokaalZoeken() {
		int k = 0;
		Oplossing o1 = startOplossing();
		o1 = valideerOplossing(o1);
		Oplossing best_o = o1;
		int best_cost = o1.getKost();
		
		// TODO - while aanpassen aan stop voorwaarden
//		while(k<10) {
			// TODO selecteer nieuwe oplossing (methode is leeg)
//			Oplossing o2 = selecteerOplossing();
//			o2 = valideerOplossing(o2);
			
			//TODO check dat element voldoet aan de selectie criteria
			
//			if(o2.getKost() < best_cost) {
//				best_o = o2;
//				best_cost = o2.getKost();
//			}
			
			// TODO - check of stopvoorwaarden zijn voldaan
			
//			k = k+1;
//		}
		
		System.out.println("KOST: " + best_cost +"\n");
		/*
		for(Auto a1 : o1.getReservaties()) {
			System.out.println("RESERVATIE: " + a1.getNaam()+"\n");
		}
		*/
	}
	
	public Oplossing startOplossing() {
		Oplossing o = new Oplossing();
		
		// alle autos in zone 0 plaatsen
		o.getToewijzingen().put("z0", autos);
		//o = valideerOplossing(o);
		return o;	
	}
	
	public Oplossing selecteerOplossing() {
		return null;	
	}
	
	public Oplossing valideerOplossing(Oplossing o) {
		Auto auto = null;
		boolean toegewezen = false;
		for(Request r : requests) {
			toegewezen = false;
			
			if(o.getToewijzingen().get(r.getZone()) == null) {
				// aangrenzende zones checken	
				for(String zone : zones.get(r.getZone())) {
					if(o.getToewijzingen().get(zone) != null && !toegewezen) {
						auto = getVrijeWagen(o.getToewijzingen().get(zone), r);
						o.setKost(o.getKost() + r.getP2());
						o.getReservaties().add(auto);
						toegewezen = true;
						System.out.println("Aanliggend: "+ r.getId() + " - " + auto.getNaam() + "\n");
					}
				}
			} else {
				auto = getVrijeWagen(o.getToewijzingen().get(r.getZone()), r);
				o.getReservaties().add(auto);
				toegewezen = true;
				System.out.println("Rechstreeks: "+ r.getId() + " - " + auto.getNaam() + "\n");
			}
			if(!toegewezen) {
				o.getNiet_toegewezen().add(auto);
				o.setKost(o.getKost() + r.getP1());
			}
		}
		return o;
	}
	
	// zoek vrije wagen in zone en wijzig tijd, geen gevonden return 0
	public Auto getVrijeWagen(ArrayList<Auto> a, Request r) {
		for(Auto auto : a) {
			//System.out.println(auto.getNaam() + " - " + auto.getWanneer_vrij() + " - " + ((r.getDag()*24*60) + r.getDuur()) +"\n" );
			if(auto.getWanneer_vrij() <= (r.getDag()*24*60) + r.getDuur() /*&& r.getAutos().contains(auto)*/) {
				for(String a2 : r.getAutos()) {
					if(auto.getNaam().equals(a2)) {
						auto.setWanneer_vrij((r.getDag()*24*60) + r.getDuur() + r.getStart());
						return auto;
					}
				}
			}
		}
		return null;
	}
}
