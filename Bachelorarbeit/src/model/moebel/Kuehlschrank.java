package model.moebel;

import java.awt.Graphics2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Kuehlschrank extends Moebelstueck{

	
	public Kuehlschrank(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.platzierbarVorFenster = false;
	}
	
public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
		
	}
}
