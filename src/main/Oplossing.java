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
	public void setToewijzingen(Map<String, ArrayList<Auto>> toewijzingen) {
		this.toewijzingen = toewijzingen;
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
	public void printOplossing() {
		try (PrintWriter writer = new PrintWriter(new File("oplossing.csv"))) {
			//Totaal aantal lijnen van de output-file
			int totaal = toewijzingen.size() + reservaties.size() + niet_toegewezen.size() + 3;
			writer.write(totaal);
			writer.write("+Vehicle assignments");
			//Loop over hashmap met toewijzingen zone -> auto(s)
			for (Map.Entry<String, ArrayList<Auto>> entry : toewijzingen.entrySet()) {
				//Loop over auto(s) bij zone
				for(Auto auto : entry.getValue()) {
					writer.write(auto.getNaam()+";"+entry.getKey());
				}
			}
			writer.write("+Assigned requests");
			//Loop over toegewezen reservaties
			for(Request reserv : reservaties) {
				writer.write(reserv.getId()+";"+reserv.getresauto());
			}
			writer.write("+Unassigned requests");
			//Loop over niet toegewezen reservaties
			for(Request n_reserv : niet_toegewezen) {
				writer.write(n_reserv.getId());
			}
			
			
		
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}
