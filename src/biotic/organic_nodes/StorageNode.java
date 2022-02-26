package biotic.organic_nodes;

import physics.Node;

import java.awt.Color;

/**
 * Changes radius with energy and has a large maximum energy
 */
public class StorageNode extends OrganicNode {
	private final static double MAX_RADIUS = 13.0;

	public StorageNode() {
		this(null, 0, 0, 1.0);
	}

	public StorageNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.color = new Color(255,200,0);
		this.radius = getRadius();
		this.maxEnergy = 8.0;
		this.hunger = 0.00004;
		this.resistance = 1.25;
	}

	/**
	 * Multiplies radius by the amount of energy
	 * @return radius * energy/maxEnergy
	 */
	@Override
	public double getRadius() {
		return Math.max(2.0, (getEnergy() / getMaxEnergy()) * MAX_RADIUS);
	}
}
