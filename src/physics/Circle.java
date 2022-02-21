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
	protected Color color;

	protected boolean solid = false;
	private boolean deletable = false;

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

	/**
	 * Returns the distance to another circle
	 * @param other the other circle
	 * @return the distance from the center of this circle to the other
	 */
	public double distanceTo(Circle other) {
		return Math.sqrt(Math.pow(other.getX() - getX(), 2) + Math.pow(other.getY() - getY(), 2));
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(getColor());
		g.fillOval(
				(int)Math.round(getX()-getRadius()) + x,
				(int)Math.round(getY()-getRadius()) + y,
				(int)Math.round(getRadius()*2),
				(int)Math.round(getRadius()*2));
	}

	/**
	 * Called every collision step from the CollisionGrid
	 */
	public void collisionStep() { }

	/**
	 * Called every time this circle collides with another
	 * @param other the circle collided with
	 */
	public void collidedWith(Circle other) { }


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

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}
