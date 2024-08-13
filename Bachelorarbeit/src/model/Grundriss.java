package model;

import java.util.ArrayList;
import java.util.List;

public class Grundriss {
    // Klasse, die einen kompletten Grundriss speichert

    private List<RaumModell> raeume;
    private List<Raum> tmpRaeume;

    private double xLinks = 0; // x-Koordinate an dem der Raum auf den Flur trifft
    private double yLinks = 0; // Aktuelle y-Koordinate der Breite der Räume links des Flurs
    private double xRechts = 0; // x-Koordinate an dem der Raum auf den Flur trifft
    private double yRechts = 0; // Aktuelle y-Koordinate der Breite der Räume rechts des Flurs
    private boolean linksPlaziert;

    public Grundriss(Haus haus) {
        berechnePositionen(haus);
    }

    public List<RaumModell> getRaeume() {
        return raeume;
    }

    public void addRaum(RaumModell raum) {
        raeume.add(raum);
    }

    public void berechnePositionen(Haus haus) {
        tmpRaeume = new ArrayList<>(haus.getRaeume());
        raeume = new ArrayList<>();
        findeUndEntferneFlur(tmpRaeume);
        xRechts = raeume.get(0).getLaenge();

        Raum ersterRaum;
        int random;
        // Setze den ersten Raum
        do {
            random = (int) (Math.random() * tmpRaeume.size());
            ersterRaum = tmpRaeume.get(random);
        } while (ersterRaum.getTueren().size() > 2);
        tmpRaeume.remove(random);

        linksPlaziert = true;
        platziereRaum(ersterRaum);

        while (!tmpRaeume.isEmpty()) {
            Raum raum = tmpRaeume.remove((int) (Math.random() * tmpRaeume.size()));
            platziereRaum(raum);
        }

        verlaengereFlur();
    }

    // Methode zum Finden und Entfernen des Flurs aus der Liste
    private void findeUndEntferneFlur(List<Raum> tmpRaeume) {
        for (int i = 0; i < tmpRaeume.size(); i++) {
            if (tmpRaeume.get(i).getName().equals("Flur")) {
                Raum flur = tmpRaeume.remove(i);
                raeume.add(new RaumModell(flur.getName(), flur.getLaenge(), flur.getBreite(), flur.getMoebel(),
                        flur.getTueren(), 0, 0));
                return;
            }
        }
    }

    public void platziereRaum(Raum raum) {
        if (yLinks <= yRechts) {
            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
                    raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
            yLinks += raum.getBreite();
            linksPlaziert = true;
        } else {
            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
                    raum.getTueren(), xRechts, yRechts));
            yRechts += raum.getBreite();
            linksPlaziert = false;
        }
        checkTueren(raum);
    }

    public void checkTueren(Raum letzterRaum) {
        for (Tuer tuer : letzterRaum.getTueren()) {
            if (tuer.getInRaum().getName().equals(letzterRaum.getName()) && !tuer.getVonRaum().getName().equals("Flur")) {
                Raum raum = findeRaumByName(tuer.getVonRaum().getName());
                if (raum != null) {
                    platziereAngrenzendenRaum(raum);
                }
            } else if (tuer.getVonRaum().getName().equals(letzterRaum.getName()) && !tuer.getInRaum().getName().equals("Flur")) {
                Raum raum = findeRaumByName(tuer.getInRaum().getName());
                if (raum != null) {
                    platziereAngrenzendenRaum(raum);
                }
            }
        }
    }

    // Methode zum Platzieren eines angrenzenden Raums auf der gleichen Seite wie der vorherige Raum
    public void platziereAngrenzendenRaum(Raum raum) {
        if (linksPlaziert) {
            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
                    raum.getTueren(), xLinks - raum.getLaenge(), yLinks));
            yLinks += raum.getBreite();
        } else {
            raeume.add(new RaumModell(raum.getName(), raum.getLaenge(), raum.getBreite(), raum.getMoebel(),
                    raum.getTueren(), xRechts, yRechts));
            yRechts += raum.getBreite();
        }
        checkTueren(raum);
    }

    // Methode zum Finden eines Raumes anhand des Namens in tmpRaeume
    private Raum findeRaumByName(String name) {
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
}

