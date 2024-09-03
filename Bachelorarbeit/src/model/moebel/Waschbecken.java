package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Waschbecken extends Moebelstueck {

	public Waschbecken(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Spühle zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		double innereLaenge = moebelLaenge * 0.8;
		double innereBreite = moebelBreite * 0.8;

		double inneresX = moebelX + (moebelLaenge - innereLaenge) / 2;
		double inneresY = moebelY + (moebelBreite - innereBreite) / 2;

		RoundRectangle2D.Double inneresRect = new RoundRectangle2D.Double(inneresX, inneresY, innereLaenge,
				innereBreite, innereBreite * 0.1, innereLaenge * 0.1);
		g2d.setColor(Color.WHITE);
		g2d.fill(inneresRect);
		g2d.setColor(Color.BLACK);
		g2d.draw(inneresRect);
		

		double durchmesser = Math.min(innereBreite, innereLaenge) / 8;
		double kreisX = inneresX + (innereLaenge - durchmesser) / 2;
		double kreisY = inneresY + (innereBreite - durchmesser) / 2;

		Arc2D.Double kreis = new Arc2D.Double(kreisX, kreisY, durchmesser, durchmesser, 0, 360, Arc2D.OPEN);
		g2d.fill(kreis);

		if (this.getAusrichtung() == 0) {

			double wasserhahnLaenge = durchmesser * 1.2;
			double wasserhahnBreite = innereBreite / 2;
			double wasserhahnX = moebelX + (moebelLaenge - wasserhahnLaenge) / 2;
			double wasserhahnY = moebelY + moebelBreite - wasserhahnBreite;

			Rectangle2D.Double wasserhahn = new Rectangle2D.Double(wasserhahnX, wasserhahnY, wasserhahnLaenge,
					wasserhahnBreite);

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(wasserhahn);
			g2d.setColor(Color.BLACK);
			g2d.draw(wasserhahn);

		} else if (this.getAusrichtung() == 180) {

			double wasserhahnLaenge = durchmesser * 1.2;
			double wasserhahnBreite = innereBreite / 2;
			double wasserhahnX = moebelX + (moebelLaenge - wasserhahnLaenge) / 2;
			double wasserhahnY = moebelY;

			Rectangle2D.Double wasserhahn = new Rectangle2D.Double(wasserhahnX, wasserhahnY, wasserhahnLaenge,
					wasserhahnBreite);

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(wasserhahn);
			g2d.setColor(Color.BLACK);
			g2d.draw(wasserhahn);

		} else if (this.getAusrichtung() == 90) {

			double wasserhahnLaenge = innereLaenge / 2;
			double wasserhahnBreite = durchmesser * 1.2;
			double wasserhahnX = moebelX;
			double wasserhahnY = moebelY + (moebelBreite - wasserhahnBreite) / 2;

			Rectangle2D.Double wasserhahn = new Rectangle2D.Double(wasserhahnX, wasserhahnY, wasserhahnLaenge,
					wasserhahnBreite);

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(wasserhahn);
			g2d.setColor(Color.BLACK);
			g2d.draw(wasserhahn);

		} else if (this.getAusrichtung() == 270) {

			double wasserhahnLaenge = innereLaenge / 2;
			double wasserhahnBreite = durchmesser * 1.2;
			double wasserhahnX = moebelX + moebelLaenge - wasserhahnLaenge;
			double wasserhahnY = moebelY + (moebelBreite - wasserhahnBreite) / 2;

			Rectangle2D.Double wasserhahn = new Rectangle2D.Double(wasserhahnX, wasserhahnY, wasserhahnLaenge,
					wasserhahnBreite);

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(wasserhahn);
			g2d.setColor(Color.BLACK);
			g2d.draw(wasserhahn);

		}
	}
}
