package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Stuhl extends Moebelstueck {

	public Stuhl(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
	}
	
	public Stuhl(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten,boolean keepoutGesetzt) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		
		if (!keepoutGesetzt) {
			this.setKeepOutLinks(0);
			this.setKeepOutRechts(0);
			this.setKeepOutOben(0);
			this.setKeepOutUnten(1);
		}
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Stuhl zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		if (this.getAusrichtung() == 0) {

			double sitzLaenge = moebelLaenge * 0.8;
			double sitzBreite = moebelBreite * 0.8;
			double sitzX = moebelX + (moebelLaenge - sitzLaenge) / 2;
			double sitzY = moebelY;
			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);

		} else if (this.getAusrichtung() == 180) {
			

			double sitzLaenge = moebelLaenge * 0.8;
			double sitzBreite = moebelBreite * 0.8;
			double sitzX = moebelX + (moebelLaenge - sitzLaenge) / 2;
			double sitzY = moebelY + moebelBreite - sitzBreite;

			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);

		} else if (this.getAusrichtung() == 90) {
			

			double sitzLaenge = moebelLaenge * 0.8;
			double sitzBreite = moebelBreite * 0.8;
			double sitzX = moebelX+ moebelLaenge - sitzLaenge;
			double sitzY = moebelY + (moebelBreite - sitzBreite) / 2;

			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);

		} else if (this.getAusrichtung() == 270) {
			

			double sitzLaenge = moebelLaenge * 0.8;
			double sitzBreite = moebelBreite * 0.8;
			double sitzX = moebelX;
			double sitzY = moebelY + (moebelBreite - sitzBreite) / 2;

			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);
		}
	}
}
