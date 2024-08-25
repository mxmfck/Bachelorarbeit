package model;

public abstract class Moebelstueck {
// 	Abstrakte Klasse für alle Möbelstücke

	private double laenge; // Länge des Möbelstücks
	private double breite; // Breite des Möbelstücks
	private double keepOutLinks; // Abstand des Möbelstücks zum nächsten Objekt links
	private double keepOutRechts; // Abstand des Möbelstücks zum nächsten Objekt rechts
	private double keepOutOben; // Abstand des Möbelstücks zum nächsten Objekt oben
	private double keepOutUnten; // Abstand des Möbelstücks zum nächsten Objekt unten
	protected boolean platzierbarVorFenster = true; // Möbelstück kann vor einem Fenster platziert werden
	private double x;
	private double y;
	

	public Moebelstueck(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		this.laenge = laenge;
		this.breite = breite;
		this.keepOutLinks = keepOutLinks;
		this.keepOutRechts = keepOutRechts;
		this.keepOutOben = keepOutOben;
		this.keepOutUnten = keepOutUnten;
	}
	

	// Getter für alle Attribute
	public double getLaenge() {
		return laenge;
	}

	public double getBreite() {
		return breite;
	}

	public double getKeepOutLinks() {
		return keepOutLinks;
	}

	public double getKeepOutRechts() {
		return keepOutRechts;
	}

	public double getKeepOutOben() {
		return keepOutOben;
	}

	public double getKeepOutUnten() {
		return keepOutUnten;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean isPlatzierbarVorFenster() {
		return platzierbarVorFenster;
	}
	
	// Setter für die Position des Möbelstücks
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void drehen() {
		double tmp = laenge;
		breite = laenge;
		laenge =tmp;
		
		double tmp2 = keepOutLinks;
		keepOutLinks = keepOutOben;
		keepOutOben = keepOutRechts;
		keepOutRechts = keepOutUnten;
		keepOutUnten = tmp2;
	}

	public String toString() {
		return " Möbelstück: " + this.getClass().getSimpleName() + " | Länge: " + laenge + " | Breite: " + breite
				+ " | Keepout: " + keepOutLinks + ", " + keepOutRechts + ", " + keepOutOben + ", " + keepOutUnten;
	}
}
