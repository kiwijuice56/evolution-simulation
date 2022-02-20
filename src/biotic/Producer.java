package biotic;

import physics.Circle;
import simulation.Simulation;

public abstract class Producer extends Circle {
	private final Simulation sim;

	public Producer(double x, double y, Simulation sim) {
		super(x, y);
		this.sim = sim;
	}

	public abstract void produce();

	public Simulation getSim() {
		return sim;
	}
}
