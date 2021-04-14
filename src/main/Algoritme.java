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

	public Oplossing lokaalZoeken(int totalTime) {
		long start = System.currentTimeMillis();
		long end = start + 1000*totalTime;
		int tot = 0;
		
		Oplossing o = startOplossing();
		o = valideerOplossing(o);
		Oplossing best_o = new Oplossing();
		
		//zet start oplossing als beste oplossing
		best_o.setKost(o.getKost());
		best_o.setNiet_toegewezen(o.getNiet_toegewezen());
		best_o.setReservaties(o.getReservaties());
		best_o.setToewijzingen(o.getToewijzingen());
		int best_cost = best_o.getKost();
		
		//zoek voor opgegeven tijd
		while( /*tot < 50 */ System.currentTimeMillis() < end) {
			//selecteer nieuwe oplossing
			o = selecteerOplossing(o);
			//valideer nieuwe oplossing
			o = valideerOplossing(o);
			/*
			for(String z : o.getToewijzingen().keySet()) {
				System.out.println("ZONE : " + z);
				for(Auto a : o.getToewijzingen().get(z)) {
					System.out.println(a.getNaam() + " - ");
				}
				//System.out.println("\n");
			}
			System.out.println("------");
			*/
			// vergelijk met nieuwe oplossing met beste oplossing
			if(o.getKost() <= best_cost) {
				best_o.setKost(o.getKost());
				best_o.setNiet_toegewezen(o.getNiet_toegewezen());
				best_o.setReservaties(o.getReservaties());
				best_o.setToewijzingen(o.getToewijzingen());
				best_cost = o.getKost();
				System.out.println("KOST: " + o.getKost() + " - " + best_o.getKost() + "\n");
				
				
				
			// behoud huidige beste oplossing
			} else {
				//System.out.println("geen verbetering \n");
				o.setToewijzingen(best_o.getToewijzingen());
			}
			

			tot++;
		}
		
		System.out.println("KOST: " + best_cost +"\n");
		System.out.println("TOTAAL: " + tot +"\n");
		
		for(String r : autos.get(11).getReservaties().keySet()) {
			System.out.println(autos.get(11).getReservaties().get(r).getStarttijd() + " - " + autos.get(11).getReservaties().get(r).getEindtijd() + "\n");
		}
		return best_o;
	}
	
	public Oplossing startOplossing() {
		Oplossing o = new Oplossing();	
		int i = 0;
		// alle autos in zone 0 plaatsen
		//o.getToewijzingen().put("z0", this.autos);
		
		// onderstaande start oplossing werkt alleen als #zones > #autos
		// auto1 -> zone1, auto2 -> zone2, ...
		for(i = 0; i<zones.keySet().size(); i++) {
			if(i<this.autos.size()) {
				ArrayList<Auto> a = new ArrayList<Auto>();
				a.add(this.autos.get(i));
				o.getToewijzingen().put("z"+i, a);
			} else {
				o.getToewijzingen().put("z"+i, new ArrayList<Auto>());
			}

		}
		return o;	
	}
	
	public Oplossing selecteerOplossing(Oplossing o) {
		// reset vorige validatie
		o.setKost(0);
		o.setNiet_toegewezen(new ArrayList<Request>());
		o.setReservaties(new ArrayList<Request>());
		
		//genereer willekeurig nummer tussen 0 en het #zones
		int max = zones.keySet().size();
		int r1 = (int) (Math.random() * ( max ));
		//als zone geen auto's heeft, genereer nieuw willekeurig nummer
		while(o.getToewijzingen().get("z"+r1).size() <= 0) {
			r1 =  (int) (Math.random() * ( max ));
		}
		
		// haal de eerste auto uit de lijst
		Auto a1 = o.getToewijzingen().get("z"+r1).get(0);
		o.getToewijzingen().get("z"+r1).remove(0);
		a1.setReservaties(new HashMap<String, Reservatie>());
		
		// voeg auto aan willekeurige zone die verschillend is van de vorige zone
		int r2 =  (int) (Math.random() * ( max ));
		while(r1 == r2) {
			r2 = (int) (Math.random() * ( max ));
		}
		
		o.getToewijzingen().get("z"+r2).add(a1);
		return o;	
	}
	
	public Oplossing valideerOplossing(Oplossing o) {
		Auto auto = null;
		boolean toegewezen = false;
		
		// overloop alle requesten
		for(Request r : requests) {
			toegewezen = false;
			
			//als er geen autos zijn in gevraagde zone, kijk in naburige zones
			if(o.getToewijzingen().get(r.getZone()).size() > 0) {
				auto = getVrijeWagen(o.getToewijzingen().get(r.getZone()), r);
				if(auto != null) {
					r.setresauto(auto.getNaam());
					o.getReservaties().add(r);
					toegewezen = true;
				}
			} else {
				// aangrenzende zones checken
				for(String zone : zones.get(r.getZone())) {
					// kijk of er nog een wagen beschikbaar is in de aanligende zone
					auto = getVrijeWagen(o.getToewijzingen().get(zone), r);
					// wijs een wagen aan een request toe
					if(auto != null && !toegewezen) {
						o.setKost(o.getKost() + r.getP2());
						r.setresauto(auto.getNaam());
						o.getReservaties().add(r);
						toegewezen = true;
					}
				}
			}
			
			// bereken kost voor niet toegewezen request
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
		// overloop de autos in een zone
		for(Auto auto : a) {
			// kijk of een auto vrij is voor het gevraagde moment
			//if(auto.getWanneer_vrij() <= (r.getDag()*24*60) + r.getDuur()) {
			if(auto.checkVrij(((r.getDag()*24*60)+r.getStart()), ((r.getDag()*24*60)+r.getStart()+r.getDuur()))) {
				// kijk of dit een mogelijke auto is voor het request
				for(String a2 : r.getAutos()) {
					if(auto.getNaam().equals(a2)) {
						auto.getReservaties().put(r.getId(), new Reservatie(((r.getDag()*24*60)+r.getStart()), ((r.getDag()*24*60)+r.getStart()+r.getDuur())));
						//auto.setWanneer_vrij((r.getDag()*24*60) + r.getDuur() + r.getStart());
						return auto;
					}
				}
			}
		}
		return null;
	}
}

// wanneer auto vanaf dag 3 is gereserveerd, alle dagen ervoor ook gereserveerd
// ander methode om een nieuwe opplossing te selecteren
// eerst requesten toewijzen met hoogste kost

