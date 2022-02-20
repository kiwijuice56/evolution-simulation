package biotic.organic_nodes;

import biotic.Food;
import physics.Circle;
import physics.Node;

import java.awt.Color;

public class EatingNode extends OrganicNode {
	private static final double ENERGY_PER_MASS = 2.5;
	private static final double MAX_EXCESS = 2.0;
	private double excess;

	public EatingNode() {
		this(null, 0, 0, 1.0);
	}

	public EatingNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);

		this.radius = 6.0;
		this.mass = 3.0;
		this.color = new Color(45, 195, 255);
		this.maxEnergy = 2.3;
		this.hunger = 0.0005;
		this.resistance = 0.5;
	}

	@Override
	public void collidedWith(Circle other) {
		super.collidedWith(other);
		if (other instanceof Food && !(other.isDeletable())) {
			other.setDeletable(true);
			double gainedEnergy = other.getMass() * ENERGY_PER_MASS;

			if (getEnergy() + gainedEnergy > getMaxEnergy()) {
				setExcess(Math.min(MAX_EXCESS, excess + gainedEnergy));
				setEnergy(getMaxEnergy());
			} else {
				setEnergy(getEnergy() + gainedEnergy);
			}
		}
	}

	@Override
	public boolean lifeStep() {
		setEnergy(getEnergy() - getHunger());
		shareEnergy();
		double returnedExcess = 0;
		for (Node other : getConnections()) {
			OrganicNode organicOther = (OrganicNode) other;
			if (organicOther.getEnergy() + getExcess()/getConnections().size() > organicOther.getMaxEnergy())
				returnedExcess += getExcess()/getConnections().size() - organicOther.getMaxEnergy();
			organicOther.setEnergy(organicOther.getEnergy() + getExcess()/getConnections().size());
		}
		setExcess(returnedExcess);
		if (getEnergy() <= 0)
			setEnergy(getEnergy() + getExcess());
		return getEnergy() > 0;
	}

	public double getExcess() {
		return excess;
	}

	public void setExcess(double excess) {
		this.excess = excess;
	}
}
