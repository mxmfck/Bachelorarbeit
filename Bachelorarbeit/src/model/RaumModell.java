package model;

import java.util.List;

public class RaumModell {
	//Klasse, die die Position der Räume im Grundriss speichert
	
	private String name;
	private double laenge;
	private double breite;
	private double x;
	private double y;
	private List<Moebelstueck> moebel;
	private List<Tuer> tueren;
	
	public RaumModell(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren, double x, double y) {
		this.name = name;
		this.laenge = laenge;
		this.breite = breite;
		this.moebel = moebel;
		this.tueren = tueren;
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setBreite(double breite) {
		this.breite = breite;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLaenge() {
		return laenge;
	}
	
	public double getBreite() {
		return breite;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public List<Moebelstueck> getMoebel() {
		return moebel;
	}
}
