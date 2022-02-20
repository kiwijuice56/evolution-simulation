package simulation;

import biotic.Food;
import biotic.organic_nodes.*;
import biotic.FoodProducer;
import physics.CollisionGrid;

import java.util.*;

public class Simulation {

	private final CollisionGrid grid;

	private final Set<OrganicNode> organicNodes;
	private final Set<FoodProducer> foodProducers;
	private final Set<OrganicNode> toAdd;

	public Simulation(CollisionGrid grid)  {
		this.grid = grid;
		organicNodes = new HashSet<>();
		toAdd = new HashSet<>();
		foodProducers = new HashSet<>();

		ReproductiveNode x = new ReproductiveNode(null, 300, 300, 5.0,
				new ArrayList<>(Arrays.asList("eat 0", "jit 1", "rot 2", "rot 2", "nod 3", "nod 3 5", "nod 3 6", "nod 4", "nod 4 8", "nod 4 9")), this);
		addOrganicNode(x);
		initializeFood(grid);
	}

	public void stepSimulation() {
		for (OrganicNode node: organicNodes) {
			if (!node.stepLife())
				node.delete();
		}
		for (FoodProducer f : foodProducers) {
			f.simulationStep();
		}

		organicNodes.addAll(toAdd);
		toAdd.clear();
		organicNodes.removeIf(OrganicNode::isDeletable);
	}

	public boolean addFood(Food f) {
		if (toAdd.contains(f))
			return false;
		grid.addCircle(f);
		return true;
	}

	public boolean addOrganicNode(OrganicNode node) {
		if (toAdd.contains(node))
			return false;
		toAdd.add(node);
		grid.addCircle(node);
		return true;
	}

	private void initializeFood(CollisionGrid grid) {
		for (int i = 0; i < 10; i++) {
			FoodProducer f = new FoodProducer(Math.random() * grid.getWidth(), Math.random() * grid.getHeight(), this);
			grid.addCircle(f);
			foodProducers.add(f);
		}
	}
}
