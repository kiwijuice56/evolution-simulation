package biotic.organic_nodes;

import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.awt.Color;

/**
 * Follows nodes of other organisms
 */
public class FollowingNode extends DirectionalNode {

	public FollowingNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public FollowingNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(grid, root, linkedNode, x, y, energy);
		this.mass = 2.0;
		this.radius = 5.0;
		this.color = new Color(255,75,75);
		this.hunger = 0.000025;
		this.resistance = 1.0;
		this.impulseStrength = 0.035;
	}

	/**
	 * Ensures considered circle are organic nodes
	 * @param other the circle to be considered
	 * @return whether the circle is an organic and target-able node
	 */
	public boolean isValidTarget(Circle other){
		return other instanceof OrganicNode;
	}
}
