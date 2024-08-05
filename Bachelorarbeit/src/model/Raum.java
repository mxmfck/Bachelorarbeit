package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum {
//	Abstrakte Klasse für alle Räume
	
	private double laenge; //Länge des Raumes
	private double breite; //Breite des Raumes
	private List<Moebelstueck> moebel; //Liste der Möbelstücke im Raum
	private double groesse; //Größe des Raumes
	private List<Tuer> tueren; //Liste der Türen im Raum
	private String name; // Name des Raumes zur Identifikation

	public Raum(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		this.name = name;
		this.laenge = laenge;
		this.breite = breite;
		this.moebel = moebel;
		this.tueren = tueren;
		this.groesse = laenge * breite;
	}

//	public Raum() {
//
//	}

	//Getter für alle Attribute
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

	//Methoden zum Hinzufügen von Möbelstücken und Türen
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
