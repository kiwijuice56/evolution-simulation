package biotic;

import physics.Circle;

import java.awt.Color;

public class Virus extends Circle {
	private static final double MASS_MIN = 0.05;
	private static final double MASS_MAX = 0.5;
	private static final double RADIUS_MIN = 2.0;
	private static final double RADIUS_MAX = 4.0;

	public Virus() {
		this(0, 0);
	}

	public Virus(double x, double y) {
		super(x, y);
		this.mass = Math.random() * (MASS_MAX-MASS_MIN) + MASS_MIN;
		this.radius = Math.round(Math.random() * (RADIUS_MAX-RADIUS_MIN) + RADIUS_MIN);
		this.color = new Color((int)(Math.random()*120) + 30, (int)(Math.random()*15) + 5 , 25);
	}

}
