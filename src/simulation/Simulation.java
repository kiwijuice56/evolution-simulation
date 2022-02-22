package simulation;

import biotic.producers.Producer;
import biotic.producers.VirusProducer;
import biotic.organic_nodes.*;
import biotic.producers.FoodProducer;
import physics.Circle;
import physics.CollisionGrid;

import java.util.*;

/**
 * Step through the biological simulation steps for OrganicNodes and Producers
 */
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

	/**
	 * Step through one frame for all OrganicNodes and Producers
	 */
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

	/**
	 * Adds an OrganicNode to a list of nodes to add at the end of the frame
	 * @param node the node to add
	 * @return true if the addition was successful, false if the node was already within the addition list
	 */
	public boolean addOrganicNode(OrganicNode node) {
		if (toAdd.contains(node))
			return false;
		toAdd.add(node);
		return true;
	}

	/* * * * * * * * * * * * * * * * * * * * * */

	public void addProduct(Circle c) {
		grid.addCircle(c);
	}

	public int getNodeCount() {
		return organicNodes.size();
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}
