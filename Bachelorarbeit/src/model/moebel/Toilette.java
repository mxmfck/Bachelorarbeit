package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Toilette extends Moebelstueck {

	public Toilette(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

//		// Schrank zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
//		g2d.draw(rect);

		if (this.getAusrichtung() == 0) {
			// Spühlkasten
			double kastenLaenge = moebelLaenge;
			double kastenBreite = moebelBreite * 0.1;
			double kastenX = moebelX;
			double kastenY = moebelY + moebelBreite - kastenBreite;

			Rectangle2D.Double kasten = new Rectangle2D.Double(kastenX, kastenY, kastenLaenge, kastenBreite);
			g2d.draw(kasten);

			// Schüssel
			double durchmesser = moebelLaenge;
			double schuesselX = moebelX;
			double schuesselY = moebelY;

			Arc2D.Double schuessel = new Arc2D.Double(schuesselX, schuesselY, durchmesser, durchmesser, 0, 180,
					Arc2D.OPEN);
			g2d.draw(schuessel);

			// Verbindung

//			double verbindungBreite = moebelBreite - kastenBreite - durchmesser / 2;
//			double verbindungLaenge = moebelLaenge;
//			double verbindungX = moebelX;
//			double verbindungY = moebelY + durchmesser / 2;
//
//			Rectangle2D.Double verbindung = new Rectangle2D.Double(verbindungX, verbindungY, verbindungLaenge,
//					verbindungBreite);
//			g2d.setColor(Color.LIGHT_GRAY);
//			g2d.fill(verbindung);

			Line2D.Double linieLinks = new Line2D.Double(moebelX, moebelY + durchmesser, moebelX,
					moebelY + moebelBreite - kastenBreite);
			Line2D.Double linieRechts = new Line2D.Double(moebelX + moebelLaenge, moebelY + durchmesser,
					moebelX + moebelLaenge, moebelY + moebelBreite - kastenBreite);

			g2d.setColor(Color.BLACK);
			g2d.draw(linieLinks);
			g2d.draw(linieRechts);

			// Deckel
			double deckelDurchemesser = durchmesser * 0.8;
			double deckelX = moebelX + (durchmesser - deckelDurchemesser) / 2;
			double deckelY = moebelY + durchmesser / 2 + deckelDurchemesser / 2;

			Arc2D.Double deckel = new Arc2D.Double(deckelX, deckelY, deckelDurchemesser, deckelDurchemesser, 0, 360,
					Arc2D.OPEN);
			g2d.draw(deckel);

		} else if (this.getAusrichtung() == 180) {

			// Spühlkasten
			double kastenLaenge = moebelLaenge;
			double kastenBreite = moebelBreite * 0.1;
			double kastenX = moebelX;
			double kastenY = moebelY;

			Rectangle2D.Double kasten = new Rectangle2D.Double(kastenX, kastenY, kastenLaenge, kastenBreite);
			g2d.draw(kasten);

			// Schüssel
			double durchmesser = moebelLaenge;
			double schuesselX = moebelX;
			double schuesselY = moebelY + moebelBreite - durchmesser;

			Arc2D.Double schuessel = new Arc2D.Double(schuesselX, schuesselY, durchmesser, durchmesser, 180, 180,
					Arc2D.OPEN);
			g2d.draw(schuessel);

			// Verbindung
			double verbindungBreite = moebelBreite - kastenBreite - durchmesser / 2;
//			double verbindungLaenge = moebelLaenge;
//			double verbindungX = moebelX;
//			double verbindungY = moebelY + kastenBreite;
//
//			Rectangle2D.Double verbindung = new Rectangle2D.Double(verbindungX, verbindungY, verbindungLaenge,
//					verbindungBreite);
//			g2d.setColor(Color.LIGHT_GRAY);
//			g2d.fill(verbindung);

			Line2D.Double linieLinks = new Line2D.Double(moebelX, moebelY + kastenBreite, moebelX,
					moebelY + moebelBreite - durchmesser / 2);
			Line2D.Double linieRechts = new Line2D.Double(moebelX + moebelLaenge, moebelY + kastenBreite,
					moebelX + moebelLaenge, moebelY + moebelBreite - durchmesser / 2);

			g2d.setColor(Color.BLACK);
			g2d.draw(linieLinks);
			g2d.draw(linieRechts);

			// Deckel
			double deckelDurchemesser = durchmesser * 0.8;
			double deckelX = moebelX + (durchmesser - deckelDurchemesser) / 2;
			double deckelY = moebelY + kastenBreite + verbindungBreite - deckelDurchemesser / 2;

			Arc2D.Double deckel = new Arc2D.Double(deckelX, deckelY, deckelDurchemesser, deckelDurchemesser, 0, 360,
					Arc2D.OPEN);
			g2d.draw(deckel);

		} else if (this.getAusrichtung() == 90) {

			// Spühlkasten
			double kastenLaenge = moebelLaenge * 0.1;
			double kastenBreite = moebelBreite;
			double kastenX = moebelX;
			double kastenY = moebelY;

			Rectangle2D.Double kasten = new Rectangle2D.Double(kastenX, kastenY, kastenLaenge, kastenBreite);
			g2d.draw(kasten);

			// Schüssel
			double durchmesser = moebelBreite;
			double schuesselX = moebelX + moebelLaenge - durchmesser;
			double schuesselY = moebelY;

			Arc2D.Double schuessel = new Arc2D.Double(schuesselX, schuesselY, durchmesser, durchmesser, 270, 180,
					Arc2D.OPEN);
			g2d.draw(schuessel);

			// Verbindung
//			double verbindungBreite = moebelBreite;
			double verbindungLaenge = moebelLaenge - kastenLaenge - durchmesser / 2;
//			double verbindungX = moebelX+kastenLaenge;
//			double verbindungY = moebelY;
//
//			Rectangle2D.Double verbindung = new Rectangle2D.Double(verbindungX, verbindungY, verbindungLaenge,
//					verbindungBreite);
//			g2d.setColor(Color.LIGHT_GRAY);
//			g2d.fill(verbindung);

			Line2D.Double linieOben = new Line2D.Double(moebelX + kastenLaenge, moebelY + moebelBreite,
					moebelX + moebelLaenge - durchmesser / 2, moebelY + moebelBreite);
			Line2D.Double linieUnten = new Line2D.Double(moebelX + kastenLaenge, moebelY,
					moebelX + moebelLaenge - durchmesser / 2, moebelY);

			g2d.setColor(Color.BLACK);
			g2d.draw(linieOben);
			g2d.draw(linieUnten);

			// Deckel
			double deckelDurchemesser = durchmesser * 0.8;
			double deckelX = moebelX + kastenLaenge + verbindungLaenge - deckelDurchemesser / 2;
			double deckelY = moebelY + (durchmesser - deckelDurchemesser) / 2;

			Arc2D.Double deckel = new Arc2D.Double(deckelX, deckelY, deckelDurchemesser, deckelDurchemesser, 0, 360,
					Arc2D.OPEN);
			g2d.draw(deckel);

		} else if (this.getAusrichtung() == 270) {

			// Spühlkasten
			double kastenLaenge = moebelLaenge * 0.1;
			double kastenBreite = moebelBreite;
			double kastenX = moebelX + moebelLaenge - kastenLaenge;
			double kastenY = moebelY;

			Rectangle2D.Double kasten = new Rectangle2D.Double(kastenX, kastenY, kastenLaenge, kastenBreite);
			g2d.draw(kasten);

			// Schüssel
			double durchmesser = moebelBreite;
			double schuesselX = moebelX;
			double schuesselY = moebelY;

			Arc2D.Double schuessel = new Arc2D.Double(schuesselX, schuesselY, durchmesser, durchmesser, 90, 180,
					Arc2D.OPEN);
			g2d.draw(schuessel);

			// Verbindung
			double verbindungLaenge = moebelLaenge - kastenLaenge - durchmesser / 2;

			Line2D.Double linieOben = new Line2D.Double(moebelX + moebelLaenge - kastenLaenge, moebelY + moebelBreite,
					moebelX + durchmesser / 2, moebelY + moebelBreite);
			Line2D.Double linieUnten = new Line2D.Double(moebelX + moebelLaenge - kastenLaenge, moebelY,
					moebelX + durchmesser / 2, moebelY);

			g2d.setColor(Color.BLACK);
			g2d.draw(linieOben);
			g2d.draw(linieUnten);

			// Deckel
			double deckelDurchemesser = durchmesser * 0.8;
			double deckelX = moebelX + durchmesser - deckelDurchemesser;
			double deckelY = moebelY + (durchmesser - deckelDurchemesser) / 2;

			Arc2D.Double deckel = new Arc2D.Double(deckelX, deckelY, deckelDurchemesser, deckelDurchemesser, 0, 360,
					Arc2D.OPEN);
			g2d.draw(deckel);

		}
	}
}
