package biotic.organic_nodes;

import physics.Node;

import java.awt.*;

public class RotationalNode extends OrganicNode {
	private double accelX;
	private double accelY;
	private double turningPoint;

	public RotationalNode() {
		this(null, 0, 0, 1.0);
	}

	public RotationalNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.hunger = 0.0003;
		this.color = new Color(255, 77, 95);
		this.radius = 5.0;
		this.mass = 3.0;
		turningPoint = Math.random() * Math.PI;
	}

	public void collisionStep() {
		turningPoint = (turningPoint + Math.random()/50) % (2*Math.PI);
		accelX = Math.cos(turningPoint);
		accelY = Math.sin(turningPoint);
		setvX(getvX() + accelX/25);
		setvY(getvY() + accelY/25);
	}

}
