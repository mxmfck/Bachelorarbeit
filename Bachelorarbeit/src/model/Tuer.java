package model;

public class Tuer {
//	Klasse für Türen

	private Raum vonRaum; //Raum, von dem die Tür ausgeht
	private Raum inRaum; //Raum, in den die Tür führt
	private double breite; //Breite der Tür
	
	public Tuer(Raum vonRaum, Raum inRaum, double breite) {
		this.vonRaum = vonRaum;
		this.inRaum = inRaum;
		this.breite = breite;
	}
	
	//Getter für alle Attribute
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
