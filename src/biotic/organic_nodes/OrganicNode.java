package biotic.organic_nodes;

import biotic.Food;
import biotic.Virus;
import physics.Circle;
import physics.Node;

import java.awt.Color;

public class OrganicNode extends Node {
	private double energy;
	protected double hunger;
	protected double maxEnergy;
	protected double resistance;

	private double excess;

	public OrganicNode() {
		this(null, 0, 0, 1);
	}

	public OrganicNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y);
		this.hunger = 0.00001;
		this.maxEnergy = 1.0;
		this.energy = energy;
		this.color = new Color(35, 55, 225);
		this.radius = 5.0;
		this.resistance = 3.0;
	}

	/**
	 * Called every frame by the Simulation, handles hunger and energy
	 * @return whether this organism is still alive
	 */
	public boolean lifeStep() {
		setEnergy(getEnergy() - getHunger());
		shareEnergy();
		return energy > 0;
	}

	@Override
	public void setDeletable(boolean deletable) {
		super.setDeletable(deletable);
		if (deletable) {
			for (Node other : getConnections())
				other.getConnections().remove(this);
			getConnections().clear();
		}
	}

	public void shareEnergy() {
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
	}

	@Override
	public void collidedWith(Circle other) {
		if (other instanceof Virus && !(other.isDeletable())) {
			other.setDeletable(true);
			double stolenEnergy = getEnergy() * (1.0 / getResistance());
			setEnergy(Math.max(0.0001, getEnergy() - stolenEnergy));
		}
	}

	/* * * * * * * * * * * * * * * * * * * * * */
	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = Math.min(maxEnergy, energy);
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
