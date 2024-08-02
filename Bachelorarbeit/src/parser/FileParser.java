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
import model.moebel.Schrank;
import model.moebel.Sofa;
import model.raeume.Wohnzimmer;

public class FileParser {

	public static Haus parseHaus(String path, Haus haus) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		Raum aktuellerRaum = null;
		double aktuellerRaumGroesse = 0;
		double aktuellerRaumBreite = 0;
		double aktuellerRaumLaenge = 0;
		List<Moebelstueck> aktuelleMoebel = new ArrayList<Moebelstueck>();
		List<TmpTuere> aktuelleTueren = new ArrayList<TmpTuere>();

		while ((line = reader.readLine()) != null) {
			line = line.trim();
//			if (line.endsWith(":")) {
//				if (aktuellerRaum != null) {
//					haus.addRaum(aktuellerRaum);
//				}
			String raumName = line.substring(0, line.length() - 1);
			switch (raumName) {
			case "Wohnzimmer":
				do {
					line = reader.readLine().trim();
					switch (line.split(":")[0]) {
					case "Größe":
						aktuellerRaumGroesse = Integer.parseInt(line.split(":")[1].trim());
						break;
					case "Maße":
						aktuellerRaumLaenge = Integer.parseInt(line.split("x")[0].split(":")[1].trim());
						aktuellerRaumBreite = Integer.parseInt(line.split("x")[1].trim());
						break;
					case "Möbel":
						do {
							line = reader.readLine().trim();
							double laenge = 0;
							double breite = 0;
							double keepoutLinks = 0;
							double keepoutRechts = 0;
							double keepoutOben = 0;
							double keepoutUnten = 0;

							switch (line.split(":")[0]) {
							case "Sofa":
								laenge = Double.parseDouble(line.split(":")[1].split("x")[0].trim());
								breite = Double.parseDouble(line.split(":")[1].split("x")[1].trim());

								line = reader.readLine().trim();
								if (line.startsWith("Keepout")) {
									if (line.split(":")[1].contains(",") == false) {
										keepoutLinks = Double.parseDouble(line.split(":")[1].trim());
										keepoutRechts = Double.parseDouble(line.split(":")[1].trim());
										keepoutUnten = Double.parseDouble(line.split(":")[1].trim());
										keepoutOben = Double.parseDouble(line.split(":")[1].trim());
									} else if (line.split(":")[1].split(",").length == 3) {
										keepoutLinks = Double.parseDouble(line.split(":")[1].split(",")[0].trim());
										keepoutUnten = Double.parseDouble(line.split(":")[1].split(",")[1].trim());
										keepoutRechts = Double.parseDouble(line.split(":")[1].split(",")[2].trim());
										keepoutOben = 0;
									} else if (line.split(":")[1].split(",").length == 4) {
										keepoutLinks = Double.parseDouble(line.split(":")[1].split(",")[0].trim());
										keepoutUnten = Double.parseDouble(line.split(":")[1].split(",")[1].trim());
										keepoutRechts = Double.parseDouble(line.split(":")[1].split(",")[2].trim());
										keepoutOben = Double.parseDouble(line.split(":")[1].split(",")[3].trim());
									}
								}
								aktuelleMoebel.add(new Sofa(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben,
										keepoutUnten));
								break;
							case "Schrank":
								laenge = Double.parseDouble(line.split(":")[1].split("x")[0].trim());
								breite = Double.parseDouble(line.split(":")[1].split("x")[1].trim());
								keepoutLinks = 0;
								keepoutRechts = 0;
								keepoutOben = 0;
								keepoutUnten = 0;

								line = reader.readLine().trim();
								if (line.startsWith("Keepout")) {
									if (line.split(":")[1].contains(",") == false) {
										keepoutLinks = Double.parseDouble(line.split(":")[1].trim());
										keepoutRechts = Double.parseDouble(line.split(":")[1].trim());
										keepoutUnten = Double.parseDouble(line.split(":")[1].trim());
										keepoutOben = Double.parseDouble(line.split(":")[1].trim());
									} else if (line.split(":")[1].split(",").length == 3) {
										keepoutLinks = Double.parseDouble(line.split(":")[1].split(",")[0].trim());
										keepoutUnten = Double.parseDouble(line.split(":")[1].split(",")[1].trim());
										keepoutRechts = Double.parseDouble(line.split(":")[1].split(",")[2].trim());
										keepoutOben = 0;
									} else if (line.split(":")[1].split(",").length == 4) {
										keepoutLinks = Double.parseDouble(line.split(":")[1].split(",")[0].trim());
										keepoutUnten = Double.parseDouble(line.split(":")[1].split(",")[1].trim());
										keepoutRechts = Double.parseDouble(line.split(":")[1].split(",")[2].trim());
										keepoutOben = Double.parseDouble(line.split(":")[1].split(",")[3].trim());
									}
								}
								aktuelleMoebel.add(new Schrank(laenge, breite, keepoutLinks, keepoutRechts, keepoutOben,
										keepoutUnten));
							}

						} while (!line.endsWith(":") && line != "");
						break;

					case "Türen":
						do {
							line = reader.readLine().trim();
							aktuelleTueren.add(new TmpTuere(raumName, line, 1)); // TODO Türbreite
						} while (!line.endsWith(":") && line != "");
					}

				} while (line != "");

			}
			aktuellerRaum = new Wohnzimmer(raumName, aktuellerRaumLaenge, aktuellerRaumBreite, aktuelleMoebel, null);
			break;
		}
		for (TmpTuere tuere:aktuelleTueren) {
			haus.getRaumByName(tuere.vonRaum).addTuer(new Tuer(haus.getRaumByName(tuere.vonRaum), haus.getRaumByName(tuere.inRaum), tuere.breite));
		}
		
		return haus;
	}

//		for i in aktuelleTueren {
//            haus.addTuer(new Tuer(i.vonRaum, i.inRaum, i.breite));
//        }

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
