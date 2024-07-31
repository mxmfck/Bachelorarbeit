package model;

import java.util.List;

public class Haus {

	private List<Raum> raeume;
	private Raum flur;
	private List<Tuer> tueren;
	
	public Haus() {
		this.raeume = raeume;
		this.flur = flur;
		this.tueren = tueren;
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
	
	
}
