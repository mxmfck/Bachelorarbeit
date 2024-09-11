package algorithm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import model.Raum;

public class TuerModell implements Cloneable {
//	Klasse für Türen

	private Raum vonRaum; // Raum, von dem die Tür ausgeht
	private Raum inRaum; // Raum, in den die Tür führt
	private double breite; // Breite der Tür
	private double x; // x-Koordinate der Tür
	private double y; // y-Koordinate der Tür
	private boolean linksOeffnend; // Gibt an, ob die Tür links öffnend ist
	private boolean horizontal;

	public TuerModell(Raum vonRaum, Raum inRaum, double breite) {
		this.vonRaum = vonRaum;
		this.inRaum = inRaum;
		this.breite = Math.round(breite * 100.0) / 100.0;
	}


	// Getter für alle Attribute
	public Raum getVonRaum() {
		return vonRaum;
	}

	public Raum getInRaum() {
		return inRaum;
	}

	public double getBreite() {
		return breite;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public boolean isLinksOeffnend() {
		return linksOeffnend;
	}

	// Setter für die x-Koordinate
	public void setX(double x) {
		this.x = Math.round(x * 100.0) / 100.0;
	}

	// Setter für die y-Koordinate

	public void setY(double y) {
		this.y = Math.round(y * 100.0) / 100.0;
	}

	// Setter für die Öffnungsrichtung
	public void setLinksOeffnend(boolean linksOeffnend) {
		this.linksOeffnend = linksOeffnend;
	}

	// Setter für die Ausrichtung
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {
		if (this.x == raum.getX()) {// linke wand
			if (this.isLinksOeffnend() == true) {
				int tuerX = offsetX + (int) ((raum.getRaumX()) * SCALE);
				int tuerY = offsetY + (int) ((this.getY()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX - tuerBreite, tuerY, tuerBreite * 2, tuerBreite * 2, 0, 90,
						Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			} else if (this.isLinksOeffnend() == false) {
				int tuerX = offsetX + (int) ((raum.getRaumX()) * SCALE);
				int tuerY = offsetY + (int) ((this.getY()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX - tuerBreite, tuerY - tuerBreite, tuerBreite * 2,
						tuerBreite * 2, 270, 90, Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			}
		} else if (this.x == raum.getX() + raum.getLaenge()) {
			if (this.isLinksOeffnend() == true) {
				int tuerX = offsetX + (int) ((raum.getRaumX() + raum.getRaumLaenge()) * SCALE);
				int tuerY = offsetY + (int) ((this.getY()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX - tuerBreite, tuerY, tuerBreite * 2, tuerBreite * 2, 90, 90,
						Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			} else if (this.isLinksOeffnend() == false) {
				int tuerX = offsetX + (int) ((raum.getRaumX() + raum.getRaumLaenge()) * SCALE);
				int tuerY = offsetY + (int) ((this.getY()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX - tuerBreite, tuerY - tuerBreite, tuerBreite * 2,
						tuerBreite * 2, 180, 90, Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			}
		} else if (this.y == raum.getY()) {
			if (this.isLinksOeffnend() == true) {

				int tuerX = offsetX + (int) ((this.getX()) * SCALE);
				int tuerY = offsetY + (int) ((raum.getRaumY()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX, tuerY - tuerBreite, tuerBreite * 2, tuerBreite * 2, 180, 90,
						Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			} else if (this.isLinksOeffnend() == false) {
				int tuerX = offsetX + (int) ((this.getX()) * SCALE);
				int tuerY = offsetY + (int) ((raum.getRaumY()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX - tuerBreite, tuerY - tuerBreite, tuerBreite * 2,
						tuerBreite * 2, 270, 90, Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			}
		} else if (this.y == raum.getY() + raum.getBreite()) {
			if (this.isLinksOeffnend() == true) {
				int tuerX = offsetX + (int) ((this.getX()) * SCALE);
				int tuerY = offsetY + (int) ((raum.getRaumY() + raum.getRaumBreite()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX - tuerBreite, tuerY - tuerBreite, tuerBreite * 2,
						tuerBreite * 2, 0, 90, Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			} else if (this.isLinksOeffnend() == false) {
				int tuerX = offsetX + (int) ((this.getX()) * SCALE);
				int tuerY = offsetY + (int) ((raum.getRaumY() + raum.getRaumBreite()) * SCALE);
				int tuerBreite = (int) (this.getBreite() * SCALE);

				Arc2D.Double arc = new Arc2D.Double(tuerX, tuerY - tuerBreite, tuerBreite * 2, tuerBreite * 2, 90, 90,
						Arc2D.PIE);
				g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fill(arc);
				g2d.setColor(Color.BLACK);
				g2d.draw(arc);
			}
		}
	}

}
