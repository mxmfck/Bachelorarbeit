package model.moebel;

import java.awt.Color;
import java.awt.Graphics2D;

import model.Moebelstueck;

public class Sofa extends Moebelstueck{

	public Sofa(double laenge, double breite, double keepOutLinks, double keepOutRechts, double keepOutOben,
			double keepOutUnten) {
		super(laenge, breite, keepOutLinks, keepOutRechts, keepOutOben, keepOutUnten);
	}
	
		public void draw(Graphics2D g2d, int x, int y, int width, int height) {
	       

	        // Farbe für das Sofa
	        g2d.setColor(Color.WHITE);
	        g2d.fillRect(x, y, width, height); // Basis des Sofas

	        // Rahmen um das Sofa
	        g2d.setColor(Color.BLACK);
	        g2d.drawRect(x, y, width, height);

	        // Zeichne die Rückenlehne (oberer Balken)
	        int lehnenHoehe = height / 5; // Höhe der Rückenlehne ist ein Fünftel der gesamten Höhe
	        g2d.fillRect(x, y, width, lehnenHoehe);

	        // Zeichne die Armlehnen (linker und rechter Balken)
	        int armlehnenBreite = width / 10; // Breite der Armlehnen ist ein Zehntel der gesamten Breite
	        g2d.fillRect(x, y, armlehnenBreite, height); // Linke Armlehne
	        g2d.fillRect(x + width - armlehnenBreite, y, armlehnenBreite, height); // Rechte Armlehne
	    }
	}

