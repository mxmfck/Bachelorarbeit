package visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import algorithm.Fenster;
import algorithm.Grundriss;
import algorithm.RaumModell;
import algorithm.TuerModell;
import model.Moebelstueck;

public class GrundrissPanel extends JPanel {
	private List<RaumModell> raeume;
	private static final int SCALE = 50; // Skalierungsfaktor für die Darstellung
	private static final int FIXED_OFFSET = 10; // Feste Verschiebung um 25 Pixel nach rechts und unten
	private int offsetX = 0; // Verschiebung entlang der X-Achse
	private int offsetY = 0; // Verschiebung entlang der Y-Achse
	private int WALL_THICKNESS = 0; // Dicke der Wände in Pixeln

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
		offsetY = -minY - FIXED_OFFSET;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Optional: Antialiasing einschalten für glattere Darstellung
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Höhe des Panels
		int height = (int) Math.round(raeume.get(0).getBreite() * SCALE);

		// Transformation anwenden, um die y-Achse zu invertieren
		g2d.translate(0, height); // Verschiebung um die Höhe des Panels
		g2d.scale(1, -1); // Skalierung entlang der y-Achse um -1

		// Zeichnen der Räume
		for (RaumModell raum : raeume) {
			WALL_THICKNESS = (int) Math.round(raum.getWANDBREITE() * SCALE); // Dicke der Wände in Pixeln
			int wandX = offsetX + (int) Math.round(raum.getX() * SCALE);
			int wandY = offsetY + (int) Math.round(raum.getY() * SCALE);
			int wandLaenge = (int) Math.round(raum.getLaenge() * SCALE);
			int wandBreite = (int) Math.round(raum.getBreite() * SCALE);
			int raumX = offsetX + (int) Math.round(raum.getRaumX() * SCALE);
			int raumY = offsetY + (int) Math.round(raum.getRaumY() * SCALE);
			int raumLaenge = (int) Math.round(raum.getRaumLaenge() * SCALE);
			int raumBreite = (int) Math.round(raum.getRaumBreite() * SCALE);

			// Zeichne die Außenwände des Raumes
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(WALL_THICKNESS * 2)); // Setzt die Dicke der Wände
			g2d.drawRect(wandX, wandY, wandLaenge, wandBreite);

			// Fülle das Innere des Raumes
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(raumX, raumY, raumLaenge, raumBreite);

			// Zeichne die Möbel im Raum
			if (raum.getMoebel() != null) {
				for (Moebelstueck moebel : raum.getMoebel()) {
//					int moebelX = offsetX + (int) ((moebel.getX()) * SCALE);
//					int moebelY = offsetY + (int) ((moebel.getY()) * SCALE);
//					int moebelLaenge = (int) (moebel.getBreite() * SCALE);
//					int moebelBreite = (int) (moebel.getLaenge() * SCALE);
//
//					g2d.setStroke(new BasicStroke(1));
//					g2d.setColor(Color.LIGHT_GRAY);
//					g2d.fillRect(moebelX, moebelY, moebelBreite, moebelLaenge);
//					g2d.setColor(Color.BLACK);
//					g2d.drawRect(moebelX, moebelY, moebelBreite, moebelLaenge);
//					g2d.drawString(moebel.getClass().getSimpleName(), moebelX + 5, moebelY + 15);

					try {
						moebel.draw(g2d, raum, offsetX, offsetY, SCALE);
					} catch (Exception e) {
						System.out.println();
					}
				}
			}

			// Zeichne die Fenster im Raum (Fenster werden über die Wände gezeichnet)
			for (Fenster fenster : raum.getFenster()) {
				int fensterX = offsetX + (int) ((fenster.getX()) * SCALE);
				int fensterY = offsetY + (int) ((fenster.getY()) * SCALE);
				int fensterBreite = (int) (fenster.getBreite() * SCALE);
				int fensterHoehe = WALL_THICKNESS * 2;
//				int fensterHoehe = 1;

				g2d.setStroke(new BasicStroke(fensterHoehe)); // Setzt die Strichstärke für Fenster auf 1 Pixel
				if (fenster.isHorizontal()) {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(fensterX + WALL_THICKNESS, fensterY, fensterX + fensterBreite - WALL_THICKNESS,
							fensterY);
//					// Test
//					g2d.setStroke(new BasicStroke(1)); // Setzt die Strichstärke für Türen auf 1 Pixel
//					g2d.setColor(Color.RED);
//					g2d.drawLine(fensterX, fensterY, fensterX + fensterBreite, fensterY);
				} else {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(fensterX, fensterY + WALL_THICKNESS, fensterX,
							fensterY + fensterBreite - WALL_THICKNESS);
				}
			}

//			int centerX = wandX + wandLaenge / 2;
//			int centerY = wandY + wandBreite / 2;

			// Temporäre Transformation für normalen Text
			AffineTransform originalTransform = g2d.getTransform();

			// Rückgängig machen der Invertierung nur für den Text
			g2d.scale(1, -1);
			g2d.translate(0, -height);

			// Berechne den Mittelpunkt des Raums
			int centerX = raumX + raumLaenge / 2;
			int centerY = raumY + raumBreite / 2;

			// Text für den Namen des Raumes und die Größe in m²
			String raumText = raum.getName() + "\n" + (raum.getRaumLaenge() * raum.getRaumBreite()) + " m²";

			// Bestimme die Breite des Textes, um ihn zu zentrieren
			int textWidth = g2d.getFontMetrics().stringWidth(raumText);
			int textHeight = g2d.getFontMetrics().getHeight();

			// Versatz für die Umrandung
			int outlineOffset = 1;

			// Zeichne die Umrandung (schwarzer Text leicht versetzt in alle Richtungen)
			g2d.setColor(Color.BLACK);
			g2d.drawString(raumText, centerX - textWidth / 2 - outlineOffset, height - (centerY + textHeight / 4 - outlineOffset));
			g2d.drawString(raumText, centerX - textWidth / 2 + outlineOffset, height - (centerY + textHeight / 4 - outlineOffset));
			g2d.drawString(raumText, centerX - textWidth / 2 - outlineOffset, height - (centerY + textHeight / 4 + outlineOffset));
			g2d.drawString(raumText, centerX - textWidth / 2 + outlineOffset, height - (centerY + textHeight / 4 + outlineOffset));

			// Zeichne den weißen Text zentriert im Raum
			g2d.setColor(Color.WHITE);
			g2d.drawString(raumText, centerX - textWidth / 2, height - (centerY + textHeight / 4));
			// Ursprüngliche Transformation wiederherstellen
			g2d.setTransform(originalTransform);
		}

		for (RaumModell raum : raeume) {
			// Zeichne die Türen im Raum
			for (TuerModell tuer : raum.getTueren()) {
				if (tuer.getInRaum().getName().equals(raum.getName())) {
					int tuerX = offsetX + (int) ((tuer.getX()) * SCALE);
					int tuerY = offsetY + (int) ((tuer.getY()) * SCALE);
					int tuerBreite = (int) (tuer.getBreite() * SCALE);

					g2d.setStroke(new BasicStroke(WALL_THICKNESS * 2 + 1)); // Setzt die Strichstärke für Türen auf 1
																			// Pixel
					g2d.setColor(Color.LIGHT_GRAY);

					if (!tuer.isHorizontal()) {
						g2d.drawLine(tuerX, tuerY + WALL_THICKNESS, tuerX, tuerY + tuerBreite - WALL_THICKNESS);

					} else {

						g2d.drawLine(tuerX + WALL_THICKNESS, tuerY, tuerX + tuerBreite - WALL_THICKNESS, tuerY);
					}

					tuer.draw(g2d, raum, offsetX, offsetY, SCALE);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		int maxX = 0;
		int maxY = 0;

		for (RaumModell raum : raeume) {
			if (raum.getX() > raeume.get(0).getX()) {
				int raumRechteckWidth = offsetX + (int) ((raum.getX() + raum.getLaenge()) * SCALE);
				int raumRechteckHeight = offsetY + (int) ((raum.getY() + raum.getBreite()) * SCALE);
				maxX = Math.max(maxX, raumRechteckWidth);
				maxY = Math.max(maxY, raumRechteckHeight);
			} else {
//				int raumRechteckWidth = offsetX + (int) ((raum.getX() + raum.getLaenge()) * SCALE);
				int raumRechteckHeight = offsetY + (int) ((raum.getY() + raum.getBreite()) * SCALE);
//				maxX = Math.max(maxX, raumRechteckWidth);
				maxY = Math.max(maxY, raumRechteckHeight);
			}
		}
		WALL_THICKNESS = (int) Math.round(raeume.get(0).getWANDBREITE() * SCALE);

		return new Dimension(maxX + WALL_THICKNESS, maxY + 2 * WALL_THICKNESS - 2 * offsetY);
	}
}
