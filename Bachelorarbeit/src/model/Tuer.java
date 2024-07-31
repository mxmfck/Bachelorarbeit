package model;

public class Tuer {

	private Raum vonRaum;
	private Raum inRaum;
	private double breite;
	
	public Tuer(Raum vonRaum, Raum inRaum, double breite) {
		this.vonRaum = vonRaum;
		this.inRaum = inRaum;
		this.breite = breite;
	}
	
	public Raum getVonRaum() {
		return vonRaum;
	}
	
	public Raum getInRaum() {
		return inRaum;
	}
	
	public double getBreite() {
		return breite;
	}
	
	
}
