package model;

public class Fenster {

	private double x;
	private double y;
	private double breite;
	private boolean horizontal;
	
	public Fenster(double x, double y, double breite) {
		this.x = x;
		this.y = y;
		this.breite = breite;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getBreite() {
		return breite;
	}
	
	public boolean isHorizontal() {
		return horizontal;
	}
}
