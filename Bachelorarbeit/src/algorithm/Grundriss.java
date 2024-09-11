package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Haus;
import model.Raum;
import model.Tuer;

public class Grundriss {
	// Klasse, die einen kompletten Grundriss speichert

	private List<RaumModell> raeume; // Liste aller Räume im Grundriss
	private List<RaumModell> raeumeLinks; // Liste aller Räume links des Flurs
	private List<RaumModell> raeumeRechts; // Liste aller Räume rechts des Flurs

	private double xLinks = 0; // x-Koordinate an dem der Raum auf den Flur trifft
	private double yLinks = 0; // Aktuelle y-Koordinate der Breite der Räume links des Flurs
	private double xRechts = 0; // x-Koordinate an dem der Raum auf den Flur trifft
	private double yRechts = 0; // Aktuelle y-Koordinate der Breite der Räume rechts des Flurs
	private double maxRechts = 0; // Maximale x-Koordinate der Räume rechts des Flurs
	private double maxLinks = 0; // Maximale x-Koordinate der Räume links des Flurs
	private boolean linksPlaziert; // Gibt an, ob der letzte Raum links oder rechts platziert wurde
	private final double WANDBREITE = 0.1; 

	public Grundriss(Haus haus) {
		berechneGrundriss(haus);

	}

	public List<RaumModell> getRaeume() {
		return raeume;
	}

	public void addRaum(RaumModell raum) {
		raeume.add(raum);
	}

	public double getMaxLinks() {
		return maxLinks;
	}

	public double getMaxRechts() {
		return maxRechts;
	}

	public double getYLinks() {
		return yLinks;
	}

	public double getYRechts() {
		return yRechts;
	}

	// Methode zum Berechnen des Grundrisses
	private void berechneGrundriss(Haus haus) {
		List<Raum> tmpRaeume;
		tmpRaeume = new ArrayList<>(haus.getRaeume());
		raeume = new ArrayList<>();
		findeUndEntferneFlur(tmpRaeume);
		xRechts = raeume.get(0).getLaenge();
		xLinks = raeume.get(0).getX();
		maxRechts = raeume.get(0).getLaenge();
		maxLinks = raeume.get(0).getX();

		Raum ersterRaum = findeRaumMitWenigenTueren(tmpRaeume);

		linksPlaziert = true;
		platziereRaumLinks(ersterRaum, tmpRaeume);

		ersterRaum = findeRaumMitWenigenTueren(tmpRaeume);
		platziereRaumRechts(ersterRaum, tmpRaeume);

		while (!tmpRaeume.isEmpty()) {
			Raum raum = tmpRaeume.remove((int) (Math.random() * tmpRaeume.size()));
			platziereRaum(raum, tmpRaeume);
		}

		verlaengereFlur();
		erzeugeTueren();
		erzeugeFenster();

		raeume.addAll(raeumeLinks);
		raeume.addAll(raeumeRechts);
	}

	// Methode zum Finden und Entfernen des Flurs aus der Liste
	private void findeUndEntferneFlur(List<Raum> tmpRaeume) {
		for (int i = 0; i < tmpRaeume.size(); i++) {
			if (tmpRaeume.get(i).getName().equals("Flur")) {
				Raum flur = tmpRaeume.remove(i);
				List<TuerModell> tueren = new ArrayList<>();
				for (Tuer tuer : flur.getTueren()) {
					tueren.add(new TuerModell(tuer.getVonRaum(), tuer.getInRaum(), tuer.getBreite()));
				}

				RaumModell flurModell = new RaumModell(flur.getName(), flur.getLaenge(), flur.getBreite(),
						flur.getMoebel(), tueren, 0 + WANDBREITE, 0 + WANDBREITE);
				raeume.add(flurModell);
				return;
			}
		}
	}

	// Methode zum Platzieren eines Raumes im Grundriss
	private void platziereRaum(Raum raum, List<Raum> tmpRaeume) {
		if (yLinks <= yRechts) {
			platziereRaumLinks(raum, tmpRaeume);
		} else {
			platziereRaumRechts(raum, tmpRaeume);
		}
	}

	// Methode zum Platzieren eines Raumes links des Flurs
	private void platziereRaumLinks(Raum raum, List<Raum> tmpRaeume) {

		platziereRaumSeite(raum, tmpRaeume, true);

	}

	// Methode zum Platzieren eines Raumes rechts des Flurs
	private void platziereRaumRechts(Raum raum, List<Raum> tmpRaeume) {

		platziereRaumSeite(raum, tmpRaeume, false);
	}

	// Methode die den Raum letztendlich platziert
	private void platziereRaumSeite(Raum raum, List<Raum> tmpRaeume, boolean links) {
		List<RaumModell> raeume = links ? raeumeLinks : raeumeRechts;
		double x = links ? xLinks : xRechts;
		double y = links ? yLinks : yRechts;
		double max = links ? maxLinks : maxRechts;

		if (raeume == null) {
			raeume = new ArrayList<>();
			double random = Math.random();
			if (random < 0.5) {
				List<TuerModell> tueren = new ArrayList<>();
				for (Tuer tuer : raum.getTueren()) {
					tueren.add(new TuerModell(tuer.getVonRaum(), tuer.getInRaum(), tuer.getBreite()));
				}
				RaumModell raumModell = new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(),
						raum.getMoebel(), tueren, links ? x - raum.getLaenge() - WANDBREITE : x + WANDBREITE,
						y + WANDBREITE);
				raeume.add(raumModell);
				y += Math.round(raumModell.getBreite()*100.0)/100.0;
				max = Math.round((links ? raumModell.getLaenge() : x + raumModell.getLaenge())*100.0)/100.0;
			} else {
				List<TuerModell> tueren = new ArrayList<>();
				for (Tuer tuer : raum.getTueren()) {
					tueren.add(new TuerModell(tuer.getVonRaum(), tuer.getInRaum(), tuer.getBreite()));
				}
				RaumModell raumModell = new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(),
						raum.getMoebel(), tueren, links ? x - raum.getBreite() - WANDBREITE : x + WANDBREITE,
						y + WANDBREITE);
				raeume.add(raumModell);
				y += Math.round(raumModell.getBreite()*100.0)/100.0;
				max = Math.round((links ? raumModell.getLaenge() : x + raumModell.getLaenge())*100.0)/100.0;
			}
		} else {
			if (Math.abs(raum.getLaenge() - raeume.get(raeume.size() - 1).getLaenge()) < Math
					.abs(raum.getBreite() - raeume.get(raeume.size() - 1).getLaenge())) {
				List<TuerModell> tueren = new ArrayList<>();
				for (Tuer tuer : raum.getTueren()) {
					tueren.add(new TuerModell(tuer.getVonRaum(), tuer.getInRaum(), tuer.getBreite()));
				}
				RaumModell raumModell = new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(),
						raum.getMoebel(), tueren, links ? x - raum.getLaenge() - WANDBREITE : x + WANDBREITE,
						y + WANDBREITE);
				raeume.add(raumModell);
				y += Math.round(raumModell.getBreite()*100.0)/100.0;
				if (links) {
					if (max < raumModell.getLaenge()) {
						max = Math.round(raumModell.getLaenge()*100.0)/100.0;
					}
				} else {
					if (max < x + raumModell.getLaenge()) {
						max = Math.round((x + raumModell.getLaenge())*100.0)/100.0;
					}
				}
			} else {
				List<TuerModell> tueren = new ArrayList<>();
				for (Tuer tuer : raum.getTueren()) {
					tueren.add(new TuerModell(tuer.getVonRaum(), tuer.getInRaum(), tuer.getBreite()));
				}
				RaumModell raumModell = new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(),
						raum.getMoebel(), tueren, links ? x - raum.getBreite() - WANDBREITE : x + WANDBREITE,
						y + WANDBREITE);
				raeume.add(raumModell);
				y += Math.round(raumModell.getBreite()*100.0)/100.0;
				if (links) {
					if (max < raumModell.getLaenge()) {
						max = Math.round(raumModell.getLaenge()*100.0)/100.0;
					}
				} else {
					if (max < x + raumModell.getLaenge()) {
						max = Math.round((x + raumModell.getLaenge())*100.0)/100.0;
					}
				}
			}
		}

		if (links) {
			raeumeLinks = raeume;
			yLinks = y;
			maxLinks = max;
			linksPlaziert = true;
		} else {
			raeumeRechts = raeume;
			yRechts = y;
			maxRechts = max;
			linksPlaziert = false;
		}

		checkTueren(raum, tmpRaeume);
	}

	// Methode zum Überprüfen der Türen eines Raumes
	private void checkTueren(Raum letzterRaum, List<Raum> tmpRaeume) {
		for (Tuer tuer : letzterRaum.getTueren()) {
			if (tuer.getInRaum().getName().equals(letzterRaum.getName())
					&& !tuer.getVonRaum().getName().equals("Flur")) {
				Raum raum = findeRaumByName(tuer.getVonRaum().getName(), tmpRaeume);
				if (raum != null) {
					platziereAngrenzendenRaum(raum, tmpRaeume);

				}
			} else if (tuer.getVonRaum().getName().equals(letzterRaum.getName())
					&& !tuer.getInRaum().getName().equals("Flur")) {
				Raum raum = findeRaumByName(tuer.getInRaum().getName(), tmpRaeume);
				if (raum != null) {
					platziereAngrenzendenRaum(raum, tmpRaeume);
				}
			}
		}
	}

	// Methode zum Platzieren eines angrenzenden Raums auf der gleichen Seite wie
	// der vorherige Raum
	private void platziereAngrenzendenRaum(Raum raum, List<Raum> tmpRaeume) {
		if (linksPlaziert) {
			platziereRaumLinks(raum, tmpRaeume);
		} else {
			platziereRaumRechts(raum, tmpRaeume);
		}
	}

	// Methode zum Finden eines Raumes anhand des Namens in tmpRaeume
	private Raum findeRaumByName(String name, List<Raum> tmpRaeume) {
		for (Raum raum : tmpRaeume) {
			if (raum.getName().equals(name)) {
				return tmpRaeume.remove(tmpRaeume.indexOf(raum));
			}
		}
		return null;
	}

	// Methode zum Verlängern des Flurs
	private void verlaengereFlur() {
		if (yLinks > yRechts) {
			raeume.get(0).setBreite(yLinks);
			raeume.get(0).setRaumBreite(raeume.get(0).getBreite() - 2 * WANDBREITE);
		} else {
			raeume.get(0).setBreite(yRechts);
			raeume.get(0).setRaumBreite(raeume.get(0).getBreite() - 2 * WANDBREITE);
		}
	}

	// Methode zum erzeugen der Türen im Grundriss
	private void erzeugeTueren() {

		verarbeiteRaeume(raeumeLinks, true);
		verarbeiteRaeume(raeumeRechts, false);

		platziereEingangsTuer();

	}

	// Verarbeitet alle Türen für die gegebenen Räume
	private void verarbeiteRaeume(List<RaumModell> raeume, boolean istLinks) {
		for (RaumModell raum : raeume) {
			for (TuerModell tuer : raum.getTueren()) {
				if (tuer.getVonRaum().getName().equals("Flur")) {
					platziereFlurTuer(raum, tuer, istLinks);
				} else if (tuer.getInRaum().getName().equals(raum.getName())) {
					platziereZwischenTuer(raum, tuer, raeume, istLinks);
				}
			}
		}
	}

	// Platziert die Tür für den Flur
	private void platziereFlurTuer(RaumModell raum, TuerModell tuer, boolean istLinks) {

		if (raum.getBreite() > 2) {
			double random = Math.random();
			if (random < 0.5) {
				tuer.setX(Math.round((raum.getX() + (istLinks ? raum.getLaenge() : 0))*100.0)/100.0);
				tuer.setY(Math.round((raum.getRaumY() + 0.08)*100.0)/100.0);
				tuer.setHorizontal(false);
				tuer.setLinksOeffnend(false);

			} else {
				tuer.setX(Math.round((raum.getX()  + (istLinks ? raum.getLaenge() : 0))*100.0)/100.0);
				tuer.setY(Math.round((raum.getRaumY() + raum.getRaumBreite() - (0.08 + tuer.getBreite()))*100.0)/100.0);
				tuer.setHorizontal(false);
				tuer.setLinksOeffnend(true);

			}
		} else {
			tuer.setX(Math.round((raum.getX() + (istLinks ? raum.getLaenge() : 0))*100.0)/100.0);;
			tuer.setY(Math.round((raum.getRaumY() + (raum.getRaumBreite() - tuer.getBreite()) / 2)*100.0)/100.0);;
			tuer.setHorizontal(false);
			tuer.setLinksOeffnend(true);

		}

	}

	// Platziert die Tür zwischen zwei Räumen
	private void platziereZwischenTuer(RaumModell inRaum, TuerModell tuer, List<RaumModell> raeume, boolean istLinks) {
		RaumModell vonRaum = findeRaumByNameModell(tuer.getVonRaum().getName(), raeume);
		TuerModell vonRaumTuer = findeTuerByName(tuer.getInRaum().getName(), vonRaum.getTueren());

		if (inRaum.getY() + inRaum.getBreite() == vonRaum.getY()) {
			platziereTuerObenUnten(tuer, vonRaumTuer, inRaum, vonRaum, true, istLinks);
		} else if (vonRaum.getY() + vonRaum.getBreite() == inRaum.getY()) {
			platziereTuerObenUnten(tuer, vonRaumTuer, inRaum, vonRaum, false, istLinks);
		}
	}

	// Platziert die Tür zwischen zwei Räumen, wenn der eine über dem anderen liegt
	private void platziereTuerObenUnten(TuerModell tuer, TuerModell vonRaumTuer, RaumModell inRaum, RaumModell vonRaum,
			boolean vonRaumUeberInRaum, boolean istLinks) {
		
		
		switch (Double.compare(inRaum.getLaenge() - vonRaum.getLaenge(), 0.0)) {
		
		case 0: // Räume gleich lang

			tuer.setX(istLinks ? vonRaum.getRaumX() + 0.08
					: vonRaum.getRaumX() + vonRaum.getRaumLaenge() - (0.08 + tuer.getBreite()));
			tuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY() );
			tuer.setHorizontal(true);
			tuer.setLinksOeffnend(istLinks? vonRaumUeberInRaum: !vonRaumUeberInRaum);

			vonRaumTuer.setX(istLinks ? vonRaum.getRaumX() + 0.08
					: vonRaum.getRaumX() + vonRaum.getRaumLaenge() - (0.08 + tuer.getBreite()));
			vonRaumTuer.setY(vonRaumUeberInRaum ? vonRaum.getY()  : inRaum.getY() );
			vonRaumTuer.setHorizontal(true);
			vonRaumTuer.setLinksOeffnend(istLinks? vonRaumUeberInRaum: !vonRaumUeberInRaum);
			break;

		case 1: // inRaum länger als vonRaum
			tuer.setX(istLinks ? vonRaum.getRaumX() + 0.08
					: vonRaum.getRaumX() + vonRaum.getRaumLaenge() - (0.08 + tuer.getBreite()));
			tuer.setY(vonRaumUeberInRaum ? vonRaum.getY()  : inRaum.getY() );
			tuer.setHorizontal(true);
			tuer.setLinksOeffnend(istLinks? vonRaumUeberInRaum: !vonRaumUeberInRaum);

			vonRaumTuer.setX(istLinks ? vonRaum.getRaumX() + 0.08
					: vonRaum.getRaumX() + vonRaum.getRaumLaenge() - (0.08 + tuer.getBreite()));
			vonRaumTuer.setY(vonRaumUeberInRaum ? vonRaum.getY()  : inRaum.getY() );
			vonRaumTuer.setHorizontal(true);
			vonRaumTuer.setLinksOeffnend(istLinks? vonRaumUeberInRaum: !vonRaumUeberInRaum);
			break;

		case -1: // vonRaum länger als inRaum
			tuer.setX(istLinks ? inRaum.getRaumX() + 0.08
					: inRaum.getRaumX() + inRaum.getRaumLaenge() - (0.08 + tuer.getBreite()));
			tuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY() );
			tuer.setHorizontal(true);
			tuer.setLinksOeffnend(istLinks? vonRaumUeberInRaum: !vonRaumUeberInRaum);

			vonRaumTuer.setX(istLinks ? inRaum.getRaumX() + 0.08
					: inRaum.getRaumX() + inRaum.getRaumLaenge() - (0.08 + tuer.getBreite()));
			vonRaumTuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
			vonRaumTuer.setHorizontal(true);
			vonRaumTuer.setLinksOeffnend(istLinks? vonRaumUeberInRaum: !vonRaumUeberInRaum);
			break;
		}
	}

	// Platziert Eingangstür zum Flur
	private void platziereEingangsTuer() {
		RaumModell flur = raeume.get(0);
		TuerModell flurTuer = findeEingangsTuer(flur.getTueren());

		if (flurTuer != null) {
			flurTuer.setX(Math.round((flur.getRaumX() + (flur.getRaumLaenge() - flurTuer.getBreite()) / 2.0)*100.0)/100.0);
			flurTuer.setY(Math.round((flur.getY())*100.0)/100.0);
			flurTuer.setHorizontal(true);
			flurTuer.setLinksOeffnend(true);
		}
	}

	// Findet eine Tür anhand des Namens in einer Liste von Türen
	private RaumModell findeRaumByNameModell(String name, List<RaumModell> raeume) {
		for (RaumModell raum : raeume) {
			if (raum.getName().equals(name)) {
				return raum;
			}
		}
		return null;
	}

	// Methode zum Erzeugen der Fenster im Grundriss
	private void erzeugeFenster() {

		verarbeiteRaeumemitFenstern(raeumeLinks, true);
		verarbeiteRaeumemitFenstern(raeumeRechts, false);
		platziereFlurFenster();
	}

	// Verarbeitet Räume mit Fenstern
	private void verarbeiteRaeumemitFenstern(List<RaumModell> raeume, boolean istLinks) {
		for (int i = 0; i < raeume.size(); i++) {
			RaumModell raum = raeume.get(i);

			if ((raum.getName().contains("Badezimmer") || raum.getName().contains("WC"))
					&& (i == 0 || i == raeume.size() - 1)) {
				boolean unten;
				if (i == 0) {
					unten = true;
				} else {
					unten = false;
				}
				platziereFensterInKleinenRaumen(raum, istLinks, unten);
			} else {
				if (i == 0) { // Erster Raum
					platziereFensterImErstenRaum(raum, istLinks);
				} else if (i == raeume.size() - 1) { // Letzter Raum
					platziereFensterImLetztenRaum(raum, istLinks);
				} else { // Mittlere Räume
					platziereFensterInMittlerenRaumen(raum, istLinks);
				}
			}
		}
	}

	// Platziert Fenster im ersten Raum
	private void platziereFensterImErstenRaum(RaumModell raum, boolean istLinks) {
		platziereFenster(raum, true, raum.getRaumX(), raum.getY());
		platziereFenster(raum, false, istLinks ? raum.getX() : raum.getX() + raum.getLaenge() ,
				raum.getRaumY()); 
	}

	// Platziert Fenster im letzten Raum
	private void platziereFensterImLetztenRaum(RaumModell raum, boolean istLinks) {
		platziereFenster(raum, true, raum.getRaumX(), raum.getY() + raum.getBreite() );
		platziereFenster(raum, false, istLinks ? raum.getX()  : raum.getX() + raum.getLaenge() ,
				raum.getRaumY()); 
	}

	// Platziert Fenster in mittleren Räumen
	private void platziereFensterInMittlerenRaumen(RaumModell raum, boolean istLinks) {
		platziereFenster(raum, false, istLinks ? raum.getX()  : raum.getX() + raum.getLaenge() ,
				raum.getRaumY());
	}

	// Platziert Fenster in kleinen Räumen
	private void platziereFensterInKleinenRaumen(RaumModell raum, boolean istLinks, boolean unten) {
		if (unten) {// unten
			if (istLinks) { // links
				if (raum.getLaenge() < raum.getBreite()) { // Fenster unten
					raum.addFenster(new Fenster(raum.getRaumX() + (raum.getRaumLaenge() - 0.8) / 2,
							raum.getY() , 0.8, true));
				} else { // Fenster links
					raum.addFenster(new Fenster(raum.getX() ,
							raum.getRaumY() + (raum.getRaumBreite() - 0.8) / 2, 0.8, false));
				}
			} else { // Fenster unten
				if (raum.getLaenge() < raum.getBreite()) {
					raum.addFenster(new Fenster(raum.getRaumX() + (raum.getRaumLaenge() - 0.8) / 2,
							raum.getY() , 0.8, true));
				} else {// Fenster rechts
					raum.addFenster(new Fenster(raum.getX() + raum.getLaenge() ,
							raum.getRaumY() + (raum.getRaumBreite() - 0.8) / 2, 0.8, false));
				}
			}
		} else {// oben
			if (istLinks) { // Fenster oben 
				if (raum.getLaenge() < raum.getBreite()) {
					raum.addFenster(new Fenster(raum.getRaumX() + (raum.getRaumLaenge() - 0.8) / 2,
							raum.getY() + raum.getBreite() , 0.8, true));
				} else {// Fenster links
					raum.addFenster(new Fenster(raum.getX() ,
							raum.getRaumY() + (raum.getBreite() - 0.8) / 2, 0.8, false));
				}
			} else {
				if (raum.getLaenge() < raum.getBreite()) {// Fenster oben
					raum.addFenster(new Fenster(raum.getRaumX() + (raum.getRaumLaenge() - 0.8) / 2,
							raum.getY() + raum.getBreite() , 0.8, true));
				} else {// Fenster rechts
					raum.addFenster(new Fenster(raum.getX() + raum.getLaenge() ,
							raum.getRaumY() + (raum.getRaumBreite() - 0.8) / 2, 0.8, false));
				}
			}
		}

	}

	// Platziert Fenster in einem Raum
	private void platziereFenster(RaumModell raum, boolean horizontal, double xKoordinate, double yKoordinate) {
		double fensterGroesse;

		if (raum.getRaumLaenge() <= 2 || raum.getRaumBreite() <= 2) {
			fensterGroesse = 0.8;
		} else if (raum.getRaumLaenge() <= 4 || raum.getRaumBreite() <= 4) {
			fensterGroesse = 1.1;
		} else {
			fensterGroesse = 1.5;
		}

		if (horizontal) {
			raum.addFenster(new Fenster(xKoordinate + (raum.getRaumLaenge() - fensterGroesse) / 2, yKoordinate,
					fensterGroesse, true));
		} else {
			raum.addFenster(new Fenster(xKoordinate, yKoordinate + (raum.getRaumBreite() - fensterGroesse) / 2,
					fensterGroesse, false));
		}
	}

	// Platziert das Fenster im Flur
	private void platziereFlurFenster() {
		RaumModell flur = raeume.get(0);
		platziereFenster(flur, true, flur.getRaumX(), flur.getY() + flur.getBreite());
	}

	// Findet eine Tür anhand des Namens in einer Liste von Türen
	private TuerModell findeTuerByName(String name, List<TuerModell> tueren) {
		for (TuerModell tuer : tueren) {
			if (tuer.getInRaum().getName().equals(name)) {
				return tuer;
			}
		}
		return null;
	}

	private TuerModell findeEingangsTuer(List<TuerModell> tueren) {
		for (TuerModell tuer : tueren) {
			if (tuer.getInRaum().getName().equals("Flur") && tuer.getVonRaum() == null) {
				return tuer;
			}
		}
		return null;
	}

	// Findet einen Raum mit weniger als 3 Türen
	private Raum findeRaumMitWenigenTueren(List<Raum> tmpRaeume) {
		Raum raum;
		int random;
		do {
			random = (int) (Math.random() * tmpRaeume.size());
			raum = tmpRaeume.get(random);
		} while (raum.getTueren().size() > 2);
		tmpRaeume.remove(random);
		return raum;
	}
}
