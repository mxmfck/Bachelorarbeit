package model.moebel;

import java.util.List;

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
			boolean stuehleLinks, boolean stuehleRechts) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
		this.anzahlStuehle = anzahlStuehle;
		this.stuehle = stuehle;
		this.stuehleOben = stuehleOben;
		this.stuehleUnten = stuehleUnten;
		this.stuehleLinks = stuehleLinks;
		this.stuehleRechts = stuehleRechts;
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

	public String toString() {
		String result= super.toString() + " | Anzahl Stühle: " + anzahlStuehle + " | Stühle oben: " + stuehleOben
				+ " | Stühle unten: " + stuehleUnten + " | Stühle links: " + stuehleLinks + " | Stühle rechts: "
				+ stuehleRechts + " | Stühle: \n  ";
		if (stuehle == null)
			return result;
		for (Stuhl stuhl : stuehle) {
			result += "  "+stuhl.toString() + "\n  ";
		}
		return result;
	}

}
