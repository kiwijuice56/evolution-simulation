package biotic.producers;

import biotic.particles.Food;
import simulation.Simulation;

import java.awt.Color;

/**
 * Spawns new food circle surrounding the exterior of this circle
 */
public class FoodProducer extends Producer {
	private static final double MASS_MIN = 3;
	private static final double MASS_MAX = 6;
	private static final double RADIUS_MIN = 8;
	private static final double RADIUS_MAX = 16;
	private static final double FOOD_SPEED = 2.8;
	private static final double FOOD_CHANCE = 0.04;

	public FoodProducer(double x, double y, Simulation sim) {
		super(x, y, sim);
		this.mass = Math.random() * (MASS_MAX-MASS_MIN) + MASS_MIN;
		this.radius = Math.round(Math.random() * (RADIUS_MAX-RADIUS_MIN) + RADIUS_MIN);
		this.color = new Color(128, 200, 70);
	}

	/**
	 * Overrides producer method to create food circles
	 */
	@Override
	public void produce() {
		if (FOOD_CHANCE > Math.random()) {
			double angle = Math.random() * Math.PI * 2;
			Food f = new Food(getX() + Math.cos(angle) * (4+getRadius()), getY() + Math.sin(angle) * (4+getRadius()));
			f.setvX(getvX() + Math.cos(angle) * FOOD_SPEED);
			f.setvY(getvY() + Math.sin(angle) * FOOD_SPEED);
			getSim().addProduct(f);
		}
	}
}
