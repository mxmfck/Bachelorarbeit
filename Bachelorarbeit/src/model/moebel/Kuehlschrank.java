package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Kuehlschrank extends Moebelstueck {

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
		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Kühlschrank zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(1));
		if (this.getAusrichtung() == 0) {

			Line2D.Double line = new Line2D.Double(moebelX, moebelY + 0.1 * moebelBreite, moebelX + moebelLaenge,
					moebelY + 0.1 * moebelBreite);
			g2d.draw(line);

		} else if (this.getAusrichtung() == 90) {

			Line2D.Double line = new Line2D.Double(moebelX + 0.9 * moebelLaenge, moebelY, moebelX + 0.9 * moebelLaenge,
					moebelY + moebelBreite);
			g2d.draw(line);

		} else if (this.getAusrichtung() == 180) {

			Line2D.Double line = new Line2D.Double(moebelX, moebelY + 0.9 * moebelBreite, moebelX + moebelLaenge,
					moebelY + 0.9 * moebelBreite);
			g2d.draw(line);
			
		} else if (this.getAusrichtung() == 270) {

			Line2D.Double line = new Line2D.Double(moebelX + 0.1 * moebelLaenge, moebelY, moebelX + 0.1 * moebelLaenge,
					moebelY + moebelBreite);
			g2d.draw(line);
		
		}
	}
}
