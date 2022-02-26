package biotic.organic_nodes;

import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.awt.Color;

/**
 * Avoids nodes of other organisms
 */
public class RunningNode extends DirectionalNode {
	public RunningNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public RunningNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(grid, root, linkedNode, x, y, energy);
		this.mass = 2.0;
		this.radius = 5.0;
		this.color = new Color(125,25,215);
		this.hunger = 0.0004;
		this.resistance = 1.0;
		this.impulseStrength = -0.28;
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
