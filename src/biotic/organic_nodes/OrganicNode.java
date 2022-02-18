package biotic.organic_nodes;

import physics.Node;

import java.awt.*;

public class OrganicNode extends Node {
	private double energy;
	protected double hunger;
	protected double maxEnergy;

	public OrganicNode() {
		this(null, 0, 0, 1);
	}

	public OrganicNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y);
		this.hunger = 0.0001;
		this.maxEnergy = 1.0;

		this.energy = maxEnergy;
		this.color = new Color(84, 61, 255);
		this.radius = 3.0;

	}

	public boolean stepLife() {
		setEnergy(getEnergy() - getHunger());
		for (Node other : getConnections()) {
			OrganicNode otherOrganic = (OrganicNode) other;
			double averageEnergy = (otherOrganic.getEnergy() + getEnergy()) / 2.0;
			setEnergy(averageEnergy);
			otherOrganic.setEnergy(averageEnergy);
		}
		return energy > 0;
	}

	@Override
	public void delete() {
		super.delete();
		for (Node other : getConnections())
			other.getConnections().remove(this);
		getConnections().clear();
	}

	/* * * * * * * * * * * * * * * * * * * * * */
	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = Math.max(0, energy);
	}

	public double getHunger() {
		return hunger;
	}

	public double getMaxEnergy() {
		return maxEnergy;
	}

	public Color getColor() {
		double scale = Math.min(1, getEnergy() / getMaxEnergy() + .05);
		return new Color(
				(int) (super.getColor().getRed() * scale),
				(int) (super.getColor().getGreen() * scale),
				(int) (super.getColor().getBlue() * scale));
	}
	/* * * * * * * * * * * * * * * * * * * * * */
}
