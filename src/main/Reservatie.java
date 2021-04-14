package main;

public class Reservatie {
	private int starttijd;
	private int eindtijd;
	
	public Reservatie(int starttijd, int eindtijd) {
		this.starttijd = starttijd;
		this.eindtijd = eindtijd;
	}
	
	public int getStarttijd() {
		return starttijd;
	}
	public void setStarttijd(int starttijd) {
		this.starttijd = starttijd;
	}
	public int getEindtijd() {
		return eindtijd;
	}
	public void setEindtijd(int eindtijd) {
		this.eindtijd = eindtijd;
	}
	
	
}
