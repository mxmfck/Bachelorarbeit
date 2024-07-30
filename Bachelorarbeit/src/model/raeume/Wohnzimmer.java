package model.raeume;

import java.util.List;

import model.Moebelstueck;
import model.Raum;
import model.Tuer;

public class Wohnzimmer extends Raum{

	public Wohnzimmer(double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		super(laenge, breite, moebel, tueren);
	}

	
}
