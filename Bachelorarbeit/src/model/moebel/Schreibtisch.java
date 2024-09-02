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
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		schreibtischstuhl = new Stuhl(0.5, 0.5, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten); // TODO Werte
	}

	public String toString() {
		return " Schreibtisch: " + super.toString() + "\n  " + schreibtischstuhl.toString();
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Schrank zeichnen
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

		} else if (this.getAusrichtung() == 180) {
			double stuhlLaenge = schreibtischstuhl.getLaenge() * SCALE;
			double stuhlX = moebelX + (moebelLaenge - stuhlLaenge) / 2;
			double stuhlY = moebelY + moebelBreite;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge, 0.4 * SCALE);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);

		} else if (this.getAusrichtung() == 90) {
			double stuhlBreite = schreibtischstuhl.getBreite() * SCALE;
			double stuhlX = moebelX + moebelLaenge;
			double stuhlY = moebelY + (moebelBreite - stuhlBreite) / 2;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, 0.4 * SCALE, stuhlBreite);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);
		
		} else if (this.getAusrichtung() == 270) {
			double stuhlBreite = schreibtischstuhl.getBreite() * SCALE;
			double stuhlX = moebelX - 0.4 * SCALE;
			double stuhlY = moebelY + (moebelBreite - stuhlBreite) / 2;

			Rectangle2D.Double stuhl = new Rectangle2D.Double(stuhlX, stuhlY, 0.4 * SCALE, stuhlBreite);
			g2d.setColor(Color.BLACK);
			g2d.draw(stuhl);
		}
	}
}
