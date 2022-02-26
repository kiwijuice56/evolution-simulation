package biotic.organic_nodes;

import physics.Node;

/**
 * Abstract node that implements "excess" mechanic to consume energy and flow it into surrounding nodes
 */
public abstract class EnergyAbsorbingNode extends OrganicNode {
	protected static final double MAX_EXCESS = 2.0;
	private double excess;

	public EnergyAbsorbingNode(Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
	}

	/**
	 * Override OrganicNode method to allow flow of excess energy
	 * @return whether this node is alive
	 */
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
