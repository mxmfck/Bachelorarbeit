package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum {

	private double laenge;
	private double breite;
	private List<Moebelstueck> moebel;
	private double groesse;
	private List<Tuer> tueren;
	private String name; // Zur Identifikation des Raumes

	public Raum(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		this.name = name;
		this.laenge = laenge;
		this.breite = breite;
		this.moebel = moebel;
		this.tueren = tueren;
		this.groesse = laenge * breite;
	}

	public Raum() {

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
		if (this.tueren == null)
			this.tueren = new ArrayList<Tuer>();
		this.tueren.add(tuer);
	}

	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}

	public void setBreite(double breite) {
		this.breite = breite;
	}

	public void setGroesse(double groesse) {
		this.groesse = groesse;
	}

	public String getName() {
		return name;
	}
}
