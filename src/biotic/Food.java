package biotic;

import biotic.organic_nodes.EatingNode;
import physics.Circle;

import java.awt.*;

public class Food extends Circle {
	public Food() {
	}

	public Food(double x, double y) {
		super(x, y);
		this.radius = Math.round(Math.random() * 2 + 3);
		this.mass = radius;
		this.color = new Color((int)(Math.random()*30) + 60, (int)(Math.random()*40) + 150 , 70);

	}

}
