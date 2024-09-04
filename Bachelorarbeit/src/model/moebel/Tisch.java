package model.moebel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import algorithm.RaumModell;
import model.Moebelstueck;

public class Tisch extends Moebelstueck {
//    Klasse für Tische (Sonderfall von Moebelstueck)

	private int anzahlStuehle; // Anzahl der Stühle
	private List<Stuhl> stuehle; // Liste der Stühle
	private boolean stuehleOben; // Platzierung der Stühle
	private boolean stuehleUnten; // Platzierung der Stühle
	private boolean stuehleLinks; // Platzierung der Stühle
	private boolean stuehleRechts; // Platzierung der Stühle

	public Tisch(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten, int anzahlStuehle, List<Stuhl> stuehle, boolean stuehleOben, boolean stuehleUnten,
			boolean stuehleLinks, boolean stuehleRechts, boolean keepoutGesetzt) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.anzahlStuehle = anzahlStuehle;
		this.stuehle = stuehle;
		this.stuehleOben = stuehleOben;
		this.stuehleUnten = stuehleUnten;
		this.stuehleLinks = stuehleLinks;
		this.stuehleRechts = stuehleRechts;
		
		if (!keepoutGesetzt) {
			this.setKeepOutLinks(1);
			this.setKeepOutRechts(1);
			this.setKeepOutOben(1);
			this.setKeepOutUnten(1);
		}
	}

	// Getter und Setter
	public int getAnzahlStuehle() {
		return anzahlStuehle;
	}

	public List<Stuhl> getStuehle() {
		return stuehle;
	}

	public boolean isStuehleOben() {
		return stuehleOben;
	}

	public boolean isStuehleUnten() {
		return stuehleUnten;
	}

	public boolean isStuehleLinks() {
		return stuehleLinks;
	}

	public boolean isStuehleRechts() {
		return stuehleRechts;
	}

	public void setAnzahlStuehle(int anzahlStuehle) {
		this.anzahlStuehle = anzahlStuehle;
	}

	public void setStuehle(List<Stuhl> stuehle) {
		this.stuehle = stuehle;
	}

	public void setStuehleOben(boolean stuehleOben) {
		this.stuehleOben = stuehleOben;
	}

	public void setStuehleUnten(boolean stuehleUnten) {
		this.stuehleUnten = stuehleUnten;
	}

	public void setStuehleLinks(boolean stuehleLinks) {
		this.stuehleLinks = stuehleLinks;
	}

	public void setStuehleRechts(boolean stuehleRechts) {
		this.stuehleRechts = stuehleRechts;
	}

	public void drehen() {
		super.drehen();
		boolean tmp = stuehleOben;
		stuehleOben = stuehleRechts;
		stuehleRechts = stuehleUnten;
		stuehleUnten = stuehleLinks;
		stuehleLinks = tmp;
	}

	public String toString() {
		String result = super.toString() + " | Anzahl Stühle: " + anzahlStuehle + " | Stühle oben: " + stuehleOben
				+ " | Stühle unten: " + stuehleUnten + " | Stühle links: " + stuehleLinks + " | Stühle rechts: "
				+ stuehleRechts + " | Stühle: \n  ";
		if (stuehle == null)
			return result;
		for (Stuhl stuhl : stuehle) {
			result += "  " + stuhl.toString() + "\n  ";
		}
		return result;
	}

	public void draw(Graphics2D g2d, RaumModell raum, int offsetX, int offsetY, int SCALE) {

		double moebelX = offsetX + this.getX() * SCALE;
		double moebelY = offsetY + this.getY() * SCALE;
		double moebelLaenge = this.getLaenge() * SCALE;
		double moebelBreite = this.getBreite() * SCALE;

		// Tisch zeichnen
		Rectangle2D.Double rect = new Rectangle2D.Double(moebelX, moebelY, moebelLaenge, moebelBreite);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(rect);
		g2d.setColor(Color.BLACK);
		g2d.draw(rect);

		// Stühle zeichnen
		if (stuehle != null) {
			double stuhlLaenge = stuehle.get(0).getLaenge() * SCALE;
			double stuhlBreite = stuehle.get(0).getBreite() * SCALE;

			if (stuehleLinks && stuehleOben && stuehleRechts && stuehleUnten) {
				if (anzahlStuehle % 4 == 0) {
					int stuehleProSeite = anzahlStuehle / 4;

					for (int i = 0; i < stuehleProSeite; i++) {
						double stuhlX = moebelX + (moebelLaenge / stuehleProSeite) * i
								+ (moebelLaenge / stuehleProSeite - stuhlLaenge) / 2;
						double stuhlY = moebelY + moebelBreite;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge,
								stuhlBreite * 0.5);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX, stuhlY + stuhlBreite * 0.4,
								stuhlX + stuhlLaenge, stuhlY + stuhlBreite * 0.4);
						g2d.draw(linie);
					}

					for (int i = 0; i < stuehleProSeite; i++) {
						double stuhlX = moebelX + (moebelLaenge / stuehleProSeite) * i
								+ (moebelLaenge / stuehleProSeite - stuhlLaenge) / 2;
						double stuhlY = moebelY - stuhlBreite * 0.5;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge,
								stuhlBreite * 0.5);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX, stuhlY + stuhlBreite * 0.1,
								stuhlX + stuhlLaenge, stuhlY + stuhlBreite * 0.1);
						g2d.draw(linie);
					}

					for (int i = 0; i < stuehleProSeite; i++) {
						double stuhlX = moebelX - stuhlBreite * 0.5;
						double stuhlY = moebelY + (moebelBreite / stuehleProSeite) * i
								+ (moebelBreite / stuehleProSeite - stuhlLaenge) / 2;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge * 0.5,
								stuhlBreite);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX + stuhlLaenge * 0.1, stuhlY,
								stuhlX + stuhlLaenge * 0.1, stuhlY + stuhlBreite);
						g2d.draw(linie);
					}

					for (int i = 0; i < stuehleProSeite; i++) {
						double stuhlX = moebelX + moebelLaenge;
						double stuhlY = moebelY + (moebelBreite / stuehleProSeite) * i
								+ (moebelBreite / stuehleProSeite - stuhlLaenge) / 2;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge * 0.5,
								stuhlBreite);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX + stuhlLaenge * 0.4, stuhlY,
								stuhlX + stuhlLaenge * 0.4, stuhlY + stuhlBreite);
						g2d.draw(linie);
					}
				} else {

					int stuehleObenUnten = this.getLaenge() > this.getBreite() ?anzahlStuehle / 2-1: (anzahlStuehle - (anzahlStuehle / 2-1)*2)/2;
					int stuehleLinksRechts = this.getLaenge()> this.getBreite()?(anzahlStuehle - stuehleObenUnten*2)/2:anzahlStuehle / 2-1;
					
					for (int i = 0; i < stuehleObenUnten; i++) {
						double stuhlX = moebelX + (moebelLaenge / stuehleObenUnten) * i
								+ (moebelLaenge / stuehleObenUnten - stuhlLaenge) / 2;
						double stuhlY = moebelY + moebelBreite;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge,
								stuhlBreite * 0.5);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX, stuhlY + stuhlBreite * 0.4,
								stuhlX + stuhlLaenge, stuhlY + stuhlBreite * 0.4);
						g2d.draw(linie);
					}

					for (int i = 0; i < stuehleObenUnten; i++) {
						double stuhlX = moebelX + (moebelLaenge / stuehleObenUnten) * i
								+ (moebelLaenge / stuehleObenUnten - stuhlLaenge) / 2;
						double stuhlY = moebelY - stuhlBreite * 0.5;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge,
								stuhlBreite * 0.5);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX, stuhlY + stuhlBreite * 0.1,
								stuhlX + stuhlLaenge, stuhlY + stuhlBreite * 0.1);
						g2d.draw(linie);
					}

					for (int i = 0; i < stuehleLinksRechts; i++) {
						double stuhlX = moebelX - stuhlBreite * 0.5;
						double stuhlY = moebelY + (moebelBreite / stuehleLinksRechts) * i
								+ (moebelBreite / stuehleLinksRechts - stuhlLaenge) / 2;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge * 0.5,
								stuhlBreite);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX + stuhlLaenge * 0.1, stuhlY,
								stuhlX + stuhlLaenge * 0.1, stuhlY + stuhlBreite);
						g2d.draw(linie);
					}

					for (int i = 0; i < stuehleLinksRechts; i++) {
						double stuhlX = moebelX + moebelLaenge;
						double stuhlY = moebelY + (moebelBreite / stuehleLinksRechts) * i
								+ (moebelBreite / stuehleLinksRechts - stuhlLaenge) / 2;
						Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge * 0.5,
								stuhlBreite);
						g2d.setColor(Color.BLACK);
						g2d.draw(stuhlRect);

						Line2D.Double linie = new Line2D.Double(stuhlX + stuhlLaenge * 0.4, stuhlY,
								stuhlX + stuhlLaenge * 0.4, stuhlY + stuhlBreite);
						g2d.draw(linie);
					}
				}
			}else {
				if (stuehleOben) {
					double stuhlX = moebelX + (moebelLaenge - stuhlLaenge) / 2;
					double stuhlY = moebelY + moebelBreite;
					
					Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge, stuhlBreite*0.5);
					g2d.setColor(Color.BLACK);
					g2d.draw(stuhlRect);
					
					Line2D.Double linie = new Line2D.Double(stuhlX, stuhlY + stuhlBreite * 0.4, stuhlX + stuhlLaenge, stuhlY + stuhlBreite * 0.4);
					g2d.draw(linie);
				}
				if(stuehleUnten) {
					double stuhlX = moebelX
							+ (moebelLaenge - stuhlLaenge) / 2;
					double stuhlY = moebelY - stuhlBreite * 0.5;
					Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge,
							stuhlBreite * 0.5);
					g2d.setColor(Color.BLACK);
					g2d.draw(stuhlRect);

					Line2D.Double linie = new Line2D.Double(stuhlX, stuhlY + stuhlBreite * 0.1,
							stuhlX + stuhlLaenge, stuhlY + stuhlBreite * 0.1);
					g2d.draw(linie);
				}
				if(stuehleLinks) {
					double stuhlX = moebelX - stuhlLaenge * 0.5;
					double stuhlY = moebelY + (moebelBreite - stuhlBreite) / 2;

					Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge*0.5, stuhlBreite);
					g2d.setColor(Color.BLACK);
					g2d.draw(stuhlRect);

					Line2D.Double linie = new Line2D.Double(stuhlX + stuhlLaenge * 0.1, stuhlY,
							stuhlX + stuhlLaenge * 0.1, stuhlY + stuhlBreite);
					g2d.draw(linie);
				}
				if (stuehleRechts) {
					double stuhlX = moebelX + moebelLaenge;
					double stuhlY = moebelY + (moebelBreite - stuhlBreite) / 2;

					Rectangle2D.Double stuhlRect = new Rectangle2D.Double(stuhlX, stuhlY, stuhlLaenge*0.5, stuhlBreite);
					g2d.setColor(Color.BLACK);
					g2d.draw(stuhlRect);

					Line2D.Double linie = new Line2D.Double(stuhlX + stuhlLaenge * 0.4, stuhlY,
							stuhlX + stuhlLaenge * 0.4, stuhlY + stuhlBreite);
					g2d.draw(linie);
				}
			}
		}
	}
}
