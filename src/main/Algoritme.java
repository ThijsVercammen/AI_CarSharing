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

	public Oplossing lokaalZoeken() {
		int k = 0;
		Oplossing o = startOplossing();
		o = valideerOplossing(o);
		Oplossing best_o = o;
		int best_cost = o.getKost();
		//System.out.println("KOST: " + o.getKost() +"\n");
		//System.out.println("------------------------------------------------" +"\n");
		
		// TODO - while aanpassen aan stop voorwaarden
		while(k<100000) {
			// TODO selecteer nieuwe oplossing (methode is leeg)
			o = selecteerOplossing(best_o);
			o = valideerOplossing(o);
			
			//TODO check dat element voldoet aan de selectie criteria
			
			if(o.getKost() < best_cost) {
				best_o = o;
				best_cost = o.getKost();
			}
			
			// TODO - check of stopvoorwaarden zijn voldaan
			
			k = k+1;
			//System.out.println("KOST: " + o.getKost() +"\n");
			//System.out.println("------------------------------------------------" +"\n");
		}
		
		System.out.println("KOST: " + best_cost +"\n");
		return best_o;
	}
	
	public Oplossing startOplossing() {
		Oplossing o = new Oplossing();		
		// alle autos in zone 0 plaatsen
		o.getToewijzingen().put("z0", this.autos);
		for(int i = 1; i<zones.keySet().size(); i++) {
			o.getToewijzingen().put("z"+i, new ArrayList<Auto>());
		}
		return o;	
	}
	
	public Oplossing selecteerOplossing(Oplossing o1) {
		Oplossing o = o1;
		o.setKost(0);
		//generate random number between 0 and 5 (5 excluded)
		int max = zones.keySet().size();
		int r1 = (int) (Math.random() * ( max ));
		while(o.getToewijzingen().get("z"+r1).size() <= 0) {
			r1 =  (int) (Math.random() * ( max ));
		}
		Auto a1 = o.getToewijzingen().get("z"+r1).get(0);

		o.getToewijzingen().get("z"+r1).remove(0);
		
		int r2 =  (int) (Math.random() * ( max ));
		while(r1 == r2) {
			r2 = (int) (Math.random() * ( max ));
		}
		
		o.getToewijzingen().get("z"+r2).add(a1);

		
		//System.out.println("RANDOM: " + r1 + " - " + r2 + "\n");
		return o;	
	}
	
	public Oplossing valideerOplossing(Oplossing o) {
		Auto auto = null;
		boolean toegewezen = false;
		for(Request r : requests) {
			toegewezen = false;
			
			if(o.getToewijzingen().get(r.getZone()).size() <= 0) {
				// aangrenzende zones checken	
				for(String zone : zones.get(r.getZone())) {
					auto = getVrijeWagen(o.getToewijzingen().get(zone), r);
					if(auto != null && !toegewezen) {
						//auto = getVrijeWagen(o.getToewijzingen().get(zone), r);
						o.setKost(o.getKost() + r.getP2());
						o.getReservaties().add(r);
						toegewezen = true;
						//System.out.println("Aanliggend: "+ r.getId() + " - " + auto.getNaam() + "\n");
					}
				}
			} else {
				auto = getVrijeWagen(o.getToewijzingen().get(r.getZone()), r);
				if(auto != null) {
					o.getReservaties().add(r);
					toegewezen = true;
					//System.out.println("Rechstreeks: "+ r.getId() + " - " + auto.getNaam() +"\n");
				}
				
			}
			if(!toegewezen) {
				o.getNiet_toegewezen().add(r);
				o.setKost(o.getKost() + r.getP1());
			}
		}
		return o;
	}
	
	// zoek vrije wagen in zone en wijzig tijd, geen gevonden return 0
	public Auto getVrijeWagen(ArrayList<Auto> a, Request r) {
		if(a == null) {
			return null;
		}
		for(Auto auto : a) {
			if(auto.getWanneer_vrij() <= (r.getDag()*24*60) + r.getDuur()) {
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
