package model.raeume;

import java.util.List;

import model.Moebelstueck;
import model.Raum;
import model.Tuer;

public class WC extends Raum{

	public WC(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		super(name, laenge, breite, moebel, tueren);
	}

	
}
