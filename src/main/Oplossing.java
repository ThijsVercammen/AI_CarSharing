package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Oplossing {
	
	private Map<String, ArrayList<Auto>> toewijzingen;
	private int kost;
	private ArrayList<Request> reservaties;
	private ArrayList<Request> niet_toegewezen;
	
	
	public Oplossing() {
		this.toewijzingen = new HashMap<>();
		this.kost = 0;
		this.reservaties = new ArrayList<>();
		this.niet_toegewezen = new ArrayList<>();
	}
	

	public Map<String, ArrayList<Auto>> getToewijzingen() {
		return toewijzingen;
	}
	public void setToewijzingen(Map<String, ArrayList<Auto>> t) {
		HashMap<String, ArrayList<Auto>> h = new HashMap<>();
		for(String s : t.keySet()) {
			h.put(s, new ArrayList<>(t.get(s)));
		}
		this.toewijzingen = h;
	}
	public int getKost() {
		return kost;
	}
	public void setKost(int kost) {
		this.kost = kost;
	}
	public ArrayList<Request> getReservaties() {
		return reservaties;
	}
	public void setReservaties(ArrayList<Request> reservaties) {
		this.reservaties = reservaties;
	}
	public ArrayList<Request> getNiet_toegewezen() {
		return niet_toegewezen;
	}
	public void setNiet_toegewezen(ArrayList<Request> niet_toegewezen) {
		this.niet_toegewezen = niet_toegewezen;
	}
	public Request getHoogsteKostRequest(Auto a) {
		Request hoogste = this.niet_toegewezen.get(0);
		for(int i = 1; i<this.niet_toegewezen.size(); i++) {
			for(String auto : this.niet_toegewezen.get(i).getAutos()) {
				if(a.getNaam().equals(auto)) {
					if(hoogste.getP1()<this.niet_toegewezen.get(i).getP1()) {
						hoogste = this.niet_toegewezen.get(i);
					}
				}
			}
		}
		return hoogste;
	}
	public void printOplossing(String sol) {
		try (PrintWriter writer = new PrintWriter(new File(sol))) {
			//Totaal aantal lijnen van de output-file
			int totaal = toewijzingen.size() + reservaties.size() + niet_toegewezen.size() + 3;
			writer.write(totaal);
			writer.println();
			writer.write("+Vehicle assignments");
			writer.println();
			//Loop over hashmap met toewijzingen zone -> auto(s)
			for (Map.Entry<String, ArrayList<Auto>> entry : toewijzingen.entrySet()) {
				//Loop over auto(s) bij zone
				for(Auto auto : entry.getValue()) {
					writer.write(auto.getNaam()+";"+entry.getKey());
					writer.println();
				}
			}
			writer.write("+Assigned requests");
			writer.println();
			//Loop over toegewezen reservaties
			for(Request reserv : reservaties) {
				writer.write(reserv.getId()+";"+reserv.getresauto());
				writer.println();
			}
			writer.write("+Unassigned requests");
			writer.println();
			//Loop over niet toegewezen reservaties
			for(Request n_reserv : niet_toegewezen) {
				writer.write(n_reserv.getId());
				writer.println();
			}
			writer.close();
			
			
		
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}
