package visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

import algorithm.Grundriss;
import model.Fenster;
import model.Moebelstueck;
import model.RaumModell;
import model.TuerModell;
import model.moebel.Sofa;

public class GrundrissPanel extends JPanel {
	private List<RaumModell> raeume;
	private static final int SCALE = 100; // Skalierungsfaktor für die Darstellung
	private static final int WALL_THICKNESS = 8; // Dicke der Wände in Pixeln
	private static final int FIXED_OFFSET = 10; // Feste Verschiebung um 25 Pixel nach rechts und unten
	private int offsetX = 0; // Verschiebung entlang der X-Achse
	private int offsetY = 0; // Verschiebung entlang der Y-Achse

	public GrundrissPanel(Grundriss grundriss) {
		this.raeume = grundriss.getRaeume();
		calculateOffsets();
	}

	private void calculateOffsets() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;

		for (RaumModell raum : raeume) {
			minX = Math.min(minX, (int) (raum.getX() * SCALE));
			minY = Math.min(minY, (int) (raum.getY() * SCALE));
		}

		offsetX = -minX + FIXED_OFFSET;
		offsetY = -minY + FIXED_OFFSET;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Optional: Antialiasing einschalten für glattere Darstellung
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Skalierungsfaktor für die Ausgabe (Verkleinerung)
		double outputScale = 0.5; // Skaliert die Ausgabe auf 50% der ursprünglichen Größe

		// Transformation anwenden: Verkleinerung der Zeichnung
		g2d.scale(outputScale, outputScale);

		g2d.setStroke(new BasicStroke(WALL_THICKNESS)); // Setzt die Dicke der Wände (die nach der Transformation
														// kleiner wirkt)

		for (RaumModell raum : raeume) {
			// Berechne die Position und Größe des Raums mit hoher interner Skalierung
			int raumX = offsetX + (int) Math.round(raum.getX() * SCALE);
			int raumY = offsetY + (int) Math.round(raum.getY() * SCALE);
			int raumLaenge = (int) Math.round(raum.getLaenge() * SCALE);
			int raumBreite = (int) Math.round(raum.getBreite() * SCALE);

			// Zeichne die Außenwände des Raumes
			g2d.setColor(Color.BLACK);
			g2d.drawRect(raumX + WALL_THICKNESS / 2, raumY + WALL_THICKNESS / 2, raumLaenge, raumBreite);

			// Fülle das Innere des Raumes
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(raumX + WALL_THICKNESS, raumY + WALL_THICKNESS, raumLaenge - WALL_THICKNESS,
					raumBreite - WALL_THICKNESS);

			// Zeichne die Möbel im Raum
			if (raum.getMoebel() != null) {
				for (Moebelstueck moebel : raum.getMoebel()) {
//					if (moebel instanceof Sofa) {
//						Sofa sofa = (Sofa) moebel;
//						sofa.draw(g2d, offsetX + (int) ((sofa.getX()) * SCALE), offsetY + (int) ((sofa.getY()) * SCALE),
//								(int) (sofa.getBreite() * SCALE), (int) (sofa.getLaenge() * SCALE));
//					}else {
					int moebelX = offsetX + (int) ((moebel.getX()) * SCALE);
					int moebelY = offsetY + (int) ((moebel.getY()) * SCALE);
					int moebelLaenge = (int) (moebel.getBreite() * SCALE);
					int moebelBreite = (int) (moebel.getLaenge() * SCALE);

					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(moebelX, moebelY, moebelBreite, moebelLaenge);
					g.setColor(Color.BLACK);
					g.drawRect(moebelX, moebelY, moebelBreite, moebelLaenge);
					g.drawString(moebel.getClass().getSimpleName(), moebelX + 5, moebelY + 15);
				}
			}
//			}

			// Zeichne die Fenster im Raum (Fenster werden über die Wände gezeichnet)
			for (Fenster fenster : raum.getFenster()) {
				int fensterX = offsetX + (int) ((fenster.getX()) * SCALE);
				int fensterY = offsetY + (int) ((fenster.getY()) * SCALE);
				int fensterBreite = (int) (fenster.getBreite() * SCALE);
				int fensterHoehe = WALL_THICKNESS;

				if (fenster.isHorizontal()) {
					// Zeichne das Fenster horizontal entlang der X-Achse
					g2d.setColor(Color.BLUE);
					g2d.fillRect(fensterX, fensterY, fensterBreite, fensterHoehe);
				} else {
					// Zeichne das Fenster vertikal entlang der Y-Achse
					g2d.setColor(Color.BLUE);
					g2d.fillRect(fensterX, fensterY, fensterHoehe, fensterBreite);
				}

				// Zeichne die Türen im Raum
				for (TuerModell tuer : raum.getTueren()) {
					int tuerX = offsetX + (int) ((tuer.getX()) * SCALE);
					int tuerY = offsetY + (int) ((tuer.getY()) * SCALE);
					int tuerBreite = (int) (tuer.getBreite() * SCALE);
					int tuerHoehe = WALL_THICKNESS;

					if (!tuer.isHorizontal()) {
						// Vertikale Tür (nach oben)
						tuerHoehe = tuerBreite;
						tuerBreite = WALL_THICKNESS;
					}

					g.setColor(Color.RED);
					g.fillRect(tuerX, tuerY, tuerBreite, tuerHoehe);

				}

			}

			int centerX = raumX + raumLaenge / 2;
			int centerY = raumY + raumBreite / 2;

			// Text für den Namen des Raumes und die Größe in m²
			String raumText = raum.getName() + " - " + (raum.getLaenge() * raum.getBreite()) + " m²";

			// Bestimme die Breite des Textes, um ihn zu zentrieren
			int textWidth = g2d.getFontMetrics().stringWidth(raumText);
			int textHeight = g2d.getFontMetrics().getHeight();

			// Zeichne den Text zentriert im Raum
			g2d.setColor(Color.WHITE);
			g2d.drawString(raumText, centerX - textWidth / 2, centerY + textHeight / 4);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		int maxX = 0;
		int maxY = 0;

		for (RaumModell raum : raeume) {
			int raumRechteckWidth = offsetX + (int) ((raum.getX() + raum.getLaenge()) * SCALE);
			int raumRechteckHeight = offsetY + (int) ((raum.getY() + raum.getBreite()) * SCALE);
			maxX = Math.max(maxX, raumRechteckWidth);
			maxY = Math.max(maxY, raumRechteckHeight);
		}

		return new Dimension(maxX + WALL_THICKNESS, maxY + WALL_THICKNESS);
	}
}
