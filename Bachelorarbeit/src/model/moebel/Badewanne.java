package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Badewanne extends Moebelstueck {

	public Badewanne(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten, boolean keepoutGesetzt) {
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

		// Großes Rechteck zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		// Berechnung des kleineren Rechtecks (90% der Größe)
		double innereBreite = moebelBreite * 0.9;
		double innereLaenge = moebelLaenge * 0.9;

		// Berechnung der Position, um das kleinere Rechteck zu zentrieren
		double inneresX = moebelX + (moebelLaenge - innereLaenge) / 2;
		double inneresY = moebelY + (moebelBreite - innereBreite) / 2;

		// Festlegung des Radius für die abgerundeten Ecken
		double arcBreite = innereBreite * 0.1;
		double arcLaenge = innereLaenge * 0.1;

		// Kleineres Rechteck mit abgerundeten Ecken zeichnen
		RoundRectangle2D.Double innerRect = new RoundRectangle2D.Double(inneresX, inneresY, innereLaenge, innereBreite,
				arcBreite, arcLaenge);

		g2d.setColor(Color.WHITE);
		g2d.fill(innerRect);
		g2d.setColor(Color.BLACK);
		g2d.draw(innerRect);

		if (this.getAusrichtung() == 0 || this.getAusrichtung() == 180) {
			double durchmesser = innereBreite / 8;

			double kreisX = inneresX + (innereLaenge - durchmesser) / 7;
			double kreisY = inneresY + (innereBreite - durchmesser) / 2;

			Arc2D.Double arc = new Arc2D.Double(kreisX, kreisY, durchmesser, durchmesser, 0, 360, Arc2D.PIE);
			g2d.setColor(Color.BLACK);
			g2d.fill(arc);
		} else {
			double durchmesser = innereLaenge / 8;

			double kreisX = inneresX + (innereLaenge - durchmesser) / 2;
			double kreisY = inneresY + (innereBreite - durchmesser) / 7;

			Arc2D.Double arc = new Arc2D.Double(kreisX, kreisY, durchmesser, durchmesser, 0, 360, Arc2D.PIE);
			g2d.setColor(Color.BLACK);
			g2d.fill(arc);
		}
	}
}
