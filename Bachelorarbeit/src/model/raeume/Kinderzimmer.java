package model.raeume;

import java.util.List;

import model.Moebelstueck;
import model.Raum;
import model.Tuer;

public class Kinderzimmer extends Raum{

	public Kinderzimmer(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		super(name, laenge, breite, moebel, tueren);
	}

	
}
