package biotic.organic_nodes;

import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.awt.Color;

public class TrackingNode extends DirectionalNode {
	
	public TrackingNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public TrackingNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(grid, root, linkedNode, x, y, energy);
		this.mass = 2.0;
		this.radius = 5.0;
		this.color = new Color(255,75,75);
		this.hunger = 0.000025;
		this.impulseStrength = 0.035;
	}
}
