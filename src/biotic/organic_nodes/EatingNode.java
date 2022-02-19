package biotic.organic_nodes;

import biotic.Food;
import physics.Circle;
import physics.Node;

import java.awt.Color;

public class EatingNode extends OrganicNode {
	public EatingNode() {
		this(null, 0, 0, 1.0);
	}

	public EatingNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.radius = 7.0;
		this.mass = 3.0;
		this.maxEnergy = 2.5;
		this.hunger = 0.0003;
		this.color = new Color(45, 195, 255);
	}

	@Override
	public void collidedWith(Circle other) {
		if (other instanceof Food && !(other.isDeletable())) {
			other.delete();
			if (getEnergy() < getMaxEnergy())
				setEnergy(getEnergy() + other.getMass()/10);
		}
	}
}
