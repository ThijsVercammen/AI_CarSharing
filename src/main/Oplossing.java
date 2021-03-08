package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Oplossing {
	
	private Map<String, Auto> toewijzingen;
	private int kost;
	private ArrayList<Auto> reservaties;
	private ArrayList<Auto> niet_toegewezen;
	
	
	public Oplossing() {
		this.toewijzingen = new HashMap<>();
		this.kost = 0;
		this.reservaties = new ArrayList<>();
		this.niet_toegewezen = new ArrayList<>();
	}
	
	public Map<String, Auto> getToewijzingen() {
		return toewijzingen;
	}
	public void setToewijzingen(Map<String, Auto> toewijzingen) {
		this.toewijzingen = toewijzingen;
	}
	public int getKost() {
		return kost;
	}
	public void setKost(int kost) {
		this.kost = kost;
	}
	public ArrayList<Auto> getReservaties() {
		return reservaties;
	}
	public void setReservaties(ArrayList<Auto> reservaties) {
		this.reservaties = reservaties;
	}
	public ArrayList<Auto> getNiet_toegewezen() {
		return niet_toegewezen;
	}
	public void setNiet_toegewezen(ArrayList<Auto> niet_toegewezen) {
		this.niet_toegewezen = niet_toegewezen;
	}
	
	

}
