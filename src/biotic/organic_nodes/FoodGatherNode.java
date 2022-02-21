package biotic.organic_nodes;

import biotic.Food;
import biotic.FoodProducer;
import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.awt.Color;

public class FoodGatherNode extends DirectionalNode {

	public FoodGatherNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public FoodGatherNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(grid, root, linkedNode, x, y, energy);
		this.mass = 2.0;
		this.radius = 5.0;
		this.color = new Color(95,255,95);
		this.hunger = 0.000025;
		this.resistance = 1.0;
		this.impulseStrength = 0.035;
	}

	public boolean isValidTarget(Circle other){
		return other instanceof Food || other instanceof FoodProducer;
	}
}
