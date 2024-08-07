package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Haus;
import model.Moebelstueck;
import model.Raum;
import model.Tuer;
import model.moebel.Arbeitsplatte;
import model.moebel.Badewanne;
import model.moebel.Bett;
import model.moebel.Dusche;
import model.moebel.Herd;
import model.moebel.Kommode;
import model.moebel.Kuehlschrank;
import model.moebel.Nachttisch;
import model.moebel.Ofen;
import model.moebel.Schrank;
import model.moebel.Schreibtisch;
import model.moebel.Sessel;
import model.moebel.Sofa;
import model.moebel.Spuele;
import model.moebel.Stuhl;
import model.moebel.Tisch;
import model.moebel.Toilette;
import model.moebel.Waschbecken;
import model.raeume.Badezimmer;
import model.raeume.Flur;
import model.raeume.Gaestezimmer;
import model.raeume.Kinderzimmer;
import model.raeume.Kueche;
import model.raeume.Schlafzimmer;
import model.raeume.WC;
import model.raeume.Wohnzimmer;

public class FileParser {

	public static Haus parseHaus(String path, Haus haus) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) { //try with resource Statement für das automatische Schließen des Readers
			String line; //Speichert die aktuelle Zeile des Readers
			Raum aktuellerRaum = null; //Speichert den aktuellen Raum
			List<TmpTuere> aktuelleTueren = new ArrayList<>(); //Speichert die Türen des aktuellen Raums (TmpTüren, da die Raume noch nicht initialisiert sein können)

			while ((line = reader.readLine()) != null) {
				line = line.trim(); //Entfernt Leerzeichen am Anfang und Ende der Zeile
				if (line.endsWith(":")) { //Wenn die Zeile mit einem Doppelpunkt endet, wird ein neuer Raum erstellt
//					if (aktuellerRaum != null) {
//						haus.addRaum(aktuellerRaum);
//					}
					String raumName = line.substring(0, line.length() - 1); //Speichert den Namen des Raumes
					aktuellerRaum = createRaum(reader, raumName, aktuelleTueren); //Erstellt den Raum
					if (aktuellerRaum != null) {
						haus.addRaum(aktuellerRaum); //Fügt den Raum dem Haus hinzu
					}
				}
			}
			addTuerenToHaus(haus, aktuelleTueren); //Transformiert TmpTuere in Tuer und fügt sie dem Haus hin
		}
		return haus;
	}

	//Erstellt einen Raum anhand der Anforderungen in der Datei | Übergabe des Readers, des Raumnamens und der Türen des Raums
	private static Raum createRaum(BufferedReader reader, String raumName, List<TmpTuere> aktuelleTueren)
			throws IOException {
		Raum raum = null;
		double groesse = 0;
		double laenge = 0;
		double breite = 0;
		List<Moebelstueck> moebel = new ArrayList<>();

		String line;
		while ((line = reader.readLine()) != null) { //removed && line !="" //ReadLine solange bis das Ende der Datei erreicht ist oder eine freie Zeile erreicht wird
			line = line.trim();
			if (line.isEmpty()) { // removed || line.endsWith(":")
//				reader.reset();
				break;  					    // removed Works?
			}
			switch (line.split(":")[0]) { //Switch-Case für die verschiedenen Anforderungen
			case "Größe":
				groesse = Double.parseDouble(line.split(":")[1].trim().replace(',', '.'));
				break;
			case "Maße":
				laenge = Double.parseDouble(line.split("x")[0].split(":")[1].trim().replace(',', '.'));
				breite = Double.parseDouble(line.split("x")[1].trim().replace(',', '.'));
				break;
			case "Möbel":
				parseMoebel(reader, moebel); //Erstellt die Möbelstücke des Raumes
				break;
			case "Türen":
				parseTueren(reader, raumName, aktuelleTueren); //Erstellen der Türen des Raumes
				break;
			}
		}
		//Erstellen des Raumes anhand des Raumnamens | startsWith, da es möglicherweise mehrere Räume des gleichen Typs gibt (z.B. Kinderzimmer1, Kinderzimmer2, ...)
		if (raumName.startsWith("Wohnzimmer")) {
			raum = new Wohnzimmer(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Badezimmer")) {
			raum = new Badezimmer(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Flur")) {
			raum = new Flur(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Gaestezimmer")) {
			raum = new Gaestezimmer(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Kueche")) {
			raum = new Kueche(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Schlafzimmer")) {
			raum = new Schlafzimmer(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("WC")) {
			raum = new WC(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Kinderzimmer")) {
			raum = new Kinderzimmer(raumName, laenge, breite, moebel, null);
		} else if (raumName.startsWith("Küche")) {
			raum = new Kueche(raumName, laenge, breite, moebel, null);
		} else {
			System.err.println("Unbekannter Raum: " + raumName);
		}
		return raum;
	}

	//Erstellt die Möbelstücke des Raumes | Übergabe des Readers und der Liste der Möbelstücke
	private static void parseMoebel(BufferedReader reader, List<Moebelstueck> moebel) throws IOException {
		String line;
		while ((line = reader.readLine()) != null && !line.endsWith(":") && !line.isEmpty()) { //
			line = line.trim();
			if (line.isEmpty()) { // removed || line.endsWith(":")
//				reader.reset();
				break;
			}
			double laenge = Double.parseDouble(line.split(":")[1].split("x")[0].trim().replace(',', '.'));
			double breite = Double.parseDouble(line.split(":")[1].split("x")[1].trim().replace(',', '.'));
			double keepoutLinks = 0, keepoutRechts = 0, keepoutOben = 0, keepoutUnten = 0;
			String keepoutLine;

			reader.mark(1000);
			keepoutLine = reader.readLine().trim();
			if (keepoutLine.startsWith("Keepout")) { //Wenn eine Keepout-Anforderung existiert, werden die Werte gespeichert | Alternativ default Werte (TODO)
				String[] keepoutValues = keepoutLine.split(":")[1].split(";");

				if (keepoutValues.length == 4) {
					keepoutLinks = Double.parseDouble(keepoutValues[0].trim().replace(',', '.'));
					keepoutUnten = Double.parseDouble(keepoutValues[1].trim().replace(',', '.'));
					keepoutRechts = Double.parseDouble(keepoutValues[2].trim().replace(',', '.'));
					keepoutOben = Double.parseDouble(keepoutValues[3].trim().replace(',', '.'));
				} else if (keepoutValues.length == 3) {
					keepoutLinks = Double.parseDouble(keepoutValues[0].trim().replace(',', '.'));
					keepoutRechts = Double.parseDouble(keepoutValues[2].trim().replace(',', '.'));
					keepoutOben = 0;
					keepoutUnten = Double.parseDouble(keepoutValues[1].trim().replace(',', '.'));
				} else if (keepoutValues.length == 1) {
					keepoutLinks = Double.parseDouble(keepoutValues[0].trim().replace(',', '.'));
					keepoutRechts = Double.parseDouble(keepoutValues[0].trim().replace(',', '.'));
					keepoutOben = Double.parseDouble(keepoutValues[0].trim().replace(',', '.'));
					keepoutUnten = Double.parseDouble(keepoutValues[0].trim().replace(',', '.'));
				}
			} else {
				reader.reset();
			}

			//Erstellen des Möbelstücks anhand des Möbelnamens | startsWith, da es möglicherweise mehrere Möbelstücke des gleichen Typs gibt (z.B. Bett1, Bett)
			if (line.startsWith("Sofa")) {
				moebel.add(new Sofa(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Schrank")) {
				moebel.add(new Schrank(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Badewanne")) {
				moebel.add(new Badewanne(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Bett")) {
				moebel.add(new Bett(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Dusche")) {
				moebel.add(new Dusche(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Herd")) {
				moebel.add(new Herd(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Kommode")) {
				moebel.add(new Kommode(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Nachttisch")) {
				moebel.add(new Nachttisch(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Sessel")) {
				moebel.add(new Sessel(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Spüle")) {
				moebel.add(new Spuele(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Kühlschrank")) {
				moebel.add(new Kuehlschrank(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Ofen")) {
				moebel.add(new Ofen(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Schreibtisch")) {
				moebel.add(new Schreibtisch(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Arbeitsplatte")) {
				moebel.add(new Arbeitsplatte(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Stuhl")) {
				moebel.add(new Stuhl(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Tisch")) {
				int anzahlStuehle = 0;
				boolean stuehleOben = false;
				boolean stuehleUnten = false;
				boolean stuehleLinks = false;
				boolean stuehleRechts = false;
				List<Stuhl> stuehle = new ArrayList<>();

//				reader.mark(1000);
				String additionalInfo = reader.readLine().trim();
//				line = reader.readLine().trim();

				do {
					switch (additionalInfo.split(":")[0].trim()) {
					case "Stühle":
						anzahlStuehle = Integer.parseInt(additionalInfo.split(":")[1].trim());
						for (int i = 0; i < anzahlStuehle; i++) {
							stuehle.add(new Stuhl(0.6, 0.6, 0.2, 0.5, 0.2, 0.1)); // TODO Werte
						}
						break;

					case "Position":
						if (additionalInfo.split(":")[1].contains("o")) {
							stuehleOben = true;
						}
						if (additionalInfo.split(":")[1].contains("u")) {
							stuehleUnten = true;
						}
						if (additionalInfo.split(":")[1].contains("l")) {
							stuehleLinks = true;
						}
						if (additionalInfo.split(":")[1].contains("r")) {
							stuehleRechts = true;
						}
						break;
					default:
						reader.reset();
						break;
					}
					reader.mark(1000);
				} while ((additionalInfo = reader.readLine()) != null && !additionalInfo.endsWith(":")
						&& additionalInfo != "");
				reader.reset();

				moebel.add(new Tisch(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten,
						anzahlStuehle, stuehle, stuehleOben, stuehleUnten, stuehleLinks, stuehleRechts));
			} else if (line.startsWith("Toilette")) {
				moebel.add(new Toilette(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else if (line.startsWith("Waschbecken")) {
				moebel.add(new Waschbecken(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben, keepoutUnten));
			} else {
				System.err.println("Unbekanntes Möbelstück: " + line);
			}
		}
		reader.reset();
	}

	//Erstellt die Türen des Raumes | Übergabe des Readers, des Raumnamens und der Liste der Türen
	private static void parseTueren(BufferedReader reader, String raumName, List<TmpTuere> aktuelleTueren)
			throws IOException {
		String line = reader.readLine();
		while (line != null /*&& line != "" && !line.endsWith(":")*/) {
			line = line.trim();
			if (line.isEmpty() || line.endsWith(":")) {
				reader.reset();
				break;
			}
			aktuelleTueren.add(new TmpTuere(raumName, line, 1)); // TODO Türbreite
			reader.mark(1000);
			line = reader.readLine();
		}
	}

	//Fügt die Türen dem Haus hinzu | Übergabe des Hauses und der Liste der Türen
	private static void addTuerenToHaus(Haus haus, List<TmpTuere> tueren) {
		for (TmpTuere tuere : tueren) {
			Raum raum1 = haus.getRaumByName(tuere.vonRaum);
			Raum raum2 = haus.getRaumByName(tuere.inRaum);
			if (raum1 != null && raum2 != null) {
				Tuer tuer = new Tuer(raum1, raum2, tuere.breite);
				haus.addTuer(tuer);
				raum1.addTuer(tuer);
				raum2.addTuer(tuer);
			}
		}
	}

	//Klasse für temporäre Türen
	private static class TmpTuere {
		private String vonRaum;
		private String inRaum;
		private double breite;

		public TmpTuere(String vonRaum, String inRaum, double breite) {
			this.vonRaum = vonRaum;
			this.inRaum = inRaum;
			this.breite = breite;
		}
	}
}
