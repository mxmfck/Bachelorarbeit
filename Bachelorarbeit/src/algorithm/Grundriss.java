package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Fenster;
import model.Haus;
import model.Raum;
import model.RaumModell;
import model.Tuer;

public class Grundriss {
	// Klasse, die einen kompletten Grundriss speichert

	private List<RaumModell> raeume; // Liste aller Räume im Grundriss
	private List<RaumModell> raeumeLinks; // Liste aller Räume links des Flurs
	private List<RaumModell> raeumeRechts; // Liste aller Räume rechts des Flurs
//    private List<Raum> tmpRaeume;

	private double xLinks = 0; // x-Koordinate an dem der Raum auf den Flur trifft
	private double yLinks = 0; // Aktuelle y-Koordinate der Breite der Räume links des Flurs
	private double xRechts = 0; // x-Koordinate an dem der Raum auf den Flur trifft
	private double yRechts = 0; // Aktuelle y-Koordinate der Breite der Räume rechts des Flurs
	private double maxRechts = 0; // Maximale x-Koordinate der Räume rechts des Flurs
	private double maxLinks = 0; // Maximale x-Koordinate der Räume links des Flurs
	private boolean linksPlaziert; // Gibt an, ob der letzte Raum links oder rechts platziert wurde

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
		maxRechts = raeume.get(0).getLaenge();

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
				flur.addTuer(new Tuer(null, flur, 0.92));
				raeume.add(new RaumModell(flur.getName(), flur.getLaenge(), flur.getBreite(), flur.getMoebel(),
						flur.getTueren(), 0, 0));
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

	//Methode die den Raum letztendlich platziert
	private void platziereRaumSeite(Raum raum, List<Raum> tmpRaeume, boolean links) {
		List<RaumModell> raeume = links ? raeumeLinks : raeumeRechts;
		double x = links ? xLinks : xRechts;
		double y = links ? yLinks : yRechts;
		double max = links ? maxLinks : maxRechts;

		if (raeume == null) {
			raeume = new ArrayList<>();
			double random = Math.random();
			if (random < 0.5) {
				raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
						raum.getTueren(), links ? x - raum.getLaenge() : x, y));
				y += raum.getBreite();
				max = links ? raum.getLaenge() : x + raum.getLaenge();
			} else {
				raeume.add(new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(), raum.getMoebel(),
						raum.getTueren(), links ? x - raum.getBreite() : x, y));
				y += raum.getLaenge();
				max = links ? raum.getBreite() : x + raum.getBreite();
			}
		} else {
			if (Math.abs(raum.getLaenge() - raeume.get(raeume.size() - 1).getLaenge()) < Math
					.abs(raum.getBreite() - raeume.get(raeume.size() - 1).getLaenge())) {
				raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
						raum.getTueren(), links ? x - raum.getLaenge() : x, y));
				y += raum.getBreite();
				if (links) {
					if (max < raum.getLaenge()) {
						max = raum.getLaenge();
					}
				} else {
					if (max < x + raum.getLaenge()) {
						max = x + raum.getLaenge();
					}
				}
			} else {
				raeume.add(new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(), raum.getMoebel(),
						raum.getTueren(), links ? x - raum.getBreite() : x, y));
				y += raum.getLaenge();
				if (links) {
					if (max < raum.getBreite()) {
						max = raum.getBreite();
					}
				} else {
					if (max < x + raum.getBreite()) {
						max = x + raum.getBreite();
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
		} else {
			raeume.get(0).setBreite(yRechts);
		}
	}

	// Methode zum erzeugen der Türen im Grundriss
	private void erzeugeTueren() {

		verarbeiteRaeume(raeumeLinks, true);
		verarbeiteRaeume(raeumeRechts, false);
		
		platziereFlurTuer();

	}
	
	// Verarbeitet alle Türen für die gegebenen Räume
	private void verarbeiteRaeume(List<RaumModell> raeume, boolean istLinks) {
	    for (RaumModell raum : raeume) {
	        for (Tuer tuer : raum.getTueren()) {
	            if (tuer.getVonRaum().getName().equals("Flur")) {
	                platziereFlurTuer(raum, tuer, istLinks);
	            } else if (tuer.getInRaum().getName().equals(raum.getName())) {
	                platziereZwischenTuer(raum, tuer, raeume, istLinks);
	            }
	        }
	    }
	}

    // Platziert die Tür für den Flur
	private void platziereFlurTuer(RaumModell raum, Tuer tuer, boolean istLinks) {

	    if (raum.getBreite() > 1.12) {
	        if (Math.random() < 0.5) {
	            tuer.setX(raum.getX() + (istLinks?raum.getLaenge():0));
	            tuer.setY(raum.getY() + 0.1);
	            tuer.setHorizontal(false);
	            tuer.setLinksOeffnend(false);
	        } else {
	            tuer.setX(raum.getX() + (istLinks?raum.getLaenge():0));
	            tuer.setY(raum.getY() + raum.getBreite() - (0.1 + tuer.getBreite()));
	            tuer.setHorizontal(false);
	            tuer.setLinksOeffnend(true);
	        }
	    } else {
	        tuer.setX(raum.getX() + (istLinks?raum.getLaenge():0));
	        tuer.setY(raum.getY() + (raum.getBreite() - tuer.getBreite()) / 2);
	        tuer.setHorizontal(false);
	        tuer.setLinksOeffnend(true);
	    }
	}

	// Platziert die Tür zwischen zwei Räumen
	private void platziereZwischenTuer(RaumModell inRaum, Tuer tuer, List<RaumModell> raeume, boolean istLinks) {
	    RaumModell vonRaum = findeRaumByNameModell(tuer.getVonRaum().getName(), raeume);
	    Tuer vonRaumTuer = findeTuerByName(tuer.getInRaum().getName(), vonRaum.getTueren());

	    if (inRaum.getY() + inRaum.getBreite() == vonRaum.getY()) {
	        platziereTuerObenUnten(tuer, vonRaumTuer, inRaum, vonRaum, true, istLinks);
	    } else if (vonRaum.getY() + vonRaum.getBreite() == inRaum.getY()) {
	        platziereTuerObenUnten(tuer, vonRaumTuer, inRaum, vonRaum, false, istLinks);
	    }
	}

	// Platziert die Tür zwischen zwei Räumen, wenn der eine über dem anderen liegt
	private void platziereTuerObenUnten(Tuer tuer, Tuer vonRaumTuer, RaumModell inRaum, RaumModell vonRaum, boolean vonRaumUeberInRaum, boolean istLinks) {
	    switch (Double.compare(inRaum.getLaenge() - vonRaum.getLaenge(), 0.0)) {
	        case 0: //Räume gleich lang
//	            tuer.setX(inRaum.getX() + (vonRaumUeberInRaum ? inRaum.getLaenge() - (0.1 + tuer.getBreite()) : 0.1));
//	            tuer.setY(vonRaumUeberInRaum ? inRaum.getY() : vonRaum.getY());
//	            tuer.setHorizontal(true);
//	            tuer.setLinksOeffnend(!vonRaumUeberInRaum);

	        	tuer.setX(istLinks? vonRaum.getX()+ 0.1: vonRaum.getX()+vonRaum.getLaenge()-(0.1+tuer.getBreite()));
	        	tuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
	        	tuer.setHorizontal(true);
	        	tuer.setLinksOeffnend(vonRaumUeberInRaum);
	        	
	            vonRaumTuer.setX(istLinks? vonRaum.getX()+ 0.1: vonRaum.getX()+vonRaum.getLaenge()-(0.1+tuer.getBreite()));
	            vonRaumTuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
	            vonRaumTuer.setHorizontal(true);
	            vonRaumTuer.setLinksOeffnend(vonRaumUeberInRaum);
	            break;

	        case 1: //inRaum länger als vonRaum
	            tuer.setX(istLinks? vonRaum.getX()+ 0.1: vonRaum.getX()+vonRaum.getLaenge()-(0.1+tuer.getBreite()));
	            tuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
	            tuer.setHorizontal(true);
	            tuer.setLinksOeffnend(vonRaumUeberInRaum);

	            vonRaumTuer.setX(istLinks? vonRaum.getX()+ 0.1: vonRaum.getX()+vonRaum.getLaenge()-(0.1+tuer.getBreite()));
	            vonRaumTuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
	            vonRaumTuer.setHorizontal(true);
	            vonRaumTuer.setLinksOeffnend(vonRaumUeberInRaum);
	            break;

	        case -1: //vonRaum länger als inRaum
	            tuer.setX(istLinks? inRaum.getX()+ 0.1: inRaum.getX()+inRaum.getLaenge()-(0.1+tuer.getBreite()));
	            tuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
	            tuer.setHorizontal(true);
	            tuer.setLinksOeffnend(vonRaumUeberInRaum);

	            vonRaumTuer.setX(istLinks? inRaum.getX()+ 0.1: inRaum.getX()+inRaum.getLaenge()-(0.1+tuer.getBreite()));
	            vonRaumTuer.setY(vonRaumUeberInRaum ? vonRaum.getY() : inRaum.getY());
	            vonRaumTuer.setHorizontal(true);
	            vonRaumTuer.setLinksOeffnend(vonRaumUeberInRaum);
	            break;
	    }
	}

	// Platziert Eingangstür zum Flur
	private void platziereFlurTuer() {
	    RaumModell flur = raeume.get(0);
	    Tuer flurTuer = findeTuerByName("Flur", flur.getTueren());

	    if (flurTuer != null) {
	        flurTuer.setX((flur.getBreite() - flurTuer.getBreite()) / 2);
	        flurTuer.setY(flur.getY());
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

	        if ((raum.getName().contains("Badezimmer") || raum.getName().contains("WC"))&&(i==0||i==raeume.size()-1)) {
	            platziereFensterInKleinenRaumen(raum);
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
	    platziereFenster(raum, true, raum.getX(), raum.getY());
	    platziereFenster(raum, false, istLinks ? raum.getX() : raum.getX() + raum.getLaenge(), raum.getY()); //Test
	}

	// Platziert Fenster im letzten Raum
	private void platziereFensterImLetztenRaum(RaumModell raum, boolean istLinks) {
	    platziereFenster(raum, true, raum.getX(), raum.getY() + raum.getBreite());
	    platziereFenster(raum, false, istLinks ? raum.getX(): raum.getX()+raum.getLaenge(), raum.getY()); //Test
	}

	// Platziert Fenster in mittleren Räumen
	private void platziereFensterInMittlerenRaumen(RaumModell raum, boolean istLinks) {
	    platziereFenster(raum, false, istLinks ? raum.getX() : raum.getX() + raum.getLaenge(), raum.getY());
	}

	// Platziert Fenster in kleinen Räumen
	private void platziereFensterInKleinenRaumen(RaumModell raum) {
	    if (raum.getLaenge() < raum.getBreite()) {
	        raum.addFenster(new Fenster(raum.getX() + (raum.getLaenge() - 0.8) / 2, raum.getY(), 0.8, true));
	    } else {
	        raum.addFenster(new Fenster(raum.getX(), raum.getY() + (raum.getBreite() - 0.8) / 2, 0.8, false));
	    }
	}
	
	// Platziert Fenster in einem Raum
	private void platziereFenster(RaumModell raum, boolean horizontal, double xKoordinate, double yKoordinate) {
	    double fensterGroesse;

	    if (raum.getLaenge() <= 2 || raum.getBreite() <= 2) {
	        fensterGroesse = 0.8;
	    } else if (raum.getLaenge() <= 4 || raum.getBreite() <= 4) {
	        fensterGroesse = 1.1;
	    } else {
	        fensterGroesse = 1.5;
	    }

	    if (horizontal) {
	        raum.addFenster(new Fenster(xKoordinate + (raum.getLaenge() - fensterGroesse) / 2, yKoordinate, fensterGroesse, true));
	    } else {
	        raum.addFenster(new Fenster(xKoordinate, yKoordinate + (raum.getBreite() - fensterGroesse) / 2, fensterGroesse, false));
	    }
	}

	// Platziert das Fenster im Flur
	private void platziereFlurFenster() {
	    RaumModell flur = raeume.get(0);
	    platziereFenster(flur, true, flur.getX(), flur.getY() + flur.getBreite());
	}

	// Findet eine Tür anhand des Namens in einer Liste von Türen
	private Tuer findeTuerByName(String name, List<Tuer> tueren) {
		for (Tuer tuer : tueren) {
			if (tuer.getInRaum().getName().equals(name)) {
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
