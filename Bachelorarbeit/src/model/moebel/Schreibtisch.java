package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Schreibtisch extends Moebelstueck {
//    Klasse für Schreibtische (Sonderfall von Moebelstueck)

	private Stuhl schreibtischstuhl; // Stuhl zum Schreibtisch

	public Schreibtisch(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten, boolean keepoutGesetzt) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		schreibtischstuhl = new Stuhl(0.5, 0.5, 0, 0, 0, 0);
		
		if (!keepoutGesetzt) {
			this.setKeepOutLinks(0);
			this.setKeepOutRechts(0);
			this.setKeepOutOben(0);
			this.setKeepOutUnten(1);
		}
	}

	public String toString() {
		return " Schreibtisch: " + super.toString() + "\n  " + schreibtischstuhl.toString();
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Schreibtisch zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		// Stuhl zeichnen

		if (this.getAusrichtung() == 0) {
			double stuhlLaenge = schreibtischstuhl.getLaenge() * SCALE;
			double stuhlX = moebelX + (moebelLaenge - stuhlLaenge) / 2;
			double stuhlY = moebelY - 0.4 * SCALE;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge, 0.4 * SCALE);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);

			double sitzLaenge = stuhlLaenge * 0.8;
			double sitzBreite = (0.4 * SCALE) * 0.9;
			double sitzX = stuhlX + (stuhlLaenge - sitzLaenge) / 2;
			double sitzY = stuhlY + (0.4 * SCALE - sitzBreite) / 2;
			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);

		} else if (this.getAusrichtung() == 180) {
			double stuhlLaenge = schreibtischstuhl.getLaenge() * SCALE;
			double stuhlX = moebelX + (moebelLaenge - stuhlLaenge) / 2;
			double stuhlY = moebelY + moebelBreite;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge, 0.4 * SCALE);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);

			double sitzLaenge = stuhlLaenge * 0.8;
			double sitzBreite = (0.4 * SCALE) * 0.9;
			double sitzX = stuhlX + (stuhlLaenge - sitzLaenge) / 2;
			double sitzY = stuhlY;

			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);

		} else if (this.getAusrichtung() == 90) {
			double stuhlBreite = schreibtischstuhl.getBreite() * SCALE;
			double stuhlX = moebelX + moebelLaenge;
			double stuhlY = moebelY + (moebelBreite - stuhlBreite) / 2;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, 0.4 * SCALE, stuhlBreite);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);

			double sitzLaenge = (0.4 * SCALE) * 0.9;
			double sitzBreite = stuhlBreite * 0.8;
			double sitzX = stuhlX;
			double sitzY = stuhlY + (stuhlBreite - sitzBreite) / 2;

			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);

		} else if (this.getAusrichtung() == 270) {
			double stuhlBreite = schreibtischstuhl.getBreite() * SCALE;
			double stuhlX = moebelX - 0.4 * SCALE;
			double stuhlY = moebelY + (moebelBreite - stuhlBreite) / 2;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, 0.4 * SCALE, stuhlBreite);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);

			double sitzLaenge = (0.4 * SCALE) * 0.9;
			double sitzBreite = stuhlBreite * 0.8;
			double sitzX = stuhlX + (0.4 * SCALE - sitzLaenge);
			double sitzY = stuhlY + (stuhlBreite - sitzBreite) / 2;

			Rectangle2D.Double sitz = new Rectangle2D.Double(sitzX, sitzY, sitzLaenge, sitzBreite);
			g2d.draw(sitz);
		}
	}
}
