package physics;

import java.awt.*;

/**
 * Base collision object
 */
public class Circle {
	private double x, y;
	private double radius;
	private double vX, vY;
	private double mass;
	private Color color;

	public Circle() {
		this(0, 0, 1, 1, Color.WHITE);
	}

	public Circle(double x, double y, double radius, double mass) {
		this(x, y, radius, mass, Color.WHITE);
	}

	public Circle(double x, double y, double radius, double mass, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.mass = mass;
		this.color = color;
	}

	/**
	 * Checks if colliding with other circle
	 * @param other the other circle
	 * @return true if colliding, false otherwise
	 */
	public boolean isColliding(Circle other) {
		return distanceTo(other) < (other.getRadius() + getRadius());
	}

	/**
	 * Returns the distance to another circle
	 * @param other the other circle
	 * @return the distance from the center of this circle to the other
	 */
	public double distanceTo(Circle other) {
		return Math.sqrt(Math.pow(other.getX() - getX(), 2) + Math.pow(other.getY() - getY(), 2));
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(
				(int)Math.round(getX()-getRadius()),
				(int)Math.round(getY()-getRadius()),
				(int)Math.round(getRadius()*2),
				(int)Math.round(getRadius()*2));
	}
	/* * * * * * * * * * * * * * * * * * * * * */
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getvX() {
		return vX;
	}

	public void setvX(double vX) {
		this.vX = vX;
	}

	public double getvY() {
		return vY;
	}

	public void setvY(double vY) {
		this.vY = vY;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	/* * * * * * * * * * * * * * * * * * * * * */
}
