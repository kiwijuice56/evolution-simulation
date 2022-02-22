package biotic.organic_nodes;

import physics.Node;

import java.awt.Color;

/**
 * Rotates randomly in all directions
 */
public class RotationalNode extends OrganicNode {
	private final static double MAX_ANGLE_CHANGE = 0.02;
	private final static double IMPULSE_STRENGTH = 0.02;
	private double angle;

	public RotationalNode() {
		this(null, 0, 0, 1.0);
	}

	public RotationalNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.radius = 5.0;
		this.mass = 3.0;
		this.color = new Color(175, 90, 125);
		this.hunger = 0.00045;
		this.resistance = 1.0;
		angle = Math.random() * Math.PI;
	}

	/**
	 * Changes angle variable to continuously rotate over time
	 */
	@Override
	public void collisionStep() {
		angle = (angle + Math.random()/50) % (2*Math.PI);
		setvX(getvX() + Math.cos(angle) * IMPULSE_STRENGTH);
		setvY(getvY() + Math.sin(angle) * IMPULSE_STRENGTH);
	}

}
