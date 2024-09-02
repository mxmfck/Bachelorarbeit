package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Arbeitsplatte extends Moebelstueck {
	// Klasse für Arbeitsplatten

	public Arbeitsplatte(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten); // Konstruktor der Superklasse
																						// aufrufen
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Arbeitsplatte zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		if (this.getAusrichtung() == 0 || this.getAusrichtung() == 180) {
			Line2D.Double line = new Line2D.Double(moebelX + moebelLaenge * 0.1, moebelY + moebelBreite / 2,
					moebelX + moebelLaenge * 0.9, moebelY + moebelBreite / 2);
			g2d.draw(line);

			line = new Line2D.Double(moebelX + moebelLaenge * 0.1, moebelY + moebelBreite / 4,
					moebelX + moebelLaenge * 0.9, moebelY + moebelBreite / 4);
			g2d.draw(line);
			
			line = new Line2D.Double(moebelX + moebelLaenge * 0.1, moebelY + moebelBreite * 3 / 4,
					moebelX + moebelLaenge * 0.9, moebelY + moebelBreite * 3 / 4);
			g2d.draw(line);
		}else {
			Line2D.Double line = new Line2D.Double(moebelX + moebelLaenge / 2, moebelY + moebelBreite * 0.1,
					moebelX + moebelLaenge / 2, moebelY + moebelBreite * 0.9);
			g2d.draw(line);
			
			line = new Line2D.Double(moebelX + moebelLaenge / 4, moebelY + moebelBreite * 0.1,
					moebelX + moebelLaenge / 4, moebelY + moebelBreite * 0.9);
			g2d.draw(line);
			
			line = new Line2D.Double(moebelX + moebelLaenge * 3 / 4, moebelY + moebelBreite * 0.1,
					moebelX + moebelLaenge * 3 / 4, moebelY + moebelBreite * 0.9);
			g2d.draw(line);
			
		}
	}
}
