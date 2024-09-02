package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Dusche extends Moebelstueck {

	public Dusche(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.platzierbarVorFenster = false;
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX +  this.getX() * SCALE;
		double moebelY = offsetY +  this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;
		
		//Dusche zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);
		
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);
		
		double innereLaenge = moebelLaenge * 0.9;
		double innereBreite = moebelBreite * 0.9;
		
		double inneresX = moebelX + (moebelLaenge - innereLaenge) / 2;
		double inneresY = moebelY + (moebelBreite - innereBreite) / 2;
		
		RoundRectangle2D.Double inneresRect = new RoundRectangle2D.Double(inneresX, inneresY, innereLaenge, innereBreite, innereBreite*0.1, innereLaenge*0.1);
		g2d.setColor(Color.WHITE);
		g2d.fill(inneresRect);
		g2d.setColor(Color.BLACK);
		g2d.draw(inneresRect);
		
		g2d.setColor(Color.BLACK);
		double durchmesser = Math.min(innereBreite,innereLaenge) / 8;
		
		double kreisX = inneresX + durchmesser;
		double kreisY = inneresY + durchmesser;
		
		Arc2D.Double kreis = new Arc2D.Double(kreisX, kreisY, durchmesser, durchmesser, 0, 360, Arc2D.OPEN);
		g2d.fill(kreis);
	}
}
