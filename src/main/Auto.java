package main;

import java.util.HashMap;

public class Auto {
	private String naam;
	private int wanneer_vrij;
	private HashMap<String, Reservatie> reservaties;
	
	public Auto(String naam, int wanneer_vrij) {
		this.naam = naam;
		this.wanneer_vrij = wanneer_vrij;
		this.reservaties = new HashMap<String, Reservatie>();
	}
	
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public int getWanneer_vrij() {
		return wanneer_vrij;
	}
	public void setWanneer_vrij(int wanneer_vrij) {
		this.wanneer_vrij = wanneer_vrij;
	}

	public HashMap<String, Reservatie> getReservaties() {
		return reservaties;
	}

	public void setReservaties(HashMap<String, Reservatie> reservaties) {
		this.reservaties = reservaties;
	}
	
	public boolean checkVrij(int start, int eind) {
		if(getReservaties().keySet().size() <= 0) return true;
		for(String req: getReservaties().keySet()) {
			if(getReservaties().get(req).getStarttijd() <= start && getReservaties().get(req).getEindtijd() >= start) {
				return false;
			}
			if(getReservaties().get(req).getStarttijd() <= eind && getReservaties().get(req).getEindtijd() >= eind) {
				return false;
			}
		}
		return true;
	}
}
