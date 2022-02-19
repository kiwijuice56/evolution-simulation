package physics;

import java.awt.*;

/**
 * Base collision object
 */
public class Circle {
	private double x, y;
	private double vX, vY;
	protected double radius;
	protected double mass;
	protected boolean isSolid = true;
	protected Color color;
	private boolean deletable = false;

	public Circle() {
		this(0, 0);
	}

	public Circle(double x, double y) {
		this.x = x;
		this.y = y;
		this.radius = 2.0;
		this.mass = 1.0;

		this.color = Color.WHITE;
	}

	/**
	 * Checks if colliding with other circle
	 * @param other the other circle
	 * @return true if colliding, false otherwise
	 */
	public boolean isColliding(Circle other) {
		return distanceTo(other) < (other.getRadius() + getRadius());
	}

	public void collidedWith(Circle other) {

	}

	public void delete() {
		deletable = true;
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

	public void collisionStep() {

	}

	/**
	 * Called when this circle did not collide with anything during a collision step
	 */
	public void isUndetectable() {
		isSolid = true;
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

	public Color getColor() {
		return color;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public boolean isSolid() {
		return isSolid;
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}
