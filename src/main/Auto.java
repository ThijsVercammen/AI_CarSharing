package main;

public class Auto {
	private String naam;
	private int wanneer_vrij;
	
	
	public Auto(String naam, int wanneer_vrij) {
		this.naam = naam;
		this.wanneer_vrij = wanneer_vrij;
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
	
	
}
