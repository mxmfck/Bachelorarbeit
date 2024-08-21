package model;

import java.util.ArrayList;
import java.util.List;

public class Grundriss {
	// Klasse, die einen kompletten Grundriss speichert

	private List<RaumModell> raeume;
	private List<RaumModell> raeumeLinks;
	private List<RaumModell> raeumeRechts;
//    private List<Raum> tmpRaeume;

	private double xLinks = 0; // x-Koordinate an dem der Raum auf den Flur trifft
	private double yLinks = 0; // Aktuelle y-Koordinate der Breite der Räume links des Flurs
	private double xRechts = 0; // x-Koordinate an dem der Raum auf den Flur trifft
	private double yRechts = 0; // Aktuelle y-Koordinate der Breite der Räume rechts des Flurs
	private double maxRechts = 0;
	private double maxLinks = 0;
	private boolean linksPlaziert;

	public Grundriss(Haus haus) {
		berechnePositionen(haus);
		erzeugeTueren();
	}

	public List<RaumModell> getRaeume() {
		return raeume;
	}

	public void addRaum(RaumModell raum) {
		raeume.add(raum);
	}

	public void berechnePositionen(Haus haus) {
		List<Raum> tmpRaeume;
		tmpRaeume = new ArrayList<>(haus.getRaeume());
		raeume = new ArrayList<>();
		findeUndEntferneFlur(tmpRaeume);
		xRechts = raeume.get(0).getLaenge();
		maxRechts = raeume.get(0).getLaenge();

		Raum ersterRaum;
		int random;
		// Setze den ersten Raum
		do {
			random = (int) (Math.random() * tmpRaeume.size());
			ersterRaum = tmpRaeume.get(random);
		} while (ersterRaum.getTueren().size() > 2);
		tmpRaeume.remove(random);

		linksPlaziert = true;
		platziereRaumLinks(ersterRaum, tmpRaeume);

		do {
			random = (int) (Math.random() * tmpRaeume.size());
			ersterRaum = tmpRaeume.get(random);
		} while (ersterRaum.getTueren().size() > 2);
		tmpRaeume.remove(random);

		linksPlaziert = false;
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

	public void platziereRaum(Raum raum, List<Raum> tmpRaeume) {
		if (yLinks == 0) {

		}
		if (yLinks <= yRechts) {
//            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
//                    raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
//            yLinks += raum.getBreite();
//            linksPlaziert = true;
			platziereRaumLinks(raum, tmpRaeume);
		} else {
//            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
//                    raum.getTueren(), xRechts, yRechts));
//            yRechts += raum.getBreite();
//            linksPlaziert = false;
			platziereRaumRechts(raum, tmpRaeume);
		}
//		checkTueren(raum, tmpRaeume);
	}

	public void platziereRaumLinks(Raum raum, List<Raum> tmpRaeume) {

		if (raeumeLinks == null) {
			raeumeLinks = new ArrayList<>();
			double random = Math.random();
			if (random < 0.5) {
				raeumeLinks.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
						raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
				yLinks += raum.getBreite();
				maxLinks = raum.getLaenge();
			} else {
				raeumeLinks.add(new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(), raum.getMoebel(),
						raum.getTueren(), xLinks - raum.getBreite(), yLinks));
				yLinks += raum.getLaenge();
				maxLinks = raum.getBreite();
			}
		} else {
			if (Math.abs(raum.getLaenge() - raeumeLinks.get(raeumeLinks.size() - 1).getLaenge()) < Math
					.abs(raum.getBreite() - raeumeLinks.get(raeumeLinks.size() - 1).getLaenge())) {
				raeumeLinks.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
						raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
				yLinks += raum.getBreite();
				if (maxLinks < raum.getLaenge()) {
					maxLinks = raum.getLaenge();
				}
			} else {
				raeumeLinks.add(new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(), raum.getMoebel(),
						raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
				yLinks += raum.getLaenge();
				if (maxLinks < raum.getBreite()) {
					maxLinks = raum.getBreite();
				}
			}
		}

		linksPlaziert = true;
		checkTueren(raum, tmpRaeume);

	}

	public void platziereRaumRechts(Raum raum, List<Raum> tmpRaeume) {

		if (raeumeRechts == null) {
			raeumeRechts = new ArrayList<>();
			double random = Math.random();
			if (random < 0.5) {
				raeumeRechts.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
						raum.getTueren(), xRechts, yRechts));
				yRechts += raum.getBreite();
				maxRechts = xRechts + raum.getLaenge();
			} else {
				raeumeRechts.add(new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(), raum.getMoebel(),
						raum.getTueren(), xRechts, yRechts));
				yRechts += raum.getLaenge();
				maxRechts = xRechts + raum.getBreite();
			}
		} else {
			if (Math.abs(raum.getLaenge() - raeumeRechts.get(raeumeRechts.size() - 1).getLaenge()) < Math
					.abs(raum.getBreite() - raeumeRechts.get(raeumeRechts.size() - 1).getLaenge())) {
				raeumeRechts.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
						raum.getTueren(), xRechts, yRechts));
				yRechts += raum.getBreite();
				if (maxRechts < xRechts + raum.getLaenge()) {
					maxRechts = xRechts + raum.getLaenge();
				}
			} else {
				raeumeRechts.add(new RaumModell(raum.getName(), raum.getBreite(), raum.getLaenge(), raum.getMoebel(),
						raum.getTueren(), xRechts, yRechts));
				yRechts += raum.getLaenge();
				if (maxRechts < xRechts + raum.getBreite()) {
					maxRechts = xRechts + raum.getBreite();
				}
			}

			linksPlaziert = false;
			checkTueren(raum, tmpRaeume);

		}
	}

	public void checkTueren(Raum letzterRaum, List<Raum> tmpRaeume) {
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
	public void platziereAngrenzendenRaum(Raum raum, List<Raum> tmpRaeume) {
		if (linksPlaziert) {
//            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
//                    raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
//            yLinks += raum.getBreite();
			platziereRaumLinks(raum, tmpRaeume);
		} else {
//            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
//                    raum.getTueren(), xRechts, yRechts));
//            yRechts += raum.getBreite();
			platziereRaumRechts(raum, tmpRaeume);
		}
//        checkTueren(raum, tmpRaeume);
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

	public void verlaengereFlur() {
		if (yLinks > yRechts) {
			raeume.get(0).setBreite(yLinks);
		} else {
			raeume.get(0).setBreite(yRechts);
		}
	}

	public void erzeugeTueren() {

		for (RaumModell raum : raeumeLinks) {
			for (Tuer tuer : raum.getTueren()) {
				String raumName = tuer.getVonRaum().getName();
				// Platzierung der Türen zum FLur
				if (raumName == "Flur") {
					double random = Math.random();

					if (raum.getBreite() > 1.02) {
						if (random < 0.5) {
							tuer.setX(raum.getX() + raum.getLaenge());
							tuer.setY(raum.getY() + 0.1);
							tuer.setHorizontal(false);
							tuer.setLinksOeffnend(false);
						} else {
							tuer.setX(raum.getX() + raum.getLaenge());
							tuer.setY(raum.getY() + raum.getLaenge() - 0.1);
							tuer.setHorizontal(false);
							tuer.setLinksOeffnend(true);
						}
					} else {
						tuer.setX(raum.getX() + raum.getLaenge());
						tuer.setY(raum.getY() + (raum.getBreite() - tuer.getBreite()) / 2);
						tuer.setHorizontal(false);
						tuer.setLinksOeffnend(false);

					}
				} else {
					if (tuer.getInRaum().getName() == raum.getName()) {
						RaumModell inRaum = raum;
						RaumModell vonRaum = findeRaumByNameModell(tuer.getVonRaum().getName(), raeumeLinks);
						if (inRaum.getY() + inRaum.getBreite() == vonRaum.getY()) {// inRaum unter vonRaum
							switch (Double.compare((inRaum.getLaenge() - vonRaum.getLaenge()), 0.0)) { // Vergleich der
																										// Längen der
																										// Räume returns
																										// 1 wenn inRaum
																										// länger, -1
																										// wenn vonRaum
																										// länger, 0
																										// wenn gleich
																										// lang

							case 0:
								tuer.setX(inRaum.getX() + 0.1);
								tuer.setY(vonRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(true);
								break;

							case 1:
								tuer.setX(vonRaum.getX() + 0.1);
								tuer.setY(vonRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(true);
								break;

							case -1:
								tuer.setX(inRaum.getX() + 0.1);
								tuer.setY(vonRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(true);
								break;

							}
						} else if (vonRaum.getY() + vonRaum.getBreite() == inRaum.getY()) {// inRaum über vonRaum)
							switch (Double.compare((inRaum.getLaenge() - vonRaum.getLaenge()), 0.0)) { // Vergleich der
							// Längen der
							// Räume returns
							// 1 wenn inRaum
							// länger, -1
							// wenn vonRaum
							// länger, 0
							// wenn gleich
							// lang

							case 0:
								tuer.setX(inRaum.getX() + 0.1);
								tuer.setY(inRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(false);
								break;

							case 1:
								tuer.setX(vonRaum.getX() + 0.1);
								tuer.setY(inRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(false);
								break;

							case -1:
								tuer.setX(inRaum.getX() + 0.1);
								tuer.setY(inRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(false);
								break;
							}
						}
					}
				}
			}
		}
		for (RaumModell raum : raeumeRechts) {
			for (Tuer tuer : raum.getTueren()) {
				String raumName = tuer.getVonRaum().getName();
				// Platzierung der Türen zum FLur
				if (raumName == "Flur") {
					double random = Math.random();

					if (raum.getBreite() > 1.02) {
						if (random < 0.5) {
							tuer.setX(raum.getX());
							tuer.setY(raum.getY() + 0.1);
							tuer.setHorizontal(false);
							tuer.setLinksOeffnend(true);
						} else {
							tuer.setX(raum.getX());
							tuer.setY(raum.getY() + raum.getLaenge() - 0.1);
							tuer.setHorizontal(false);
							tuer.setLinksOeffnend(false);
						}
					} else {
						tuer.setX(raum.getX());
						tuer.setY(raum.getY() + (raum.getBreite() - tuer.getBreite()) / 2);
						tuer.setHorizontal(false);
						tuer.setLinksOeffnend(true);

					}
				} else {
					if (tuer.getInRaum().getName() == raum.getName()) {
						RaumModell inRaum = raum;
						RaumModell vonRaum = findeRaumByNameModell(tuer.getVonRaum().getName(), raeumeLinks);
						if (inRaum.getY() + inRaum.getBreite() == vonRaum.getY()) {// inRaum unter vonRaum
							switch (Double.compare((inRaum.getLaenge() - vonRaum.getLaenge()), 0.0)) { // Vergleich der
																										// Längen der
																										// Räume returns
																										// 1 wenn inRaum
																										// länger, -1
																										// wenn vonRaum
																										// länger, 0
																										// wenn gleich
																										// lang

							case 0:
								tuer.setX(inRaum.getX() + inRaum.getLaenge() - (0.1+tuer.getBreite()));
								tuer.setY(vonRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(false);
								break;

							case 1:
								tuer.setX(vonRaum.getX() + inRaum.getLaenge() - (0.1+tuer.getBreite()));
								tuer.setY(vonRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(false);
								break;

							case -1:
								tuer.setX(inRaum.getX() + inRaum.getLaenge() - (0.1+tuer.getBreite()));
								tuer.setY(vonRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(false);
								break;

							}
						} else if (vonRaum.getY() + vonRaum.getBreite() == inRaum.getY()) {// inRaum über vonRaum)
							switch (Double.compare((inRaum.getLaenge() - vonRaum.getLaenge()), 0.0)) { // Vergleich der
							// Längen der
							// Räume returns
							// 1 wenn inRaum
							// länger, -1
							// wenn vonRaum
							// länger, 0
							// wenn gleich
							// lang

							case 0:
								tuer.setX(inRaum.getX() + inRaum.getLaenge() - (0.1+tuer.getBreite()));
								tuer.setY(inRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(true);
								break;

							case 1:
								tuer.setX(vonRaum.getX() + inRaum.getLaenge() - (0.1+tuer.getBreite()));
								tuer.setY(inRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(true);
								break;

							case -1:
								tuer.setX(inRaum.getX() + inRaum.getLaenge() - (0.1+tuer.getBreite()));
								tuer.setY(inRaum.getY());
								tuer.setHorizontal(true);
								tuer.setLinksOeffnend(true);
								break;
							}
						}
					}
				}
			}
		}
		
		RaumModell flur = raeume.get(0);
		Tuer tuer = raeume.get(0).getTueren().get(0);
		tuer.setX((flur.getBreite()-tuer.getBreite())/2);
		tuer.setY(flur.getY());
		tuer.setHorizontal(true);
		tuer.setLinksOeffnend(true);

	}

	private RaumModell findeRaumByNameModell(String name, List<RaumModell> raeume) {
		for (RaumModell raum : raeume) {
			if (raum.getName().equals(name)) {
				return raum;
			}
		}
		return null;
	}
	
	private void erzeugeFenster() {
		for (RaumModell raum :raeumeLinks){
			
		}
	}
}
