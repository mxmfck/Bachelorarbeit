package model;

import java.util.List;

public abstract class Raum {

	private double laenge;
	private double breite;
	private List<Moebelstueck> moebel;
	private double groesse;
	private List<Tuer> tueren;
	
	public Raum(double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		this.laenge = laenge;
		this.breite = breite;
		this.moebel = moebel;
		this.tueren = tueren;
		this.groesse = laenge * breite;
	}
	
	public double getLaenge() {
		return laenge;
	}
	
	public double getBreite() {
		return breite;
	}
	
	public List<Moebelstueck> getMoebel() {
		return moebel;
	}
	
	public double getGroesse() {
		return groesse;
	}
	
	public List<Tuer> getTueren() {
		return tueren;
	}
	
	public void addMoebel(Moebelstueck moebel) {
		this.moebel.add(moebel);
	}
	
	public void addTuer(Tuer tuer) {
		this.tueren.add(tuer);
	}
	
}
