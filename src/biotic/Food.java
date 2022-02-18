package biotic;

import biotic.organic_nodes.EatingNode;
import physics.Circle;

import java.awt.*;

public class Food extends Circle {
	public Food() {
		this(0, 0);
	}

	public Food(double x, double y) {
		super(x, y);
		this.radius = Math.round(Math.random() * 2 + 3);
		this.mass = radius;
		this.color = new Color((int)(Math.random()*50) + 30, (int)(Math.random()*100) + 100 , 70);

	}

}
