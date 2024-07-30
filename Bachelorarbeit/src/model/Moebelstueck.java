package model;

public abstract class Moebelstueck {

	private double laenge;
	private double breite;
	private double keepOutLinks;
	private double keepOutRechts;
	private double keepOutOben;
	private double keepOutUnten;
	
	public Moebelstueck(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		this.laenge = laenge;
		this.breite = breite;
		this.keepOutLinks = keepOutLinks;
		this.keepOutRechts = keepOutRechts;
		this.keepOutOben = keepOutOben;
		this.keepOutUnten = keepOutUnten;
	}
	
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
	
	
}
