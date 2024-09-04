package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Herd extends Moebelstueck {

	public Herd(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
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

		double moebelX = offsetX +  this.getX() * SCALE;
		double moebelY = offsetY +  this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;
		
		//Herd zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);
		
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);
		
		//Kochfeld zeichnen
		double durchmeser = Math.min(moebelLaenge, moebelBreite) / 3;
		double kochfeldX = moebelX + (moebelLaenge - durchmeser) / 6;
		double kochfeldY = moebelY + (moebelBreite - durchmeser) / 6;
		
		Arc2D.Double kochfeld1 = new Arc2D.Double(kochfeldX, kochfeldY, durchmeser, durchmeser, 0, 360, Arc2D.OPEN);
		g2d.setColor(Color.BLACK);
		g2d.fill(kochfeld1);
		
		 durchmeser = Math.min(moebelLaenge, moebelBreite) / 3;
		 kochfeldX = moebelX + (moebelLaenge - durchmeser) / 6;
		 kochfeldY = moebelY + (moebelBreite - durchmeser)*5 / 6;
		
		Arc2D.Double kochfeld2 = new Arc2D.Double(kochfeldX, kochfeldY, durchmeser, durchmeser, 0, 360, Arc2D.OPEN);
		g2d.setColor(Color.BLACK);
		g2d.fill(kochfeld2);
		
		durchmeser = Math.min(moebelLaenge, moebelBreite) / 3;
		 kochfeldX = moebelX + (moebelLaenge - durchmeser)*5 / 6;
		 kochfeldY = moebelY + (moebelBreite - durchmeser) / 6;
		
		Arc2D.Double kochfeld3 = new Arc2D.Double(kochfeldX, kochfeldY, durchmeser, durchmeser, 0, 360, Arc2D.OPEN);
		g2d.setColor(Color.BLACK);
		g2d.fill(kochfeld3);
		
		durchmeser = Math.min(moebelLaenge, moebelBreite) / 3;
		 kochfeldX = moebelX + (moebelLaenge - durchmeser)*5 / 6;
		 kochfeldY = moebelY + (moebelBreite - durchmeser)*5 / 6;
		
		Arc2D.Double kochfeld4 = new Arc2D.Double(kochfeldX, kochfeldY, durchmeser, durchmeser, 0, 360, Arc2D.OPEN);
		g2d.setColor(Color.BLACK);
		g2d.fill(kochfeld4);
	}
}
