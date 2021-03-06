package biotic.organic_nodes;

import biotic.particles.Food;
import physics.Circle;
import physics.Node;

import java.awt.Color;

/**
 * Absorbs energy from green food particles
 */
public class EatingNode extends EnergyAbsorbingNode {
	private static final double ENERGY_PER_MASS = 2.8;

	public EatingNode() {
		this(null, 0, 0, 1.0);
	}

	public EatingNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.radius = 6.0;
		this.mass = 3.0;
		this.color = new Color(45, 195, 255);
		this.maxEnergy = 2.3;
		this.hunger = 0.004;
		this.resistance = 0.5;
	}

	/**
	 * Overrides circle method to detect food collisions
	 * @param other the circle collided with
	 */
	@Override
	public void collidedWith(Circle other) {
		super.collidedWith(other);
		if (other instanceof Food && !(other.isDeletable())) {
			other.setDeletable(true);
			double gainedEnergy = other.getMass() * ENERGY_PER_MASS;

			// Save excess energy in case adjacent nodes can store it
			if (getEnergy() + gainedEnergy > getMaxEnergy()) {
				setExcess(Math.min(MAX_EXCESS, getExcess() + gainedEnergy));
				setEnergy(getMaxEnergy());
			} else {
				setEnergy(getEnergy() + gainedEnergy);
			}
		}
	}
}
