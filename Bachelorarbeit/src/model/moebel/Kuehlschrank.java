package model.moebel;

import model.Moebelstueck;

public class Kuehlschrank extends Moebelstueck{

	
	public Kuehlschrank(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.platzierbarVorFenster = false;
	}
}
