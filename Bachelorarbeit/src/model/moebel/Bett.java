package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Bett extends Moebelstueck {

	public Bett(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten, boolean keepoutGesetzt) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		
		if (!keepoutGesetzt) {
			this.setKeepOutLinks(0);
			this.setKeepOutRechts(0.5);
			this.setKeepOutOben(0);
			this.setKeepOutUnten(1);
		}
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Bett zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		// Decke zeichnen
		if (this.getAusrichtung() == 0) {
			Line2D.Double line = new Line2D.Double(moebelX, moebelY + moebelBreite * 0.8, moebelX + moebelLaenge,
					moebelY + moebelBreite * 0.8);
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.BLACK);
			g2d.draw(line);

			double kissenBoxLaenge = moebelLaenge;
			double kissenBoxBreite = 0.2 * moebelBreite;
			double kissenBoxX = moebelX;
			double kissenBoxY = moebelY + moebelBreite * 0.8;

			double kissenLaenge = 0.9 * kissenBoxLaenge;
			double kissenBreite = 0.8 * kissenBoxBreite;
			double kissenX = kissenBoxX + (kissenBoxLaenge - kissenLaenge) / 2;
			double kissenY = kissenBoxY + (kissenBoxBreite - kissenBreite) / 2;

			Rectangle2D.Double kissen = new Rectangle2D.Double(kissenX, kissenY, kissenLaenge, kissenBreite);
			g2d.draw(kissen);

		} else if (this.getAusrichtung() == 180) {

			Line2D.Double line = new Line2D.Double(moebelX, moebelY + moebelBreite * 0.2, moebelX + moebelLaenge,
					moebelY + moebelBreite * 0.2);
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.BLACK);
			g2d.draw(line);

			double kissenBoxLaenge = moebelLaenge;
			double kissenBoxBreite = 0.2 * moebelBreite;
			double kissenBoxX = moebelX;
			double kissenBoxY = moebelY;

			double kissenLaenge = 0.9 * kissenBoxLaenge;
			double kissenBreite = 0.8 * kissenBoxBreite;
			double kissenX = kissenBoxX + (kissenBoxLaenge - kissenLaenge) / 2;
			double kissenY = kissenBoxY + (kissenBoxBreite - kissenBreite) / 2;

			Rectangle2D.Double kissen = new Rectangle2D.Double(kissenX, kissenY, kissenLaenge, kissenBreite);
			g2d.draw(kissen);
			
		} else if (this.getAusrichtung() == 90) {

			Line2D.Double line = new Line2D.Double(moebelX+moebelLaenge*0.2, moebelY, moebelX + moebelLaenge*0.2,
					moebelY + moebelBreite);
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.BLACK);
			g2d.draw(line);

			double kissenBoxLaenge = 0.2*moebelLaenge;
			double kissenBoxBreite = moebelBreite;
			double kissenBoxX = moebelX;
			double kissenBoxY = moebelY;

			double kissenLaenge = 0.8 * kissenBoxLaenge;
			double kissenBreite = 0.9 * kissenBoxBreite;
			double kissenX = kissenBoxX + (kissenBoxLaenge - kissenLaenge) / 2;
			double kissenY = kissenBoxY + (kissenBoxBreite - kissenBreite) / 2;
			
			Rectangle2D.Double kissen = new Rectangle2D.Double(kissenX, kissenY, kissenLaenge, kissenBreite);
			g2d.draw(kissen);
			
		}else if(this.getAusrichtung()==270) {
			
			Line2D.Double line = new Line2D.Double(moebelX+moebelLaenge*0.8, moebelY, moebelX + moebelLaenge*0.8,
					moebelY + moebelBreite);
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.BLACK);
			g2d.draw(line);

			double kissenBoxLaenge = 0.2*moebelLaenge;
			double kissenBoxBreite = moebelBreite;
			double kissenBoxX = moebelX+moebelLaenge*0.8;
			double kissenBoxY = moebelY;

			double kissenLaenge = 0.8 * kissenBoxLaenge;
			double kissenBreite = 0.9 * kissenBoxBreite;
			double kissenX = kissenBoxX + (kissenBoxLaenge - kissenLaenge) / 2;
			double kissenY = kissenBoxY + (kissenBoxBreite - kissenBreite) / 2;
			
			Rectangle2D.Double kissen = new Rectangle2D.Double(kissenX, kissenY, kissenLaenge, kissenBreite);
			g2d.draw(kissen);
		}
	}

}
