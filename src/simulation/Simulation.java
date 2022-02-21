package simulation;

import biotic.Food;
import biotic.Producer;
import biotic.VirusProducer;
import biotic.organic_nodes.*;
import biotic.FoodProducer;
import physics.Circle;
import physics.CollisionGrid;

import java.util.*;

public class Simulation {

	private final CollisionGrid grid;

	private final Set<OrganicNode> organicNodes;
	private final Set<Producer> producers;
	private final Set<OrganicNode> toAdd;

	public Simulation(CollisionGrid grid)  {
		this.grid = grid;
		organicNodes = new HashSet<>();
		toAdd = new HashSet<>();
		producers = new HashSet<>();

		//ReproductiveNode x = new ReproductiveNode(null, 300, 300, 5.0,
		//		new ArrayList<>(Arrays.asList("eat 0", "jit 1", "sto 1", "rot 2", "nod 3", "nod 3 5", "nod 3 6", "nod 4", "nod 4 8", "nod 4 9")), this);
		initializeFood(grid);
	}

	public void stepSimulation() {
		for (OrganicNode node: organicNodes) {
			if (!node.lifeStep())
				node.setDeletable(true);
		}
		for (Producer f : producers)
			f.produce();

		organicNodes.addAll(toAdd);
		for (OrganicNode node : toAdd)
			grid.addCircle(node);
		toAdd.clear();
		organicNodes.removeIf(OrganicNode::isDeletable);
	}

	public void addProduct(Circle c) {
		grid.addCircle(c);
	}

	public boolean addOrganicNode(OrganicNode node) {
		if (toAdd.contains(node))
			return false;
		toAdd.add(node);
		return true;
	}

	private void initializeFood(CollisionGrid grid) {
		for (int i = 0; i < 11; i++) {
			FoodProducer f = new FoodProducer(Math.random() * grid.getWidth(), Math.random() * grid.getHeight(), this);
			grid.addCircle(f);
			producers.add(f);
		}
		for (int i = 0; i < 2; i++) {
			VirusProducer f = new VirusProducer(Math.random() * grid.getWidth(), Math.random() * grid.getHeight(), this);
			grid.addCircle(f);
			producers.add(f);
		}
	}

	public int getNodeCount() {
		return organicNodes.size();
	}
}
