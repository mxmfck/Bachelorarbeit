package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Haus;
import model.Raum;
import model.RaumModell;

public class SpiralBasedGrundriss {

	List<RaumModell> raeume;

	public SpiralBasedGrundriss(Haus haus) {
		berechnePositionen(haus);
	}

	public void berechnePositionen(Haus haus) {
		List<Raum> tmpRaeume = new ArrayList<>(haus.getRaeume());
		findeUndEntferneFlur(tmpRaeume);
		tmpRaeume.sort((raum1, raum2) -> Double.compare(raum1.getGroesse(), raum2.getGroesse())); // sort by size |
																									// smallest first |
																									// Double.compare
																									// returns -1 if
																									// raum1 < raum2, 0
																									// if raum1 ==
																									// raum2, 1 if raum1
																									// > raum2

//		double breite = 0;
//		double laenge = 0;
		double xMin = 0;
		double yMin = 0;
		double xMax = 0;
		double yMax = 0;

		raeume = new ArrayList<>();
		Raum aktuellerRaum = tmpRaeume.get(0);
		double aktuellerRaumLaenge = aktuellerRaum.getLaenge();
		double aktuellerRaumBreite = aktuellerRaum.getBreite();
		if (aktuellerRaumLaenge < aktuellerRaumBreite) {
			double temp = aktuellerRaumLaenge;
			aktuellerRaumLaenge = aktuellerRaumBreite;
			aktuellerRaumBreite = temp;
		}

		RaumModell raum = new RaumModell(aktuellerRaum.getName(), aktuellerRaumLaenge, aktuellerRaumBreite,
				aktuellerRaum.getMoebel(), aktuellerRaum.getTueren(), xMin, yMin);
		raeume.add(raum);
		xMax = aktuellerRaumLaenge;
		yMax = aktuellerRaumBreite;

		for (int i = 2; i <= tmpRaeume.size(); i++) {
			aktuellerRaum = tmpRaeume.get(i - 1);
			aktuellerRaumBreite = aktuellerRaum.getBreite();
			aktuellerRaumLaenge = aktuellerRaum.getLaenge();

			switch (i % 4) {
			case 0: // Nach unten (links) platzieren

				if (aktuellerRaumLaenge < aktuellerRaumBreite) {
					double temp = aktuellerRaumLaenge;
					aktuellerRaumLaenge = aktuellerRaumBreite;
					aktuellerRaumBreite = temp;
				}

				raum = new RaumModell(aktuellerRaum.getName(), aktuellerRaumLaenge, aktuellerRaumBreite,
						aktuellerRaum.getMoebel(), aktuellerRaum.getTueren(), xMin, yMin - aktuellerRaumBreite);
				raeume.add(raum);
				yMin -= aktuellerRaumBreite;

				if (aktuellerRaumLaenge == xMax+Math.abs(xMin))
					break;
				else if (aktuellerRaumLaenge < xMax+Math.abs(xMin)) {
					erzeugeFlur(xMax+Math.abs(xMin) - aktuellerRaumLaenge, aktuellerRaumBreite, xMin + aktuellerRaumLaenge, yMin);
					break;
				} else {
					erzeugeFlur(aktuellerRaumLaenge - (xMax+Math.abs(xMin)), yMax + Math.abs(yMin) - aktuellerRaumBreite, xMax,
							yMin + aktuellerRaumBreite);
					xMax = aktuellerRaumLaenge;
					break;
				}

			case 1: // Nach links (oben) platzieren

				if (aktuellerRaumLaenge > aktuellerRaumBreite) {
					double temp = aktuellerRaumLaenge;
					aktuellerRaumLaenge = aktuellerRaumBreite;
					aktuellerRaumBreite = temp;
				}

				raum = new RaumModell(aktuellerRaum.getName(), aktuellerRaumLaenge, aktuellerRaumBreite,
						aktuellerRaum.getMoebel(), aktuellerRaum.getTueren(), xMin - aktuellerRaumLaenge, yMax - aktuellerRaumBreite);
				raeume.add(raum);
				xMin -= aktuellerRaumLaenge;

				if (aktuellerRaumBreite == yMax + Math.abs(yMin))
					break;
				else if (aktuellerRaumBreite < yMax + Math.abs(yMin)) {
					erzeugeFlur(aktuellerRaumLaenge, yMax + Math.abs(yMin) - aktuellerRaumBreite,
							xMin, yMin);
					break;
				} else {
					erzeugeFlur(xMax + Math.abs(xMin) - aktuellerRaumLaenge, aktuellerRaumBreite - (yMax+Math.abs(yMin)),
							xMin +aktuellerRaumLaenge, yMax-aktuellerRaumBreite);
					yMin = yMax-aktuellerRaumBreite;
					break;
				}

			case 2: // Nach oben (links) platzieren

				if (aktuellerRaumLaenge < aktuellerRaumBreite) {
					double temp = aktuellerRaumLaenge;
					aktuellerRaumLaenge = aktuellerRaumBreite;
					aktuellerRaumBreite = temp;
				}

				raum = new RaumModell(aktuellerRaum.getName(), aktuellerRaumLaenge, aktuellerRaumBreite,
						aktuellerRaum.getMoebel(), aktuellerRaum.getTueren(), xMin, yMax);
				raeume.add(raum);
				yMax += aktuellerRaumBreite;

				if (aktuellerRaumLaenge == xMax+Math.abs(xMin))
					break;
				else if (aktuellerRaumLaenge < xMax+Math.abs(xMin)) {
					erzeugeFlur(xMax+Math.abs(xMin) - aktuellerRaumLaenge, aktuellerRaumBreite, xMin + aktuellerRaumLaenge,
							yMax - aktuellerRaumBreite);
					break;
				} else {
					erzeugeFlur(aktuellerRaumLaenge - (xMax + Math.abs(xMin)),
							(yMax + Math.abs(yMin)) - aktuellerRaumBreite, xMax, yMin);
					xMax = aktuellerRaumLaenge-Math.abs(xMin);
					break;
				}

			case 3: // Nach rechts (oben) platzieren

				if (aktuellerRaumLaenge > aktuellerRaumBreite) {
					double temp = aktuellerRaumLaenge;
					aktuellerRaumLaenge = aktuellerRaumBreite;
					aktuellerRaumBreite = temp;
				}

				raum = new RaumModell(aktuellerRaum.getName(), aktuellerRaumLaenge, aktuellerRaumBreite,
						aktuellerRaum.getMoebel(), aktuellerRaum.getTueren(), xMax, yMax - aktuellerRaumBreite);
				raeume.add(raum);
				xMax += aktuellerRaumLaenge;

				if (aktuellerRaumBreite == yMax + Math.abs(yMin))
					break;
				else if (aktuellerRaumBreite < yMax + Math.abs(yMin)) {
					erzeugeFlur(aktuellerRaumLaenge, yMax + Math.abs(yMin) - aktuellerRaumBreite,
							xMax - aktuellerRaumLaenge, yMin);
					break;
				} else {
					erzeugeFlur((xMax + Math.abs(xMin))- aktuellerRaumLaenge,
							aktuellerRaumBreite-(yMax + Math.abs(yMin)), xMin, yMax-aktuellerRaumBreite);
					yMin = yMax - aktuellerRaumBreite;
					break;

				}
			}
		}
	}

	private void erzeugeFlur(double flurLaenge, double flurBreite, double x, double y) {
		RaumModell flur = new RaumModell("Flur", flurLaenge, flurBreite, null, null, x, y);
		raeume.add(flur);
	}

	private void findeUndEntferneFlur(List<Raum> tmpRaeume) {
		for (int i = 0; i < tmpRaeume.size(); i++) {
			if (tmpRaeume.get(i).getName().equals("Flur")) {
				tmpRaeume.remove(i);
				return;
			}
		}
	}
}
