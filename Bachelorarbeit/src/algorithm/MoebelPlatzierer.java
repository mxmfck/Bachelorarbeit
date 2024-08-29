package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Moebelstueck;

public class MoebelPlatzierer {

	public static void einrichten(Grundriss grundriss) {
		for (RaumModell raum : grundriss.getRaeume()) {
			platziereMoebel(raum);
		}
	}

	public static void platziereMoebel(RaumModell raum) {
		List<BlockierterBereich> blockierteBereiche = new ArrayList<>();
		List<BlockierterBereich> blockierteFensterBereiche = new ArrayList<>();
		int counter = 0;
		List<Moebelstueck> nichtPlatzierbareMoebel = new ArrayList<>();
		do {
			blockierteBereiche.clear();
			nichtPlatzierbareMoebel.clear();
			List<Moebelstueck> moebel;

			//TODO Remove Test
			if(raum.getName().contains("Schlafzimmer")) {
				System.out.println("");
			}
			
			// 1. Blockiere Bereiche vor Türen und Fenstern
			blockiereTuerbereiche(raum, blockierteBereiche);
			blockiereFensterbereiche(raum, blockierteFensterBereiche);

			if (raum.getMoebel() == null || raum.getMoebel().isEmpty())
				return;
			moebel = new ArrayList<>(raum.getMoebel());
			Collections.shuffle(moebel);

			if (raum.getName().equals("Küche")) {
				System.out.println("");
			}
			// 2. Platziere Möbel, die nicht vor Fenster dürfen, in Ecken
			List<Moebelstueck> nichtVorFensterMoebel = new ArrayList<>();
			for (int i = 0; i < moebel.size(); i++) {
				if (!moebel.get(i).isPlatzierbarVorFenster()) {
					nichtVorFensterMoebel.add(moebel.remove(i));

					i--;
				}
			}
			for (int i = 0; i < nichtVorFensterMoebel.size(); i++) {
				if (platziereInEcke(raum, nichtVorFensterMoebel.get(i), blockierteBereiche,
						blockierteFensterBereiche)) {
					nichtVorFensterMoebel.remove(nichtVorFensterMoebel.get(i));
					i--;
				}

			}

			for (int i = 0; i < nichtVorFensterMoebel.size(); i++) {
				if (platziereAnWand(raum, nichtVorFensterMoebel.get(i), blockierteBereiche,
						blockierteFensterBereiche)) {
					nichtVorFensterMoebel.remove(nichtVorFensterMoebel.get(i));
					i--;
				} else {
					nichtPlatzierbareMoebel.add(nichtVorFensterMoebel.get(i));
					i--;
				}
			}


			blockierteFensterBereiche.clear();

			// 3. Sortiere restliche Möbel
			List<Moebelstueck> wandMoebel = new ArrayList<>();
			for (int i = 0; i < moebel.size(); i++) {
				if (moebel.get(i).getKeepOutOben() == 0) {
					wandMoebel.add(moebel.remove(i));
					i--;
				}
			}

			for (int i = 0; i < wandMoebel.size(); i++) {
				if (!platziereAnWand(raum, wandMoebel.get(i), blockierteBereiche, blockierteFensterBereiche)) {
					nichtPlatzierbareMoebel.add(wandMoebel.get(i));
				}
				wandMoebel.remove(wandMoebel.get(i));
				i--;
			}

			List<Moebelstueck> andereMoebel = moebel;
			andereMoebel.addAll(nichtPlatzierbareMoebel);
			andereMoebel.addAll(moebel);
			nichtPlatzierbareMoebel.clear();
			for (int i = 0; i < andereMoebel.size(); i++) {
				if (!platziereImRaum(raum, andereMoebel.get(i), blockierteBereiche, blockierteFensterBereiche)) {
					nichtPlatzierbareMoebel.add(andereMoebel.remove(i));
					i--;
				}
			}
			counter++;
		} while (nichtPlatzierbareMoebel.size() > 0 && counter < 50);
		if (counter >= 50) {
			throw new RuntimeException("Es konnten nicht alle Möbel im Raum " + raum.getName()
					+ " platziert werden. Bitte überprüfen Sie die Anforderungen.");
		}

	}

	private static boolean platziereImRaum(RaumModell raum, Moebelstueck m, List<BlockierterBereich> blockierteBereiche,
			List<BlockierterBereich> blockierteFensterBereiche) {
		while (m.getAusrichtung() != 0)
			m.drehen();

		double startX = raum.getRaumX();
		double endX = raum.getRaumX() + raum.getRaumLaenge();
		double startY = raum.getRaumY();
		double endY = raum.getRaumY() + raum.getRaumBreite();

		for (double x = startX + m.getKeepOutLinks(); x + m.getLaenge() + m.getKeepOutRechts() <= endX; x += 0.1) {
			for (double y = startY + m.getKeepOutUnten(); y + m.getBreite() + m.getKeepOutOben() <= endY; y += 0.1) {
				if (!kollidiert(raum, x, y, m, blockierteBereiche, blockierteFensterBereiche)) {
					m.setX(x);
					m.setY(y);
					blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
							m.getY() - m.getKeepOutUnten(), m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
							m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
					System.out.println(
							m.getClass().getSimpleName() + " im Raum platziert: x=" + m.getX() + ", y=" + m.getY());
					return true;
				}
			}
		}

		return false;
	}

	private static boolean platziereAnWand(RaumModell raum, Moebelstueck m, List<BlockierterBereich> blockierteBereiche,
			List<BlockierterBereich> blockierteFensterBereiche) {
		while (m.getAusrichtung() != 0)
			m.drehen();

		double startX = raum.getRaumX();
		double endX = raum.getRaumX() + raum.getRaumLaenge();
		double startY = raum.getRaumY();
		double endY = raum.getRaumY() + raum.getRaumBreite();

		// Linke Wand
		while (m.getAusrichtung() != 90)
			m.drehen();

		for (double y = startY + m.getKeepOutUnten(); y + m.getBreite()
				+ m.getKeepOutLinks() <= endY; y = Math.round((y + 0.1) * 100.0) / 100.0) {
			double x = raum.getRaumX() + m.getKeepOutLinks();
			if (!kollidiert(raum, x, y, m, blockierteBereiche, blockierteFensterBereiche)) {
				m.setX(x);
				m.setY(y);
				blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
						m.getY() - m.getKeepOutUnten(), m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
						m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
				System.out.println(
						m.getClass().getSimpleName() + " an Wand platziert: x=" + m.getX() + ", y=" + m.getY());
				return true;
			}
		}

		// Untere Wand
		while (m.getAusrichtung() != 180)
			m.drehen();

		for (double x = startX + m.getKeepOutLinks(); x + m.getLaenge()
				+ m.getKeepOutRechts() <= endX; x = Math.round((x + 0.1) * 100.0) / 100.0) {
			double y = raum.getRaumY() + m.getKeepOutUnten();
			if (!kollidiert(raum, x, y, m, blockierteBereiche, blockierteFensterBereiche)) {
				m.setX(x);
				m.setY(y);
				blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
						m.getY() - m.getKeepOutUnten(), m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
						m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
				System.out.println(
						m.getClass().getSimpleName() + " an Wand platziert: x=" + m.getX() + ", y=" + m.getY());
				return true;
			}
		}

		// Rechte Wand
		while (m.getAusrichtung() != 270)
			m.drehen();

		for (double y = startY + m.getKeepOutUnten(); y + m.getBreite()
				+ m.getKeepOutOben() <= endY; y = Math.round((y + 0.1) * 100.0) / 100.0) {
			double x = endX - (m.getLaenge() + m.getKeepOutRechts());
			if (!kollidiert(raum, x, y, m, blockierteBereiche, blockierteFensterBereiche)) {
				m.setX(x);
				m.setY(y);
				blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
						m.getY() - m.getKeepOutUnten(), m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
						m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
				System.out.println(
						m.getClass().getSimpleName() + " an Wand platziert: x=" + m.getX() + ", y=" + m.getY());
				return true;
			}
		}

		// Obere Wand
		while(m.getAusrichtung()!=0)
			m.drehen();
		for (double x = startX + m.getKeepOutLinks(); x + m.getLaenge()
				+ m.getKeepOutRechts() <= endX; x = Math.round((x + 0.1) * 100.0) / 100.0) {
			double y = endY - (m.getBreite() + m.getKeepOutOben());
			if (!kollidiert(raum, x, y, m, blockierteBereiche, blockierteFensterBereiche)) {
				m.setX(x);
				m.setY(y);
				blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
						m.getY() - m.getKeepOutUnten(), m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
						m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
				System.out.println(
						m.getClass().getSimpleName() + " an Wand platziert: x=" + m.getX() + ", y=" + m.getY());
				return true;
			}
		}

		return false;
	}

	private static boolean platziereInEcke(RaumModell raum, Moebelstueck m, List<BlockierterBereich> blockierteBereiche,
			List<BlockierterBereich> blockierteFenster) {
		double[][] ecken = { { raum.getRaumX(), raum.getRaumY() + raum.getRaumBreite() }, { raum.getRaumX(), raum.getRaumY() },
				{ raum.getRaumX() + raum.getRaumLaenge(), raum.getRaumY() },
				{ raum.getRaumX() + raum.getRaumLaenge(), raum.getRaumY() + raum.getRaumBreite() } };

		for (double[] ecke : ecken) {
			if (pruefeUndPlatziereInEcke(raum, m, ecke[0], ecke[1], blockierteBereiche, blockierteFenster)) {
				return true;
			}
		}
		return false;
	}

	private static boolean pruefeUndPlatziereInEcke(RaumModell raum, Moebelstueck m, double x, double y,
			List<BlockierterBereich> blockierteBereiche, List<BlockierterBereich> blockierteFensterBereiche) {
		boolean istLinkeEcke = (x == raum.getRaumX());
		boolean istObereEcke = (y == raum.getRaumY() + raum.getRaumBreite());

		// Prüfe, ob das Möbelstück in der Ecke platziert werden kann
		if (istLinkeEcke) {
			if (istObereEcke) {
				// Prüfe, ob das Möbelstück kollidiert
				while (m.getAusrichtung() != 0)
					m.drehen();
				if (m.getKeepOutOben() == 0 && m.getKeepOutLinks() == 0) {
					if (!kollidiert(raum, x + m.getKeepOutLinks(), y - (m.getKeepOutOben() + m.getBreite()), m,
							blockierteBereiche, blockierteFensterBereiche)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y - (m.getKeepOutOben() + m.getBreite()));
						blockierteBereiche.add(
								new BlockierterBereich(m.getX() - m.getKeepOutLinks(), m.getY() - m.getKeepOutUnten(),
										m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
										m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));

						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
				while (m.getAusrichtung() != 0)
					m.drehen();
				if (m.getKeepOutOben() == 0 && m.getKeepOutRechts() == 0) {
					while (m.getAusrichtung() != 90) {
						m.drehen();
					}
					if (!kollidiert(raum, x + m.getKeepOutLinks(), y - (m.getKeepOutOben() + m.getBreite()), m,
							blockierteBereiche, blockierteFensterBereiche)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y - (m.getKeepOutOben() + m.getBreite()));
						blockierteBereiche.add(
								new BlockierterBereich(m.getX() - m.getKeepOutLinks(), m.getY() - m.getKeepOutUnten(),
										m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
										m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
			} else if (!istObereEcke) {

				while (m.getAusrichtung() != 0)
					m.drehen();
				if (m.getKeepOutLinks() == 0 && m.getKeepOutOben() == 0) {
					while (m.getAusrichtung() != 90)
						m.drehen();
					if (!kollidiert(raum, x + m.getKeepOutLinks(), y + m.getKeepOutUnten(), m, blockierteBereiche,
							blockierteFensterBereiche)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y + m.getKeepOutUnten());
						blockierteBereiche.add(
								new BlockierterBereich(m.getX() - m.getKeepOutLinks(), m.getY() - m.getKeepOutUnten(),
										m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
										m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
				while (m.getAusrichtung() != 0)
					m.drehen();
				if (m.getKeepOutOben() == 0 && m.getKeepOutRechts() == 0) {
					while (m.getAusrichtung() != 180)
						m.drehen();

					if (!kollidiert(raum, x + m.getKeepOutLinks(), y + m.getKeepOutUnten(), m, blockierteBereiche,
							blockierteFensterBereiche)) {
						m.setX(x + m.getKeepOutLinks());
						m.setY(y + m.getKeepOutUnten());
						blockierteBereiche.add(
								new BlockierterBereich(m.getX() - m.getKeepOutLinks(), m.getY() - m.getKeepOutUnten(),
										m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
										m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
				}
			}
		} else if (!istLinkeEcke) {
			if (!istObereEcke) {
				while (m.getAusrichtung() != 0)
					m.drehen();
				if (m.getKeepOutOben() == 0 && m.getKeepOutLinks() == 0) {
					while (m.getAusrichtung() != 180)
						m.drehen();
					if (!kollidiert(raum, x - (m.getKeepOutRechts() + m.getLaenge()), y + m.getKeepOutUnten(), m,
							blockierteBereiche, blockierteFensterBereiche)) {
						m.setX(x - (m.getKeepOutRechts() + m.getLaenge()));
						m.setY(y + m.getKeepOutUnten());
						blockierteBereiche.add(
								new BlockierterBereich(m.getX() - m.getKeepOutLinks(), m.getY() - m.getKeepOutUnten(),
										m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
										m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
					while (m.getAusrichtung() != 0)
						m.drehen();
					if (m.getKeepOutOben() == 0 && m.getKeepOutRechts() == 0) {

						while (m.getAusrichtung() != 270)
							m.drehen();
						if (!kollidiert(raum, x - (m.getKeepOutRechts() + m.getLaenge()), y + m.getKeepOutUnten(), m,
								blockierteBereiche, blockierteFensterBereiche)) {
							m.setX(x - (m.getKeepOutRechts() + m.getLaenge()));
							m.setY(y + m.getKeepOutUnten());
							blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
									m.getY() - m.getKeepOutUnten(),
									m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
									m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
							System.out.println(m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX()
									+ ", y=" + m.getY());
							return true;
						}
					}
				}
			} else if (istObereEcke) {
				while (m.getAusrichtung() != 0)
					m.drehen();
				if (m.getKeepOutLinks() == 0 && m.getKeepOutOben() == 0) {
					while (m.getAusrichtung() != 270)
						m.drehen();
					if (!kollidiert(raum, x - (m.getKeepOutRechts() + m.getLaenge()),
							y - (m.getKeepOutOben() + m.getBreite()), m, blockierteBereiche,
							blockierteFensterBereiche)) {
						m.setX(x - (m.getKeepOutRechts() + m.getLaenge()));
						m.setY(y - (m.getKeepOutOben() + m.getBreite()));
						blockierteBereiche.add(
								new BlockierterBereich(m.getX() - m.getKeepOutLinks(), m.getY() - m.getKeepOutUnten(),
										m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
										m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
						System.out.println(
								m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX() + ", y=" + m.getY());
						return true;
					}
					while (m.getAusrichtung() != 0)
						m.drehen();
					if (m.getKeepOutOben() == 0 && m.getKeepOutRechts() == 0) {

						while (m.getAusrichtung() != 0)
							m.drehen();
						if (!kollidiert(raum, x - (m.getKeepOutRechts() + m.getLaenge()),
								y - (m.getKeepOutOben() + m.getBreite()), m, blockierteBereiche,
								blockierteFensterBereiche)) {
							m.setX(x - (m.getKeepOutRechts() + m.getLaenge()));
							m.setY(y - (m.getKeepOutOben() + m.getBreite()));
							blockierteBereiche.add(new BlockierterBereich(m.getX() - m.getKeepOutLinks(),
									m.getY() - m.getKeepOutUnten(),
									m.getLaenge() + m.getKeepOutLinks() + m.getKeepOutRechts(),
									m.getBreite() + m.getKeepOutOben() + m.getKeepOutUnten()));
							System.out.println(m.getClass().getSimpleName() + " in Ecke platziert: x=" + m.getX()
									+ ", y=" + m.getY());
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// Methode zum Überprüfen, ob ein Möbelstück mit einem belegten platz kollidiert
	private static boolean kollidiert(RaumModell raum, double x, double y, Moebelstueck m,
			List<BlockierterBereich> blockierteBereiche, List<BlockierterBereich> blockierteFensterBereiche) {
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

	private static void blockiereTuerbereiche(RaumModell raum, List<BlockierterBereich> blockierteBereiche) {
		for (TuerModell tuer : raum.getTueren()) {
			double x = tuer.getX();
			double y = tuer.getY();
			double breite = tuer.getBreite();
			double tiefe = 1.0; // 1 Meter Tiefe vor der Tür

			if (x == raum.getX()) { // Tür an der linken Wand
				blockierteBereiche.add(new BlockierterBereich(x + raum.getWANDBREITE(), y, tiefe, breite));
			} else if (x == raum.getX() + raum.getLaenge()) { // Tür an der rechten Wand
				blockierteBereiche.add(new BlockierterBereich(x - tiefe - raum.getWANDBREITE(), y, tiefe, breite));
			} else if (y == raum.getY() + raum.getBreite()) { // Tür an der oberen Wand
				blockierteBereiche.add(new BlockierterBereich(x, y - tiefe - raum.getWANDBREITE(), breite, tiefe));
			} else if (y == raum.getY()) { // Tür an der unteren Wand
				blockierteBereiche.add(new BlockierterBereich(x, y + raum.getWANDBREITE(), breite, tiefe));
			}
		}
	}

	private static void blockiereFensterbereiche(RaumModell raum, List<BlockierterBereich> blockierteFensterBereiche) {
		for (Fenster fenster : raum.getFenster()) {
			double x = fenster.getX();
			double y = fenster.getY();
			double breite = fenster.getBreite();
			double tiefe = 1.5; // 1.5 Meter Tiefe vor dem Fenster

			if (x == raum.getX()) { // Fenster an der linken Wand
				blockierteFensterBereiche.add(new BlockierterBereich(x + raum.getWANDBREITE(), y, tiefe, breite));
			} else if (x == raum.getX() + raum.getLaenge()) { // Fenster an der rechten Wand
				blockierteFensterBereiche
						.add(new BlockierterBereich(x - tiefe - raum.getWANDBREITE(), y, tiefe, breite));
			} else if (y == raum.getY() + raum.getBreite()) { // Fenster an der oberen Wand
				blockierteFensterBereiche
						.add(new BlockierterBereich(x, y - tiefe - raum.getWANDBREITE(), breite, tiefe));
			} else if (y == raum.getY()) { // Fenster an der unteren Wand
				blockierteFensterBereiche.add(new BlockierterBereich(x, y + raum.getWANDBREITE(), breite, tiefe));
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

