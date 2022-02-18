package physics;

import java.awt.*;
import java.util.Set;
import java.util.HashSet;

/**
 * Allows for connections with other nodes to bound position
 */
public class Node extends Circle {
	private final Set<Node> connections;
	private int maxLinkLength = 64;

 	public Node() {
 		this(null, 0, 0, 1, 1, Color.WHITE);
 	}

	public Node(Node linkedNode, double x, double y, double radius, double mass) {
		this(linkedNode, x, y, radius, mass, Color.WHITE);
	}

	public Node(Node linkedNode, double x, double y, double radius, double mass, Color color) {
   		super(x, y, radius, mass, color);
   		connections = new HashSet<>();
   		if (linkedNode != null) {
	   		connections.add(linkedNode);
	   		linkedNode.getConnections().add(this);	
   		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.WHITE);
		for (Node n : connections) {
			g.drawLine(
					(int)Math.round(getX()), (int)Math.round(getY()),
					(int)Math.round(n.getX()), (int)Math.round(n.getY()));
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
			if (distanceTo(n) > n.getMaxLinkLength()){
				double deform = (distanceTo(n) - n.getMaxLinkLength())/2;
				double dir = -Math.signum(getX() - n.getX());
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
			if (distanceTo(n) > n.getMaxLinkLength()){
				double deform = (distanceTo(n) - n.getMaxLinkLength())/2;
				double dir = -Math.signum(getY() - n.getY());
				setvY(getvY() + dir * deform/getMass());
				super.setY(getY() + deform*dir);
			}
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * */
	public Set<Node> getConnections() {
		return connections;
	}

	public int getMaxLinkLength() {
		return maxLinkLength;
	}

	public void setMaxLinkLength(int maxLinkLength) {
		this.maxLinkLength = maxLinkLength;
	}
	/* * * * * * * * * * * * * * * * * * * * * */
}