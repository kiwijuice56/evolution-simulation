package biotic.organic_nodes;

import biotic.Food;
import physics.Circle;
import physics.Node;

import java.awt.*;

public class EatingNode extends OrganicNode {
	public EatingNode() {
	}

	public EatingNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.radius = 6.0;
		this.mass = 3.0;
		this.color = new Color(200, 70, 125);
	}

	@Override
	public void collidedWith(Circle other) {
		if (other instanceof Food && !(other.isDeletable())) {
			other.delete();
			if (getEnergy() < getMaxEnergy())
			setEnergy(getEnergy() + 0.3);
		}
	}
}
