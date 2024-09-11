package model;

public class Tuer{
//	Klasse für Türen

	private Raum vonRaum; //Raum, von dem die Tür ausgeht
	private Raum inRaum; //Raum, in den die Tür führt
	private double breite; //Breite der Tür
	private boolean linksOeffnend; //Gibt an, ob die Tür links öffnend ist
	private boolean horizontal;
	
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
	
	
	public boolean isHorizontal() {
		return horizontal;
	}
	
	public boolean isLinksOeffnend() {
		return linksOeffnend;
	}
	
	
	//Setter für die Öffnungsrichtung
	public void setLinksOeffnend(boolean linksOeffnend) {
		this.linksOeffnend = linksOeffnend;
	}
	
	//Setter für die Ausrichtung
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
}
