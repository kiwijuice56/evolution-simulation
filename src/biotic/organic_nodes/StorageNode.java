package biotic.organic_nodes;

import physics.Node;

import java.awt.*;

public class StorageNode extends OrganicNode {
	private final static double MAX_RADIUS = 7.9;

	public StorageNode() {
		this(null, 0, 0, 1.0);
	}

	public StorageNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.color = new Color(255,200,0);
		this.radius = getRadius();
		this.maxEnergy = 3.0;
		this.hunger = 0.00005;
		this.resistance = 0.75;
	}

	@Override
	public double getRadius() {
		return Math.max(1.0, (getEnergy() / getMaxEnergy()) * MAX_RADIUS);
	}
}
