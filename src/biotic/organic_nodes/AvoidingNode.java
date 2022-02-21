package biotic.organic_nodes;

import physics.CollisionGrid;
import physics.Node;

import java.awt.Color;

public class AvoidingNode extends DirectionalNode {
	public AvoidingNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public AvoidingNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(grid, root, linkedNode, x, y, energy);
		this.mass = 2.0;
		this.radius = 5.0;
		this.color = new Color(125,25,215);
		this.hunger = 0.000025;
		this.impulseStrength = -0.035;
	}
}
