package main;

import model.Haus;

public class ParserTest {

	public static void main(String[] args) {
		Haus test = new Haus("Haus.txt");
		System.out.println(test.toString());
	}

}
