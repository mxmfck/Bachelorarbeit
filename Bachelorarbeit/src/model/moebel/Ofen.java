package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Ofen extends Moebelstueck{
	public Ofen(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
	}
	
public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
	double moebelX = offsetX + this.getX() * SCALE;
	double moebelY = offsetY + this.getY() * SCALE;
	double moebelLaenge = this.getLaenge() * SCALE;
	double moebelBreite = this.getBreite() * SCALE;

	// Ofen zeichnen
	Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

	g2d.setStroke(new BasicStroke(1));
	g2d.setColor(Color.LIGHT_GRAY);
	g2d.fill(rect);
	g2d.setColor(Color.BLACK);
	g2d.draw(rect);
	
	
	}
}
