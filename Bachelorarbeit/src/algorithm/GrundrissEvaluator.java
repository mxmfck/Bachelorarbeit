package algorithm;

import model.Haus;

public class GrundrissEvaluator {

	// Methode, die den besten Grundriss für ein Haus findet
	public static Grundriss findeBestenGrundriss(Haus haus)  { 
	Grundriss besterGrundriss = null;
	double bestesErgebnis = Double.MAX_VALUE;
	
	for (int i = 0; i < 100; i++) {
//		try {
//			Haus hausClone = (Haus) haus.clone();
			Grundriss grundriss = new Grundriss(haus);
			double ergebnis = berechneErgebnis(grundriss);
			if (ergebnis < bestesErgebnis) {
				bestesErgebnis = ergebnis;
				besterGrundriss = grundriss;
			}
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	return besterGrundriss;
	}

	// Methode, die die Fläche eines Grundrisses berechnet
	private static double berechneErgebnis(Grundriss grundriss) {
		double maxLinks = grundriss.getMaxLinks();
		double maxRechts = grundriss.getMaxRechts();
		double yLinks = grundriss.getYLinks();
		double yRechts = grundriss.getYRechts();
		
		return (Math.abs(maxLinks)+maxRechts) * Math.max(yLinks, yRechts);
	}
}
