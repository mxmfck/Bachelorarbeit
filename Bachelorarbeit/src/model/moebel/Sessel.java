package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Sessel extends Moebelstueck {

	public Sessel(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Sessel zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);
		
		if(this.getAusrichtung()==0) {
			Line2D.Double linieLinks =new Line2D.Double(moebelX+0.1*moebelLaenge, moebelY, moebelX+0.1*moebelLaenge, moebelY+moebelBreite);
			Line2D.Double linieRechts =new Line2D.Double(moebelX+0.9*moebelLaenge, moebelY, moebelX+0.9*moebelLaenge, moebelY+moebelBreite);
			Line2D.Double linieOben =new Line2D.Double(moebelX+0.1*moebelLaenge, moebelY+0.9*moebelBreite, moebelX+0.9*moebelLaenge, moebelY+0.9*moebelBreite);
			g2d.setColor(Color.BLACK);
			g2d.draw(linieLinks);
			g2d.draw(linieRechts);
			g2d.draw(linieOben);
		
		}else if(this.getAusrichtung()==180) {
			
			Line2D.Double linieLinks =new Line2D.Double(moebelX+0.1*moebelLaenge, moebelY, moebelX+0.1*moebelLaenge, moebelY+moebelBreite);
			Line2D.Double linieRechts =new Line2D.Double(moebelX+0.9*moebelLaenge, moebelY, moebelX+0.9*moebelLaenge, moebelY+moebelBreite);
			Line2D.Double linieUnten =new Line2D.Double(moebelX+0.1*moebelLaenge, moebelY+0.1*moebelBreite, moebelX+0.9*moebelLaenge, moebelY+0.1*moebelBreite);
			
			g2d.setColor(Color.BLACK);
			g2d.draw(linieLinks);
			g2d.draw(linieRechts);
			g2d.draw(linieUnten);
			
		}else if(this.getAusrichtung()==90) {
			
			Line2D.Double linieLinks =new Line2D.Double(moebelX, moebelY+0.1*moebelBreite, moebelX+moebelLaenge, moebelY+0.1*moebelBreite);
			Line2D.Double linieOben =new Line2D.Double(moebelX, moebelY+0.1*moebelBreite, moebelX, moebelY+0.9*moebelBreite);
			Line2D.Double linieUnten =new Line2D.Double(moebelX, moebelY+0.9*moebelBreite, moebelX+moebelLaenge, moebelY+0.9*moebelBreite);
			
			g2d.setColor(Color.BLACK);
			g2d.draw(linieLinks);
			g2d.draw(linieOben);
			g2d.draw(linieUnten);
			
		}else if(this.getAusrichtung()==270) {
			
			Line2D.Double linieRechts =new Line2D.Double(moebelX, moebelY+0.1*moebelBreite, moebelX+moebelLaenge, moebelY+0.1*moebelBreite);
			Line2D.Double linieOben =new Line2D.Double(moebelX+moebelLaenge, moebelY+0.1*moebelBreite, moebelX+moebelLaenge, moebelY+0.9*moebelBreite);
			Line2D.Double linieUnten =new Line2D.Double(moebelX, moebelY+0.9*moebelBreite, moebelX+moebelLaenge, moebelY+0.9*moebelBreite);
			
			g2d.setColor(Color.BLACK);
			g2d.draw(linieRechts);
			g2d.draw(linieOben);
			g2d.draw(linieUnten);
		}
	}
}
