package physics;

import biotic.Food;
import simulation.Simulation;

import java.awt.*;

public class FoodProducer extends Circle {
	private final static double foodChance = 0.015;
	private final Simulation sim;

	public FoodProducer(double x, double y, Simulation sim) {
		super(x, y);
		this.radius = (Math.random() * 32) + 16;
		this.mass = 500.0;
		this.color = new Color(128, 200, 70);
		this.sim = sim;
	}

	public void simulationStep() {
		if (foodChance > Math.random()) {
			double angle = Math.random() * Math.PI * 2;
			Food f = new Food(getX() + Math.cos(angle) * (5+getRadius()), getY() + Math.sin(angle) * (5+getRadius()));
			f.setvX(getvX() + Math.cos(angle)/2);
			f.setvY(getvY() + Math.sin(angle)/2);
			sim.addFood(f);
		}
	}
}
