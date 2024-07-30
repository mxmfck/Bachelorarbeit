package model.raeume;

import java.util.List;

import model.Moebelstueck;
import model.Raum;
import model.Tuer;

public class Badezimmer extends Raum{

	public Badezimmer(double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		super(laenge, breite, moebel, tueren);
	}

	
}
