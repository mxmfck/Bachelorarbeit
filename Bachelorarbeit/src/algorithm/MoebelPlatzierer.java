package algorithm;

import java.util.ArrayList;
import java.util.List;
import model.Fenster;
import model.Moebelstueck;
import model.RaumModell;
import model.Tuer;

public class MoebelPlatzierer {
	private static List<BlockierterBereich> blockierteBereiche = new ArrayList<>();
	private static List<Moebelstueck> nichtPlatzierbareMoebel = new ArrayList<>();

	public static void platziereMoebel(RaumModell raum) {
		blockierteBereiche.clear();
		nichtPlatzierbareMoebel.clear();
		
		if (raum.getMoebel() == null || raum.getMoebel().isEmpty())
			return;
		List<Moebelstueck> moebel = new ArrayList<>(raum.getMoebel());
		// 1. Blockiere Bereiche vor Türen
		blockiereTuerbereiche(raum);

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
		for (Moebelstueck m : nichtVorFensterMoebel) {
			if (!platziereImRaum(raum, m)) {
				nichtPlatzierbareMoebel.add(m);
			}
			nichtVorFensterMoebel.remove(nichtVorFensterMoebel.indexOf(m));
		}

		// 3. Sortiere restliche Möbel
//		List<Moebelstueck> restlicheMoebel = raum.getMoebel().stream().filter(m -> m.isPlatzierbarVorFenster())
//				.collect(Collectors.toList());

		List<Moebelstueck> moebelMitKeepoutObenNull = new ArrayList<>();
		
		for (Moebelstueck m : raum.getMoebel()) {
			if (m.getKeepOutOben() == 0) {
				moebelMitKeepoutObenNull.add(moebel.remove(moebel.indexOf(m)));
			}
		}

		List<Moebelstueck> andereMoebel = moebel;

		// 4. Platziere Möbel mit KeepOutOben = 0 entlang der Wände
		platziereEntlangWaende(raum, moebelMitKeepoutObenNull);

		// 5. Versuche, die übrigen Möbel zu platzieren
		for (Moebelstueck m : andereMoebel) {
			if (!platziereImRaum(raum, m)) {
				nichtPlatzierbareMoebel.add(m);
			}
		}

		// Ausgabe der nicht platzierbaren Möbel
		for (Moebelstueck m : nichtPlatzierbareMoebel) {
			System.out.println("Konnte " + m.getClass().getSimpleName() + " nicht platzieren");
		}
	}

	private static void blockiereTuerbereiche(RaumModell raum) {
		for (Tuer tuer : raum.getTueren()) {
			double x = tuer.getX();
			double y = tuer.getY();
			double breite = tuer.getBreite();
			double tiefe = 1.0; // 1 Meter Tiefe vor der Tür

			if (x == raum.getX()) { // Tür an der linken Wand
				blockierteBereiche.add(new BlockierterBereich(x, y, tiefe, breite));
			} else if (x == raum.getX()+raum.getLaenge()) { // Tür an der rechten Wand
				blockierteBereiche.add(new BlockierterBereich(x - tiefe, y, tiefe, breite));
			} else if (y == raum.getY()+raum.getBreite()) { // Tür an der oberen Wand
				blockierteBereiche.add(new BlockierterBereich(x, y-tiefe, breite, tiefe));
			} else if (y == raum.getY()) { // Tür an der unteren Wand
				blockierteBereiche.add(new BlockierterBereich(x, y, breite, tiefe));
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

		if (istLinkeEcke && m.getKeepOutLinks() == 0) {
			if (istObereEcke && m.getKeepOutOben() == 0) {
				if (!istVorFenster(raum, x, y-m.getBreite(), m) && !kollidiert(raum, x, y-(m.getBreite()), m)) {
					m.setX(x);
					m.setY(y);
					System.out.println(
							m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
					return true;
				}
			}
		}
		// Prüfe, ob das Möbelstück in der Ecke platziert werden kann
//		if (istLinkeEcke && m.getKeepOutLinks() == 0 && m.getKeepOutRechts() == 0) {
//			if (istObereEcke && (m.getKeepOutOben() == 0 || m.getKeepOutUnten() == 0)) {
//				// Möbelstück muss eventuell gedreht werden
//				if (y == 0 && m.getKeepOutOben() != 0) {
//					m.drehen();
//				} else if (y == raum.getBreite() && m.getKeepOutUnten() != 0) {
//					m.drehen();
//				}
//
//				// Prüfe, ob das Möbelstück vor einem Fenster steht
//				if (!istVorFenster(raum, x, y, m) && !kollidiert(raum, x, y, m)) {
//					m.setX(x - (x == raum.getLaenge() ? m.getLaenge() : 0));
//					m.setY(y - (y == raum.getBreite() ? m.getBreite() : 0));
//					System.out.println(
//							m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
//					return true;
//				}
//			}
//		}
		return false;
	}

	private static void platziereEntlangWaende(RaumModell raum, List<Moebelstueck> moebel) {
		// Obere Wand
		for (Moebelstueck m : moebel) {
			if (platziereAnWand(raum, m, 0, 0, true))
				moebel.remove(m);
		}
		// Rechte Wand
		for (Moebelstueck m : moebel) {
			if (platziereAnWand(raum, m, raum.getLaenge(), 0, false))
				moebel.remove(m);
		}
		// Untere Wand
		for (Moebelstueck m : moebel) {
			if (platziereAnWand(raum, m, 0, raum.getBreite(), true))
				moebel.remove(m);
		}
		// Linke Wand
		for (Moebelstueck m : moebel) {
			if (platziereAnWand(raum, m, 0, 0, false))
				moebel.remove(m);
		}
	}

	private static boolean platziereAnWand(RaumModell raum, Moebelstueck m, double startX, double startY,
			boolean horizontal) {
		if (m.getKeepOutOben() != 0)
			return false;

		double endX = horizontal ? raum.getLaenge() : startX;
		double endY = horizontal ? startY : raum.getBreite();

		for (double x = startX; x <= endX; x += 0.1) {
			for (double y = startY; y <= endY; y += 0.1) {
				if (!kollidiert(raum, x, y, m)) {
					m.setX(x);
					m.setY(y);
					System.out.println(
							m.getClass().getSimpleName() + " an Wand platziert: x=" + m.getX() + ", y=" + m.getY());
					return true;
				}
			}
		}
		return false;
	}

	private static boolean platziereImRaum(RaumModell raum, Moebelstueck m) {
		for (double x = 0; x <= raum.getLaenge() - m.getLaenge(); x += 0.1) {
			for (double y = 0; y <= raum.getBreite() - m.getBreite(); y += 0.1) {
				if (!kollidiert(raum, x, y, m)) {
					m.setX(x);
					m.setY(y);
					System.out.println(
							m.getClass().getSimpleName() + " im Raum platziert: x=" + m.getX() + ", y=" + m.getY());
					return true;
				}
			}
		}
		return false;
	}

	private static boolean kollidiert(RaumModell raum, double x, double y, Moebelstueck m) {
		// Prüfe Kollision mit anderen Möbeln
		for (Moebelstueck anderesM : raum.getMoebel()) {
			if (anderesM != m && ueberschneiden(x, y, m, anderesM.getX(), anderesM.getY(), anderesM)) {
				return true;
			}
		}
		// Prüfe Kollision mit blockierten Bereichen
		for (BlockierterBereich b : blockierteBereiche) {
			if (ueberschneiden(x, y, m, b.x, b.y, b.laenge, b.breite)) {
				return true;
			}
		}
		return false;
	}

	private static boolean istVorFenster(RaumModell raum, double x, double y, Moebelstueck m) {
		for (Fenster f : raum.getFenster()) {
			if (ueberschneidetFenster(x, y, m.getLaenge(), m.getBreite(), f)) {
				return true;
			}
		}
		return false;
	}

	private static boolean ueberschneidetFenster(double x, double y, double moebelLaenge, double moebelBreite,
			Fenster f) {
		if (f.isHorizontal()) {
			return x < f.getX() + f.getBreite() && x + moebelBreite > f.getX() && y < f.getY() + 0.1 && // Annahme:
																										// Fenster hat
																										// eine kleine
																										// Dicke
					y + moebelLaenge > f.getY();
		} else {
			return x < f.getX() + 0.1 && // Annahme: Fenster hat eine kleine Dicke
					x + moebelBreite > f.getX() && y < f.getY() + f.getBreite() && y + moebelLaenge > f.getY();
		}
	}

	private static boolean ueberschneiden(double x1, double y1, Moebelstueck m1, double x2, double y2,
			Moebelstueck m2) {
		return ueberschneiden(x1, y1, m1.getLaenge(), m1.getBreite(), x2, y2, m2.getLaenge(), m2.getBreite());
	}

	private static boolean ueberschneiden(double x1, double y1, Moebelstueck m, double x2, double y2, double breite2,
			double hoehe2) {
		return ueberschneiden(x1, y1, m.getLaenge(), m.getBreite(), x2, y2, breite2, hoehe2);
	}

	private static boolean ueberschneiden(double x1, double y1, double breite1, double hoehe1, double x2, double y2,
			double breite2, double hoehe2) {
		return x1 < x2 + breite2 && x1 + breite1 > x2 && y1 < y2 + hoehe2 && y1 + hoehe1 > y2;
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