package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.raeume.Flur;
import parser.FileParser;

/*Klasse in der alle Räume und Türen gespeichert werden
 *Basierend auf dieser Klasse kann der Grundriss eines Hauses erstellt werden
 * */

public class Haus {

	
	private List<Raum> raeume; //Speichert alle Räume des Hauses
	private Raum flur; //Speichert den Flur des Hauses (wird zu einem späterem Zeitpunkt variabel generiert)
	private List<Tuer> tueren;

//	Übergabe des Pfades der Datei, die die Anforderungen enthält 
	public Haus(String path) {
		raeume = new ArrayList<Raum>(); //Initialisierung der Liste für die Räume
		tueren = new ArrayList<Tuer>(); //Initialisierung der Liste für die Türen
		flur = new Flur("Flur", 2, 1.5, null, null); //Initialisierung des Flurs (vorläufig)
		raeume.add(flur); //Hinzufügen des Flurs zur Liste der Räume //TODO
		try {
			FileParser.parseHaus(path, this);//Aufruf des Parsers, der die Anforderungen aus der Datei einliest | Übergabe des Pfades und des aktuellen Hauses
		} catch (IOException e) {
			// TODO Auto-generated catch block | Fehlerbehandlung
			e.printStackTrace();
		}
	}

	public List<Raum> getRaeume() { //Getter für die Liste der Räume
		return raeume;
	}

	public void addRaum(Raum raum) { //Methode zum Hinzufügen eines Raumes
		raeume.add(raum);
	}

	public List<Tuer> getTueren() { //Getter für die Liste der Türen
		return tueren;
	}

	public void addTuer(Tuer tuer) { //Methode zum Hinzufügen einer Türe
		tueren.add(tuer);
	}

	public Raum getFlur() { //Getter für den Flur
		return flur;
	}

	public Raum getRaumByName(String name) { //Methode zum Suchen eines Raumes anhand des Namens | Nötig für die Zuteilung der Türen
		for (Raum raum : raeume) {
			if (raum.getName().equals(name)) {
				return raum;
			}
		}
		throw new IllegalArgumentException("Raum nicht gefunden");
	}
	
	public String toString() { // Methode zur Validirierung des Inputs
		String result = "";
		for (Raum raum : raeume) {
			result += raum.toString() + "\n";
		}
		return result;
	}
}
