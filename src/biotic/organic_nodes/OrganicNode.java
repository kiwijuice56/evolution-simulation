package biotic.organic_nodes;

import physics.Node;

import java.awt.Color;

public class OrganicNode extends Node {
	private double energy;
	protected double hunger;
	protected double maxEnergy;
	protected double resistance;

	public OrganicNode() {
		this(null, 0, 0, 1);
	}

	public OrganicNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y);
		this.hunger = 0.00005;
		this.maxEnergy = 1.0;

		this.energy = energy;
		this.color = new Color(35, 55, 225);
		this.radius = 4.0;
		this.resistance = 3.0;
	}

	public boolean stepLife() {
		setEnergy(getEnergy() - getHunger());
		for (Node other : getConnections()) {
			OrganicNode otherOrganic = (OrganicNode) other;
			double averageEnergy = (otherOrganic.getEnergy() + getEnergy()) / 2.0;
			if (averageEnergy > getMaxEnergy()) {
				double excess = averageEnergy - getMaxEnergy();
				setEnergy(averageEnergy);
				otherOrganic.setEnergy(averageEnergy + excess);
			} else if (averageEnergy > otherOrganic.getMaxEnergy()) {
				double excess = averageEnergy - otherOrganic.getMaxEnergy();
				setEnergy(averageEnergy + excess);
				otherOrganic.setEnergy(averageEnergy);
			} else {
				setEnergy(averageEnergy);
				otherOrganic.setEnergy(averageEnergy);
			}

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
		this.energy = Math.min(maxEnergy, Math.max(0, energy));
	}

	public double getHunger() {
		return hunger;
	}

	public double getMaxEnergy() {
		return maxEnergy;
	}

	public double getResistance() {
		return resistance;
	}

	@Override
	public Color getColor() {
		double scale = Math.min(1, getEnergy() / getMaxEnergy() + .05);
		return new Color(
				(int) (super.getColor().getRed() * scale),
				(int) (super.getColor().getGreen() * scale),
				(int) (super.getColor().getBlue() * scale));
	}
	/* * * * * * * * * * * * * * * * * * * * * */
}
