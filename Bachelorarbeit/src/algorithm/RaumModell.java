package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Moebelstueck;

public class RaumModell{
	// Klasse, die die Position der Räume im Grundriss speichert

	private String name;
	private double laenge;
	private double breite;
	private double x;
	private double y;
	private List<Moebelstueck> moebel;
	private List<TuerModell> tueren;
	private List<Fenster> fenster;
	private double raumX;
	private double raumY;
	private double raumLaenge;
	private double raumBreite;
	private final double WANDBREITE = 0.1;

	public RaumModell(String name, double laenge, double breite, List<Moebelstueck> moebel, List<TuerModell> tueren, double x,
			double y) {
		this.name = name;
		this.laenge = Math.round((laenge+2*WANDBREITE)*100.0)/100.0;
		this.breite = Math.round((breite+2*WANDBREITE)*100.0)/100.0;
		this.moebel = moebel;
		this.tueren = tueren;
		this.x = Math.round((x-WANDBREITE)*100.0)/100.0;
		this.y = Math.round((y-WANDBREITE)*100.0)/100.0;
		fenster = new ArrayList<Fenster>();
		this.raumX = Math.round(x*100.0)/100.0;
		this.raumY = Math.round(y*100.0)/100.0;
		this.raumLaenge = Math.round(laenge*100.0)/100.0;
		this.raumBreite = Math.round(breite*100.0)/100.0;
	}

	public void setX(double x) {
		this.x = Math.round(x*100.0)/100.0;
	}

	public void setY(double y) {
		this.y = Math.round(y*100.0)/100.0;
	}

	public void setBreite(double breite) {
		this.breite = Math.round(breite*100.0)/100.0;
	}

	public String getName() {
		return name;
	}

	public double getLaenge() {
		return laenge;
	}

	public double getBreite() {
		return breite;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public List<Moebelstueck> getMoebel() {
		return moebel;
	}

	public List<TuerModell> getTueren() {
		return tueren;
	}
	
	public List<Fenster> getFenster() {
		return fenster;
	}

	public void addFenster(Fenster fenster) {
		this.fenster.add(fenster);
	}
	
	public double getRaumX() {
		return raumX;
	}
	
	public double getRaumY() {
		return raumY;
	}
	
	public double getRaumLaenge() {
		return raumLaenge;
	}
	
	public double getRaumBreite() {
		return raumBreite;
	}
	
	public void setRaumBreite(double raumBreite) {
		this.raumBreite = Math.round(raumBreite*100.0)/100.0;
	}
	
	public double getWANDBREITE() {
		return WANDBREITE;
	}
}
//package model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RaumModell{
//	// Klasse, die die Position der Räume im Grundriss speichert
//
//	private String name;
//	private double laenge; //Länge mit Wand
//	private double breite; //Breite mit Wand
//	private double raumX; //X-Koordinate des Raumes ohne Wand
//	private double raumY; //Y-Koordinate des Raumes ohne Wand
//	private double raumLaenge; //Länge ohne Wand
//	private double raumBreite; //Breite ohne Wand
//	private double x; //X-Koordinate des Raumes mit Wand
//	private double y; //Y-Koordinate des Raumes mit Wand
//	private List<Moebelstueck> moebel;
//	private List<TuerModell> tueren;
//	private List<Fenster> fenster;
//	private final double WANDBREITE = 0.2;
//
//	public RaumModell(String name, double laenge, double breite, List<Moebelstueck> moebel, List<TuerModell> tueren, double x,
//			double y) {
//		this.name = name;
//		this.laenge = laenge+2*WANDBREITE;
//		this.breite = breite+2*WANDBREITE;
//		this.moebel = moebel;
//		this.tueren = tueren;
//		this.raumX = x;
//		this.raumY = y;
//		this.raumLaenge = laenge;
//		this.raumBreite = breite;
//		fenster = new ArrayList<Fenster>();
//		this.x = x-WANDBREITE;
//		this.y = y-WANDBREITE;
//	}
//
//	public void setX(double x) {
//		this.x = x;
//	}
//
//	public void setY(double y) {
//		this.y = y;
//	}
//
//	public void setBreite(double breite) {
//		this.breite = breite;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public double getLaenge() {
//		return laenge;
//	}
//
//	public double getBreite() {
//		return breite;
//	}
//
//	public double getX() {
//		return x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public List<Moebelstueck> getMoebel() {
//		return moebel;
//	}
//
//	public List<TuerModell> getTueren() {
//		return tueren;
//	}
//	
//	public List<Fenster> getFenster() {
//		return fenster;
//	}
//
//	public void addFenster(Fenster fenster) {
//		this.fenster.add(fenster);
//	}
//	
//	public double getRaumX() {
//		return raumX;
//	}
//	
//	public double getRaumY() {
//		return raumY;
//	}
//	
//	public double getRaumLaenge() {
//		return raumLaenge;
//	}
//	
//	public double getRaumBreite() {
//		return raumBreite;
//	}
//}
