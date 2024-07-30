package model.moebel;

import java.util.List;

import model.Moebelstueck;

public class Tisch extends Moebelstueck{

	private int anzahlStuehle;
	private List<Stuhl> stuehle;
	private boolean stuehleOben;
	private boolean stuehleUnten;
	private boolean stuehleLinks;
	private boolean stuehleRechts;
	
	public Tisch(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten, int anzahlStuehle, List<Stuhl> stuehle, boolean stuehleOben, boolean stuehleUnten,
			boolean stuehleLinks, boolean stuehleRechts) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.anzahlStuehle = anzahlStuehle;
		this.stuehle = stuehle;
		this.stuehleOben = stuehleOben;
		this.stuehleUnten = stuehleUnten;
		this.stuehleLinks = stuehleLinks;
		this.stuehleRechts = stuehleRechts;
	}
	
	public int getAnzahlStuehle() {
		return anzahlStuehle;
	}
	
	public List<Stuhl> getStuehle() {
		return stuehle;
	}
	
	public boolean isStuehleOben() {
		return stuehleOben;
	}
	
	public boolean isStuehleUnten() {
		return stuehleUnten;
	}
	
	public boolean isStuehleLinks() {
		return stuehleLinks;
	}
	
	public boolean isStuehleRechts() {
		return stuehleRechts;
	}
	
}
