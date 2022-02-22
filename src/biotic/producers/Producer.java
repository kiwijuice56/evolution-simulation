package biotic.producers;

import physics.Circle;
import simulation.Simulation;

/**
 * Abstract class to spawn particles around this circle
 */
public abstract class Producer extends Circle {
	private final Simulation sim;

	public Producer(double x, double y, Simulation sim) {
		super(x, y);
		this.sim = sim;
	}

	/**
	 * Method in which new particles are possibly spawned
	 */
	public abstract void produce();

	/* * * * * * * * * * * * * * * * * * * * * */

	public Simulation getSim() {
		return sim;
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}
