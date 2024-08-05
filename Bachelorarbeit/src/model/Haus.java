package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.raeume.Flur;
import parser.FileParser;

public class Haus {

	private List<Raum> raeume;
	private Raum flur;
	private List<Tuer> tueren;

	public Haus(String path) {
		raeume = new ArrayList<Raum>();
		tueren = new ArrayList<Tuer>();
		flur = new Flur("Flur", 2, 1.5, null, null);
		try {
			FileParser.parseHaus(path, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Raum> getRaeume() {
		return raeume;
	}

	public void addRaum(Raum raum) {
		raeume.add(raum);
	}

	public List<Tuer> getTueren() {
		return tueren;
	}

	public void addTuer(Tuer tuer) {
		tueren.add(tuer);
	}

	public Raum getFlur() {
		return flur;
	}

	public Raum getRaumByName(String name) {
		for (Raum raum : raeume) {
			if (raum.getName().equals(name)) {
				return raum;
			}
		}
		return null;
	}
}
