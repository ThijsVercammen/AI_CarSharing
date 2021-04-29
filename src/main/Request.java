package main;

import java.util.ArrayList;

public class Request {
	private String id;
	private String zone;
	private int dag;
	private int start;
	private int duur;
	private ArrayList<String> autos;
	private int p1;
	private int p2;
	private String toegewezenauto;
	
	
	public Request() {
	}

	public Request(String id, String zone, int dag, int start, int duur, ArrayList<String> autos, int p1, int p2) {
		this.id = id;
		this.zone = zone;
		this.dag = dag;
		this.start = start;
		this.duur = duur;
		this.autos = autos;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Request(Request r) {
		this.id = r.getId();
		this.zone = r.getZone();
		this.dag = r.getDag();
		this.start = r.getStart();
		this.duur = r.getDuur();
		this.autos = r.getAutos();
		this.p1 = r.getP1();
		this.p2 = r.getP2();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public int getDag() {
		return dag;
	}
	public void setDag(int dag) {
		this.dag = dag;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuur() {
		return duur;
	}
	public void setDuur(int duur) {
		this.duur = duur;
	}
	public ArrayList<String> getAutos() {
		return autos;
	}
	public void setAutos(ArrayList<String> autos) {
		this.autos = autos;
	}
	public int getP1() {
		return p1;
	}
	public void setP1(int p1) {
		this.p1 = p1;
	}
	public int getP2() {
		return p2;
	}
	public void setP2(int p2) {
		this.p2 = p2;
	}
	public void setresauto(String auto) {
		this.toegewezenauto = auto;
	}
	public String getresauto() {
		return toegewezenauto;
	}
	
	public static ArrayList<Auto> cloneAutos(ArrayList<Auto> list) {
	    ArrayList<Auto> clone = new ArrayList<Auto>(list.size());
	    for (Auto auto : list) {
	    	clone.add(new Auto(auto));
	    }
	    return clone;
	}
}
