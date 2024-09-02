package model;

public class Tuer{
//	Klasse für Türen

	private Raum vonRaum; //Raum, von dem die Tür ausgeht
	private Raum inRaum; //Raum, in den die Tür führt
	private double breite; //Breite der Tür
//	private double x; //x-Koordinate der Tür
//	private double y; //y-Koordinate der Tür
	private boolean linksOeffnend; //Gibt an, ob die Tür links öffnend ist
	private boolean horizontal;
	
	public Tuer(Raum vonRaum, Raum inRaum, double breite) {
		this.vonRaum = vonRaum;
		this.inRaum = inRaum;
		this.breite = breite;
	}
	
//	 @Override
//	    public Object clone() throws CloneNotSupportedException {
//	        Tuer clonedTuer = (Tuer) super.clone();
//	        
//	         clonedTuer.vonRaum = this.vonRaum;
//	         clonedTuer.inRaum = this.inRaum;
//
//	        
//	        return clonedTuer;
//	    }
	
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
	
//	public double getX() {
//		return x;
//	}
//	
//	public double getY() {
//		return y;
//	}
	
	public boolean isHorizontal() {
		return horizontal;
	}
	
	public boolean isLinksOeffnend() {
		return linksOeffnend;
	}
	
//	//Setter für die x-Koordinate
//	public void setX(double x) {
//		this.x = x;
//	}
//	
//	//Setter für die y-Koordinate
//	
//	public void setY(double y) {
//		this.y = y;
//	}
	
	//Setter für die Öffnungsrichtung
	public void setLinksOeffnend(boolean linksOeffnend) {
		this.linksOeffnend = linksOeffnend;
	}
	
	//Setter für die Ausrichtung
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
//	public void setVonRaum(Raum raum) {
//        this.vonRaum = raum;
//    }
//	
//	public void setInRaum(Raum raum) {
//        this.inRaum = raum;
//    }
}
