package biotic.organic_nodes;

import biotic.particles.Food;
import biotic.producers.FoodProducer;
import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.awt.Color;

/**
 * Follows food circles
 */
public class GatheringNode extends DirectionalNode {
	public GatheringNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public GatheringNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(grid, root, linkedNode, x, y, energy);
		this.mass = 2.0;
		this.radius = 5.0;
		this.color = new Color(95,255,95);
		this.hunger = 0.0002;
		this.resistance = 1.0;
		this.impulseStrength = 0.28;
	}

	/**
	 * Ensures considered circle are organic nodes
	 * @param other the circle to be considered
	 * @return whether the circle is a food circle
	 */
	public boolean isValidTarget(Circle other){
		return other instanceof Food || other instanceof FoodProducer;
	}
}
