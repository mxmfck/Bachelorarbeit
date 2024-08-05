package model.moebel;

import model.Moebelstueck;

public class Arbeitsplatte extends Moebelstueck{
	//Klasse für Arbeitsplatten
	
	public Arbeitsplatte(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten); //Konstruktor der Superklasse aufrufen
	}
}
