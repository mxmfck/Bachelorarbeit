package model;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import algorithm.Grundriss;
import algorithm.GrundrissEvaluator;
import algorithm.MoebelPlatzierer;
import model.raeume.Flur;
import parser.FileParser;
import visualization.GrundrissPanel;

/*Klasse in der alle Räume und Türen gespeichert werden
 *Basierend auf dieser Klasse kann der Grundriss eines Hauses erstellt werden
 * */

public class Haus {

	private List<Raum> raeume; // Speichert alle Räume des Hauses
	private Raum flur; // Speichert den Flur des Hauses (wird zu einem späterem Zeitpunkt variabel
						// generiert)
	private List<Tuer> tueren;

//	Übergabe des Pfades der Datei, die die Anforderungen enthält 
	public Haus(String path) {
		raeume = new ArrayList<Raum>(); // Initialisierung der Liste für die Räume
		tueren = new ArrayList<Tuer>(); // Initialisierung der Liste für die Türen
		flur = new Flur("Flur", 2, 1.5, null, null); // Initialisierung des Flurs (vorläufig)
		Tuer tuer = new Tuer(null, flur, 0.92); // Initialisierung der Tür des Flurs (vorläufig)
		flur.addTuer(tuer);
		raeume.add(flur); // Hinzufügen des Flurs zur Liste der Räume
		try {
			FileParser.parseHaus(path, this);// Aufruf des Parsers, der die Anforderungen aus der Datei einliest |
												// Übergabe des Pfades und des aktuellen Hauses
		} catch (IOException e) {
			// TODO Auto-generated catch block | Fehlerbehandlung
			e.printStackTrace();
		}

		Grundriss grundriss = GrundrissEvaluator.findeBestenGrundriss(this); // Erstellung eines Grundrisses auf Basis
																				// des Hauses
//		SpiralBasedGrundriss spiralBasedGrundriss = new SpiralBasedGrundriss(this); // Erstellung eines Grundrisses auf
		// Basis des Hauses
		MoebelPlatzierer.einrichten(grundriss);// Platzierung der Möbel im Raum

		// GrundrissPanel erstellen und mit der Liste der RaumModelle initialisieren
		GrundrissPanel grundrissPanel = new GrundrissPanel(grundriss);

		// Swing-Komponenten initialisieren
		JFrame frame = new JFrame("Grundriss Visualisierung");
		frame.add(new JScrollPane(grundrissPanel));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);

		grundrissPanel.revalidate();
		grundrissPanel.doLayout();

		SwingUtilities.invokeLater(() -> savePanelasImage(grundrissPanel, "grundriss.png"));
		

	}

	public List<Raum> getRaeume() { // Getter für die Liste der Räume
		return raeume;
	}

	public void addRaum(Raum raum) { // Methode zum Hinzufügen eines Raumes
		raeume.add(raum);
	}

	public List<Tuer> getTueren() { // Getter für die Liste der Türen
		return tueren;
	}

	public void addTuer(Tuer tuer) { // Methode zum Hinzufügen einer Türe
		tueren.add(tuer);
	}

	public Raum getFlur() { // Getter für den Flur
		return flur;
	}

	public Raum getRaumByName(String name) { // Methode zum Suchen eines Raumes anhand des Namens | Nötig für die
												// Zuteilung der Türen
		for (Raum raum : raeume) {
			if (raum.getName().equals(name)) {
				return raum;
			}
		}
		throw new IllegalArgumentException("Fehler beim Suchen des Raumes. Raum nicht gefunden");
	}

	public String toString() { // Methode zur Validirierung des Inputs
		String result = "";
		for (Raum raum : raeume) {
			result += raum.toString() + "\n";
		}
		return result;
	}

	public void savePanelasImage(JPanel panel, String filename) {

		Dimension size = panel.getPreferredSize();

		// Erstellt ein BufferedImage mit der Größe des Panels
		BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		
		// Panel-Inhalt auf das BufferedImage rendern
		Graphics2D g2d = image.createGraphics();
		panel.paint(g2d);
		g2d.dispose();

		// Bilddatei speichern
		try {
			ImageIO.write(image, "png", new File(filename));
			System.out.println("Bild gespeichert als: " + filename);
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern des Bildes.");
		} finally {
			System.exit(0);
		}
	}
}
