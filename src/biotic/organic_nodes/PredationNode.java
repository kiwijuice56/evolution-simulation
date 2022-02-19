package biotic.organic_nodes;

import physics.Circle;
import physics.Node;

import java.awt.Color;

public class PredationNode extends OrganicNode {
	private final ReproductiveNode root;

	public PredationNode(ReproductiveNode root) {
		this(null, root, 0, 0, 1.0);
	}
	public PredationNode(Node linkedNode, ReproductiveNode root, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.color = new Color(255, 0, 25);
		this.hunger = 0.0003;
		this.maxEnergy = 2.5;
		this.radius = 5.0;
		this.root = root;
	}

	@Override
	public void collidedWith(Circle other) {
		super.collidedWith(other);
		if (isSolid && other.isSolid() && other instanceof OrganicNode otherOrganic && !root.getOrganism().contains(other)) {
			double stolenEnergy = otherOrganic.getEnergy() * (1.0 / otherOrganic.getResistance());
			setEnergy(getEnergy() + stolenEnergy/10);
			otherOrganic.setEnergy(otherOrganic.getEnergy() - stolenEnergy);
		}
	}
}
