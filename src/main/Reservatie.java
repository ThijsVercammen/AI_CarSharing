package main;

public class Reservatie {
	private int starttijd;
	private int eindtijd;
	private Request request;
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Reservatie(int starttijd, int eindtijd, Request request) {
		this.starttijd = starttijd;
		this.eindtijd = eindtijd;
		this.request = request;
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
