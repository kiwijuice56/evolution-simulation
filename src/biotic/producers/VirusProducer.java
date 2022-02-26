package biotic.producers;

import biotic.particles.Virus;
import simulation.Simulation;

import java.awt.Color;

/**
 * Spawns new food circle surrounding the exterior of this circle
 */
public class VirusProducer extends Producer {
	private static final double MASS_MIN = 3;
	private static final double MASS_MAX = 6;
	private static final double RADIUS_MIN = 8;
	private static final double RADIUS_MAX = 16;
	private static final double VIRUS_SPEED = 2.8;
	private static final double VIRUS_CHANCE = 0.006;

	public VirusProducer(double x, double y, Simulation sim) {
		super(x, y, sim);
		this.mass = Math.random() * (MASS_MAX-MASS_MIN) + MASS_MIN;
		this.radius = Math.round(Math.random() * (RADIUS_MAX-RADIUS_MIN) + RADIUS_MIN);
		this.color = new Color(200, 15, 15);
	}

	/**
	 * Overrides producer method to create virus circles
	 */
	@Override
	public void produce() {
		if (VIRUS_CHANCE > Math.random()) {
			double angle = Math.random() * Math.PI * 2;
			Virus v = new Virus(getX() + Math.cos(angle) * (4+getRadius()), getY() + Math.sin(angle) * (4+getRadius()));
			v.setvX(getvX() + Math.cos(angle) * VIRUS_SPEED);
			v.setvY(getvY() + Math.sin(angle) * VIRUS_SPEED);
			getSim().addProduct(v);
		}
	}
}
