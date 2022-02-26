package biotic.organic_nodes;

import physics.Node;

import java.awt.Color;

/**
 * Moves randomly in all directions
 */
public class JitterNode extends OrganicNode {
	private final static double IMPULSE_STRENGTH = 0.32;

	public JitterNode() {
		this(null, 0, 0, 1.0);
	}

	public JitterNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.color = new Color(155, 135, 255);
		this.radius = 5.0;
		this.mass = 3.0;
		this.hunger = 0.0012;
		this.resistance = 1.0;
	}

	/**
	 * Changes velocity by a random vector every frame
	 */
	@Override
	public void collisionStep() {
		setvX(getvX() + (Math.random() - 0.5) * IMPULSE_STRENGTH);
		setvY(getvY() + (Math.random() - 0.5) * IMPULSE_STRENGTH);
	}

}
