package model.moebel;

import model.Moebelstueck;

public class Schreibtisch extends Moebelstueck{

	private Stuhl schreibtischstuhl;
	
	public Schreibtisch(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		schreibtischstuhl = new Stuhl(0.5,0.5,keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten); //TODO Werte
	}
}
