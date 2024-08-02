package model;

import java.io.IOException;
import java.util.List;

import parser.FileParser;

public class Haus {

	private List<Raum> raeume;
	private Raum flur;
	private List<Tuer> tueren;

	public Haus(String filePath) {
		try {
			FileParser.parseHaus(filePath, this);
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
