package biotic.organic_nodes;

import physics.Node;

import java.awt.Color;

public class JitterNode extends OrganicNode {
	private double accelX;
	private double accelY;

	public JitterNode() {
		this(null, 0, 0, 1.0);
	}

	public JitterNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.hunger = 0.0002;
		this.color = new Color(155, 135, 255);
		this.radius = 5.0;
		this.mass = 3.0;
	}

	@Override
	public void collisionStep() {
		accelX = Math.random() - 0.5;
		accelY = Math.random() - 0.5;
		setvX(getvX() + accelX/25);
		setvY(getvY() + accelY/25);
	}

}
