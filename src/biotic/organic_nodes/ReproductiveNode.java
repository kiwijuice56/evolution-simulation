package biotic.organic_nodes;

import physics.CollisionGrid;
import physics.Node;
import simulation.Simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReproductiveNode extends OrganicNode {
	private final Simulation sim;

	private List<String> code;
	private List<String> permCode;
	private List<OrganicNode> organism;

	public ReproductiveNode(Node linkedNode, double x, double y, double energy, List<String> code, Simulation sim) {
		super(linkedNode, x, y, energy);
		this.sim = sim;

		this.permCode = new ArrayList<>(code);
		this.code = new ArrayList<>(code);
		Collections.reverse(this.code);

		this.organism = new ArrayList<>();
		organism.add(this);

		this.hunger = 0.00015;
		this.maxEnergy = 5.0;
		this.color = new Color(255,200,0);
	}

	@Override
	public boolean stepLife() {
		boolean isAlive = super.stepLife();
		if (isAlive && code.size() > 0 && getEnergy()*3 >= maxEnergy) {
			createNode(code.remove(code.size()-1));
		}
		return isAlive;
	}

	private void createNode(String instruction) {
		String type = instruction.substring(0, instruction.indexOf(' '));
		int idx = Integer.parseInt(instruction.substring(instruction.indexOf(' ') + 1));
		OrganicNode node = switch (type) {
			case "jit" -> new JitterNode();
			case "rot" -> new RotationalNode();
			case "eat" -> new EatingNode();
			default -> new OrganicNode();
		};
		node.setEnergy(0.0);
		if (idx == 0) {
			node.setX(getX());
			node.setY(getY());
			connect(node);
		} else {
			node.setX(organism.get(idx).getX());
			node.setY(organism.get(idx).getY());
			organism.get(idx).connect(node);
		}
		organism.add(node);
		sim.addOrganicNode(node);
	}

}
