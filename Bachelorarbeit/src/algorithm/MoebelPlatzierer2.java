package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Fenster;
import model.Moebelstueck;
import model.RaumModell;
import model.Tuer;

public class MoebelPlatzierer2 {

	private static List<BlockierterBereich> blockierteBereiche = new ArrayList<>();
	private static List<BlockierterBereich> blockierteFensterBereiche = new ArrayList<>();
	private static List<Moebelstueck> nichtPlatzierbareMoebel = new ArrayList<>();

	public static void platziereMoebel(RaumModell raum) {
		blockierteBereiche.clear();
		nichtPlatzierbareMoebel.clear();
		List<Moebelstueck> moebel;

		// 1. Blockiere Bereiche vor Türen und Fenstern
		blockiereTuerbereiche(raum);
		blockiereFensterbereiche(raum);

		if (raum.getMoebel() == null || raum.getMoebel().isEmpty())
			return;
		moebel = new ArrayList<>(raum.getMoebel());

		// 2. Platziere Möbel, die nicht vor Fenster dürfen, in Ecken
		List<Moebelstueck> nichtVorFensterMoebel = new ArrayList<>();
		for (Moebelstueck m : moebel) {
			if (!m.isPlatzierbarVorFenster()) {
				nichtVorFensterMoebel.add(moebel.remove(moebel.indexOf(m)));
			}
		}
		for (Moebelstueck m : nichtVorFensterMoebel) {
			if (platziereInEcke(raum, m)) {
				nichtVorFensterMoebel.remove(nichtVorFensterMoebel.indexOf(m));
			}
		}

	}

	private static boolean platziereInEcke(RaumModell raum, Moebelstueck m) {
		double[][] ecken = { { raum.getX(), raum.getY() }, { raum.getX() + raum.getLaenge(), raum.getY() },
				{ raum.getX(), raum.getY() + raum.getBreite() },
				{ raum.getX() + raum.getLaenge(), raum.getY() + raum.getBreite() } };

		for (double[] ecke : ecken) {
			if (pruefeUndPlatziereInEcke(raum, m, ecke[0], ecke[1])) {
				return true;
			}
		}
		return false;
	}

	private static boolean pruefeUndPlatziereInEcke(RaumModell raum, Moebelstueck m, double x, double y) {
		boolean istLinkeEcke = (x == raum.getX());
		boolean istObereEcke = (y == raum.getY());

		// Prüfe, ob das Möbelstück in der Ecke platziert werden kann
		if (istLinkeEcke) {
			if (istObereEcke) {
				// Prüfe, ob das Möbelstück kollidiert
				if (m.getKeepOutOben() == 0 && m.getKeepOutLinks() == 0) {
					if (!kollidiert(raum, x + m.getKeepOutLinks(), y - m.getKeepOutOben(), m)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y - m.getKeepOutOben());

						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
				if (m.getKeepOutOben() == 0 && m.getKeepOutRechts() == 0) {
					m.drehen();
					if (!kollidiert(raum, x + m.getKeepOutLinks(), y - m.getKeepOutOben(), m)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y - m.getKeepOutOben());
						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
			} else if (!istObereEcke) {//TODO
				if (m.getKeepOutLinks() == 0 && m.getKeepOutUnten() == 0) {
					if (!kollidiert(raum, x + m.getKeepOutLinks(), y - m.getKeepOutOben(), m)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y - m.getKeepOutOben());

						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
			}
		}
		return false;
	}

	// Methode zum Überprüfen, ob ein Möbelstück mit einem belegten platz kollidiert
	private static boolean kollidiert(RaumModell raum, double x, double y, Moebelstueck m) {
		boolean kollidiert = false;
		for (BlockierterBereich bereich : blockierteBereiche) {
			if (bereich.y + bereich.breite <= y - m.getKeepOutUnten()// liegen die Bereiche vollständig übereinander?
					|| bereich.y >= y + m.getBreite() + m.getKeepOutOben()) {
				kollidiert = false;
				continue;
			} else {
				kollidiert = true;
			}
			if (bereich.x + bereich.laenge <= x - m.getKeepOutLinks()// liegen die Bereiche vollständig nebeneinander?
					|| bereich.x >= x + m.getLaenge() + m.getKeepOutRechts()) {
				kollidiert = false;
				continue;
			} else {
				kollidiert = true;
			}
			if (kollidiert) {
				return true;
			}
		}

		for (BlockierterBereich bereich : blockierteFensterBereiche) {
			if (bereich.y + bereich.breite <= y - m.getKeepOutUnten()// liegen die Bereiche vollständig übereinander?
					|| bereich.y >= y + m.getBreite() + m.getKeepOutOben()) {
				kollidiert = false;
				continue;
			} else {
				kollidiert = true;
			}
			if (bereich.x + bereich.laenge <= x - m.getKeepOutLinks()// liegen die Bereiche vollständig nebeneinander?
					|| bereich.x >= x + m.getLaenge() + m.getKeepOutRechts()) {
				kollidiert = false;
				continue;
			} else {
				kollidiert = true;
			}
			if (kollidiert) {
				return true;
			}
		}

		return false;
	}

	private static void blockiereTuerbereiche(RaumModell raum) {
		for (Tuer tuer : raum.getTueren()) {
			double x = tuer.getX();
			double y = tuer.getY();
			double breite = tuer.getBreite();
			double tiefe = 1.0; // 1 Meter Tiefe vor der Tür

			if (x == raum.getX()) { // Tür an der linken Wand
				blockierteBereiche.add(new BlockierterBereich(x, y, tiefe, breite));
			} else if (x == raum.getX() + raum.getLaenge()) { // Tür an der rechten Wand
				blockierteBereiche.add(new BlockierterBereich(x - tiefe, y, tiefe, breite));
			} else if (y == raum.getY() + raum.getBreite()) { // Tür an der oberen Wand
				blockierteBereiche.add(new BlockierterBereich(x, y - tiefe, breite, tiefe));
			} else if (y == raum.getY()) { // Tür an der unteren Wand
				blockierteBereiche.add(new BlockierterBereich(x, y, breite, tiefe));
			}
		}
	}

	private static void blockiereFensterbereiche(RaumModell raum) {
		for (Fenster fenster : raum.getFenster()) {
			double x = fenster.getX();
			double y = fenster.getY();
			double breite = fenster.getBreite();
			double tiefe = 1.5; // 1.5 Meter Tiefe vor dem Fenster

			if (x == raum.getX()) { // Fenster an der linken Wand
				blockierteFensterBereiche.add(new BlockierterBereich(x, y, tiefe, breite));
			} else if (x == raum.getX() + raum.getLaenge()) { // Fenster an der rechten Wand
				blockierteFensterBereiche.add(new BlockierterBereich(x - tiefe, y, tiefe, breite));
			} else if (y == raum.getY() + raum.getBreite()) { // Fenster an der oberen Wand
				blockierteFensterBereiche.add(new BlockierterBereich(x, y - tiefe, breite, tiefe));
			} else if (y == raum.getY()) { // Fenster an der unteren Wand
				blockierteFensterBereiche.add(new BlockierterBereich(x, y, breite, tiefe));
			}
		}
	}

	private static class BlockierterBereich {
		double x, y, laenge, breite;

		BlockierterBereich(double x, double y, double laenge, double breite) {
			this.x = x;
			this.y = y;
			this.laenge = laenge;
			this.breite = breite;
		}
	}
}
