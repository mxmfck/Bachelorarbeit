package model.raeume;

import java.util.List;

import model.Moebelstueck;
import model.Raum;
import model.Tuer;

public class Schlafzimmer extends Raum{

	public Schlafzimmer(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		super(name, laenge, breite, moebel, tueren);
	}

}
