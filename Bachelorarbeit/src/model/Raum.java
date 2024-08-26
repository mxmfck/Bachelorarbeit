package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Raum implements Cloneable{
//	Abstrakte Klasse für alle Räume
	
	private double laenge; //Länge des Raumes
	private double breite; //Breite des Raumes
	private List<Moebelstueck> moebel; //Liste der Möbelstücke im Raum
	private double groesse; //Größe des Raumes
	private List<Tuer> tueren; //Liste der Türen im Raum
	private String name; // Name des Raumes zur Identifikation
//	private double x;
//	private double y;

	public Raum(String name, double laenge, double breite, List<Moebelstueck> moebel, List<Tuer> tueren) {
		this.name = name;
		this.laenge = laenge;
		this.breite = breite;
		this.moebel = moebel;
		this.tueren = tueren;
		this.groesse = laenge * breite;
	}

//	@Override
//    public Object clone() throws CloneNotSupportedException {
//        Raum clonedRaum = (Raum) super.clone();
//        
//        // Tiefe Kopie der Möbelliste
//        if (this.moebel != null) {
//            clonedRaum.moebel = new ArrayList<>();
//            for (Moebelstueck m : this.moebel) {
//                clonedRaum.moebel.add((Moebelstueck) m.clone());
//            }
//        }
//        
//        // Tiefe Kopie der Türenliste
//        if (this.tueren != null) {
//            clonedRaum.tueren = new ArrayList<>();
//            for (Tuer t : this.tueren) {
//                Tuer clonedTuer = (Tuer) t.clone();
//                // Aktualisiere die Raumreferenzen in der geklonten Tür
//                if (t.getVonRaum() == this) {
//                    clonedTuer.setVonRaum(clonedRaum);
//                }
//                if (t.getInRaum() == this) {
//                    clonedTuer.setInRaum(clonedRaum);
//                }
//                clonedRaum.tueren.add(clonedTuer);
//            }
//        }
//        
//        return clonedRaum;
//    }

	//Getter für alle Attribute
	public double getLaenge() {
		return laenge;
	}

	public double getBreite() {
		return breite;
	}

	public List<Moebelstueck> getMoebel() {
		return moebel;
	}

	public double getGroesse() {
		return groesse;
	}

	public List<Tuer> getTueren() {
		return tueren;
	}
	
//	public double getX() {
//		return x;
//	}
//	
//	public double getY() {
//		return y;
//	}

	//Methoden zum Hinzufügen von Möbelstücken und Türen
	public void addMoebel(Moebelstueck moebel) {
		this.moebel.add(moebel);
	}

	public void addTuer(Tuer tuer) {
		if (this.tueren == null)
			this.tueren = new ArrayList<Tuer>();
		this.tueren.add(tuer);
	}

	
	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}

	public void setBreite(double breite) {
		this.breite = breite;
	}

	public void setGroesse(double groesse) {
		this.groesse = groesse;
	}
	
//	public void setX(double x) {
//		this.x = x;
//	}
//	
//	public void setY(double y) {
//		this.y = y;
//	}

	public String getName() {
		return name;
	}
	
//	public void setVonRaum(Tuer tuer, Raum raum) {
//        tuer.setVonRaum(raum);
//    }
//	
//	public void setInRaum(Tuer tuer, Raum raum) {
//        tuer.setInRaum(raum);
//    }

	
	public String toString() {
		String result="Name: " + name + " | Länge: " + laenge + " | Breite: " + breite + " | Größe: " + groesse + " | Möbel: "+ "\n";
		if (moebel == null)
            return result;
		for (Moebelstueck moebelstueck : moebel) {
			result += moebelstueck.toString() + "\n";
		}
		return result;
	}
}
