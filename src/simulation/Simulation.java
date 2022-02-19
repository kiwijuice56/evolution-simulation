package simulation;

import biotic.Food;
import biotic.organic_nodes.*;
import physics.FoodProducer;
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

		addOrganicNode(new ReproductiveNode(null, 200, 200, 4.0,
				new ArrayList<>(Arrays.asList("eat 0", "rot 0", "eat 1", "eat 1 3", "eat 1 3 4", "rot 5")), this));
		addOrganicNode(new ReproductiveNode(null, 200, 500, 4.0,
				new ArrayList<>(Arrays.asList("eat 0", "rot 0", "nod 2", "nod 3", "nod 4", "nod 5", "nod 6", "nod 7", "nod 8", "nod 9", "rot 10")), this));
		addOrganicNode(new ReproductiveNode(null, 600, 600, 3.0,
				new ArrayList<>(Arrays.asList("eat 0", "jit 0")), this));
		addOrganicNode(new ReproductiveNode(null, 300, 600, 3.0,
				new ArrayList<>(Arrays.asList("eat 0", "rot 1", "nod 2", "pre 3")), this));
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

		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 50; j++) {
				Food f = new Food((j*24) + (15*Math.random()) - 7.5, (i*24)  + (10*Math.random()) - 5);
				boolean isColliding = false;
				for (OrganicNode o: organicNodes)
					if (f.isColliding(o))
						isColliding = true;
				for (FoodProducer o: foodProducers)
					if (f.isColliding(o))
						isColliding = true;
				if (isColliding)
					continue;
				grid.addCircle(f);
			}
		}
	}
}
