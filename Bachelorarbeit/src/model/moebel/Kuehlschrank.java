package model.moebel;

import java.awt.Graphics2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Kuehlschrank extends Moebelstueck{

	
	public Kuehlschrank(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten, boolean keepoutGesetzt) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.platzierbarVorFenster = false;
		
		if (!keepoutGesetzt) {
			this.setKeepOutLinks(0);
			this.setKeepOutRechts(0);
			this.setKeepOutOben(0);
			this.setKeepOutUnten(1);
		}
	}
	
public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
		
	}
}
