package biotic.organic_nodes;

import physics.Circle;
import physics.Node;

import java.awt.Color;

public class PredationNode extends EnergyAbsorbingNode {
	private static final double ENERGY_LOSS = 1.25;

	private final ReproductiveNode root;

	public PredationNode(ReproductiveNode root) {
		this(null, root, 0, 0, 1.0);
	}

	public PredationNode(Node linkedNode, ReproductiveNode root, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.radius = 8.0;
		this.color = new Color(255, 0, 25);
		this.hunger = 0.00015;
		this.maxEnergy = 3.0;
		this.root = root;
	}

	/**
	 * Takes energy from collided circle only if not a part of the same organism
	 * @param other the circle collided with
	 */
	@Override
	public void collidedWith(Circle other) {
		super.collidedWith(other);
		if (solid && other.isSolid() && other instanceof OrganicNode otherOrganic && !root.getOrganism().contains(other)) {
			double stolenEnergy = otherOrganic.getEnergy() * (1.0 / otherOrganic.getResistance());
			setEnergy(getEnergy() + stolenEnergy/ENERGY_LOSS);
			otherOrganic.setEnergy(otherOrganic.getEnergy() - stolenEnergy);
		}
	}
}
