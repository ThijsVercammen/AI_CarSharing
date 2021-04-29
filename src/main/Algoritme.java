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
		// Haal starttijd op
		long start = System.currentTimeMillis();
		// Bereken einddtijd
		long end = start + 1000*totalTime;
		int tot = 0;
		//int geen_ver = 0;
		
		//Creëer initiële oplossing
		Oplossing o = startOplossing1();
		//Itereer over requests om te zien welke kunnen toegewezen worden
		o = valideerOplossing(o);
		//Creëer nieuwe beste oplossing
		Oplossing best_o1 = new Oplossing();
		//Oplossing best_o2 = new Oplossing();
		
		//Wijs start oplossing als beste oplossing toe
		best_o1.setKost(o.getKost());
		best_o1.setNiet_toegewezen(new ArrayList<Request>(o.getNiet_toegewezen()));
		best_o1.setReservaties(o.getReservaties());
		best_o1.setToewijzingen(o.getToewijzingen());
		/*
		best_o2.setKost(best_o1.getKost());
		best_o2.setNiet_toegewezen(new ArrayList<Request>(best_o1.getNiet_toegewezen()));
		best_o2.setReservaties(best_o1.getReservaties());
		best_o2.setToewijzingen(best_o1.getToewijzingen());
		*/
		int best_cost = best_o1.getKost();
		
		//Zoek tot eindtijd
		while(System.currentTimeMillis() < end) {
			/*
			if(geen_ver >= 10000) {
				System.out.println("_____best opl 1 : " + best_o1.getKost() + "\n");
				System.out.println("_____best opl 2 : " + best_o2.getKost() + "\n");
				if(best_o2.getKost() > best_o1.getKost()) {
					best_o2.setKost(best_o1.getKost());
					best_o2.setNiet_toegewezen(new ArrayList<Request>(best_o1.getNiet_toegewezen()));
					best_o2.setReservaties(best_o1.getReservaties());
					best_o2.setToewijzingen(best_o1.getToewijzingen());
				}
				o = startOplossing();
				geen_ver = 0;
				best_cost = 1000000;
			} else {*/
				//selecteer nieuwe oplossing
				o = selecteerOplossing(o);
			//}
			//valideer nieuwe oplossing
			o = valideerOplossing(o);
			
			// vergelijk met nieuwe oplossing met beste oplossing
			if(o.getKost() < best_cost) {
				//System.out.println("KOST: " + o.getKost() + " - " + best_o1.getKost() + "\n");
				best_o1.setKost(o.getKost());
				best_o1.setNiet_toegewezen(new ArrayList<Request>(o.getNiet_toegewezen()));
				best_o1.setReservaties(o.getReservaties());
				best_o1.setToewijzingen(o.getToewijzingen());
				best_cost = o.getKost();
		//		geen_ver = 0;
				
			// behoud huidige beste oplossing			
			} else {
			//	geen_ver++;
				o.setToewijzingen(best_o1.getToewijzingen());
			}
			

			tot++;
		}
		
	//	System.out.println("KOST: " + best_cost +"\n");
	//	System.out.println("Aantal loops: " + tot +"\n");
	//	System.out.println("best opl 1 : " + best_o1.getKost() + "\n");
		//System.out.println("best opl 2 : " + best_o2.getKost() + "\n");
		return best_o1;
	}
	
	// alle auto's in 1 zone
	public Oplossing startOplossing1() {
		Oplossing o = new Oplossing();	

		for(int i = 0; i<zones.keySet().size(); i++) {
			if(i == 0) {
				o.getToewijzingen().put("z"+i, this.autos);
			} else {
				//Out of cars, put empty arraylist of type Auto
				o.getToewijzingen().put("z"+i, new ArrayList<Auto>());
			}

		}
		return o;	
	}
	
	// auto's verdeeld over zones
	public Oplossing startOplossing2() {
		Oplossing o = new Oplossing();	
		// onderstaande start oplossing werkt alleen als #zones > #autos
		
		// auto1 -> zone1, auto2 -> zone2, ...
		for(int i = 0; i<zones.keySet().size(); i++) {
			if(i<this.autos.size()) {
				//Put car into zone
				ArrayList<Auto> a = new ArrayList<Auto>();
				a.add(this.autos.get(i));
				o.getToewijzingen().put("z"+i, a);
			} else {
				//Out of cars, put empty arraylist of type Auto
				o.getToewijzingen().put("z"+i, new ArrayList<Auto>());
			}
		}
		return o;	
	}
	
	public Oplossing selecteerOplossing(Oplossing o) {	
		//genereer willekeurig nummer tussen 0 en het #zones
		int max = zones.keySet().size();
		int r1;
		//Random zone die auto's bevat zoeken
		do {
			r1 = (int) (Math.random() * ( max ));
		} while (o.getToewijzingen().get("z"+r1).size() <= 0);
		
		// haal de eerste auto uit de lijst
		Auto a1 = o.getToewijzingen().get("z"+r1).get(0);
		o.getToewijzingen().get("z"+r1).remove(0);
		// Nieuwe reservatielijst aan auto toewijzen
		a1.setReservaties(new HashMap<String, Reservatie>());
		
		//Voet auto to aan random zone
		int r2;
		do {
			r2 = (int) (Math.random() * ( max ));
		} while (r1 == r2);

		o.getToewijzingen().get("z"+r2).add(a1);
		
		//Voeg auto toe aan zone van request met hoogste kost
		//o.getToewijzingen().get(o.getHoogsteKostRequest(a1).getZone()).add(a1);
		// reset vorige validatie
		o.setKost(0);
		o.setNiet_toegewezen(new ArrayList<Request>());
		o.setReservaties(new ArrayList<Request>());
		
		for(String z : o.getToewijzingen().keySet()) {
			for(Auto a : o.getToewijzingen().get(z)) {
				a.setReservaties(new HashMap<String, Reservatie>());
			}
		}

		return o;	
	}
	
	public Oplossing valideerOplossing(Oplossing o) {
		Auto auto = null;
		boolean toegewezen = false;
		
		// overloop alle requesten
		for(Request r : requests) {
			r.setresauto(null);
			toegewezen = false;
			
			//Zijn er auto's aan zone request toegewezen?
			if(o.getToewijzingen().get(r.getZone()).size() > 0) {
				// Is er een vrije auto van diegene die in de zone staan?
				auto = getVrijeWagen(o.getToewijzingen().get(r.getZone()), r);
				// Zo ja: wijs toe!
				if(auto != null&& !toegewezen) {
					r.setresauto(auto.getNaam());
					o.getReservaties().add(r);
					toegewezen = true;
				}
			//Nog geen auto toegewezen (ofwel geen in zone, ofwel geen vrije in de zone)
			} if(!toegewezen) {
				// aangrenzende zones checken
				for(String zone : zones.get(r.getZone())) {
					// kijk of er nog een wagen beschikbaar is in de aanliggende zone
					
						auto = getVrijeWagen(o.getToewijzingen().get(zone), r);
						// wijs een wagen aan een request toe
						if(auto != null) {
							o.setKost(o.getKost() + r.getP2());
							r.setresauto(auto.getNaam());
							o.getReservaties().add(r);
							toegewezen = true;
							break; //Wagen gevonden, break van zoeken in naburige zones
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
			int start = (r.getDag()*24*60)+r.getStart();
			int eind = ((r.getDag()*24*60)+r.getStart()+r.getDuur());
			if(auto.checkVrij(start, eind)) {
				// kijk of dit een mogelijke auto is voor het request
				for(String a2 : r.getAutos()) {
					if((auto.getNaam()).equals(a2)) {
						auto.getReservaties().put(r.getId(), new Reservatie(start, eind,r));
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

