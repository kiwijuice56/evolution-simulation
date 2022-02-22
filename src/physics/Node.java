package physics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;
import java.util.HashSet;

/**
 * Allows for connections with other nodes to bound position
 */
public class Node extends Circle {
	private final static double MAX_DEFORMATION = 1.0;
	private final static double MAX_LINK_LENGTH = 19.0;

	private final Set<Node> connections;

 	public Node() {
 		this(null, 0, 0);
 	}

	public Node(Node linkedNode, double x, double y) {
		super(x, y);
		connections = new HashSet<>();
		if (linkedNode != null)
	   		connect(linkedNode);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		super.draw(g, x, y);
		g.setColor(Color.GRAY);
		for (Node n : connections) {
			g.drawLine((int)Math.round(getX()) + x, (int)Math.round(getY()) + y,
					(int)Math.round(n.getX()) + x, (int)Math.round(n.getY()) + y);
		}

	}

	/**
	 * Override Circle setX() to force the node to stay in range of other nodes
	 * @param x
	 */
	@Override
	public void setX(double x) {
		super.setX(x);
		for (Node n : connections) {
			if (distanceTo(n) > MAX_LINK_LENGTH) {
				double deform = Math.min(MAX_DEFORMATION, (distanceTo(n) - MAX_LINK_LENGTH)/2);
				double dir = -Math.signum(getX() - n.getX());
				if (dir == 0)
					return;
				setvX(getvX() + dir * deform/getMass());
				super.setX(getX() + deform*dir);
			}
		}
	}

	/**
	 * Override Circle setX() to force the node to stay in range of other nodes
	 * @param y
	 */
	@Override
	public void setY(double y) {
		super.setY(y);
		for (Node n : connections) {
			if (distanceTo(n) > MAX_LINK_LENGTH){
				double deform = Math.min(MAX_DEFORMATION, (distanceTo(n) - MAX_LINK_LENGTH)/2);
				double dir = -Math.signum(getY() - n.getY());
				setvY(getvY() + dir * deform/getMass());
				super.setY(getY() + deform*dir);
			}
		}
	}

	public void connect(Node node) {
		node.getConnections().add(this);
		getConnections().add(node);
	}

	/* * * * * * * * * * * * * * * * * * * * * */

	public Set<Node> getConnections() {
		return connections;
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}