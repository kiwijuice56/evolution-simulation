import biotic.Food;
import biotic.organic_nodes.EatingNode;
import graphic_interface.ApplicationFrame;
import biotic.organic_nodes.JitterNode;
import biotic.organic_nodes.OrganicNode;
import physics.Node;
import physics.CollisionGrid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Simulation {
	private static final int MIN_MILLIS_PER_FRAME = 5;
	private final Set<OrganicNode> organicNodes;

	public Simulation(CollisionGrid grid, ApplicationFrame frame) {
		organicNodes = new HashSet<>();
		createOrganism(grid, 100, 100, 3, 3);
		createOrganism(grid, 500, 500, 2, 2);
		createOrganism(grid, 250, 300, 1, 1);
		initializeFood(grid);
		// Run collision steps in infinite loop
		while (true) {
			long start = System.currentTimeMillis();

			grid.stepCollision();
			List<OrganicNode> toKill = new ArrayList<>();
			for (OrganicNode node: organicNodes){
				boolean isAlive = node.stepLife();
				if (!isAlive) {
					node.delete();
					toKill.add(node);
				}
			}
			for (OrganicNode node : toKill)
				organicNodes.remove(node);
			frame.repaint();

			long end = System.currentTimeMillis();
			//System.out.println(end-start);
			try {
				TimeUnit.MILLISECONDS.sleep(Math.max(0, MIN_MILLIS_PER_FRAME - (end-start)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void initializeFood(CollisionGrid grid) {
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 40; j++) {
				Food f = new Food((j*29) + (15*Math.random()) - 7.5, (i*29)  + (10*Math.random()) - 5);
				grid.addCircle(f);
			}
		}
	}

	// Temporary testing method to demonstrate features
	private void createOrganism(CollisionGrid grid, int x, int y, int width, int height) {
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				OrganicNode node = new EatingNode(null, x + (i*5), y + (j*5), 1);
				organicNodes.add(node);
				grid.addCircle(node);
				for (Node other : nodes) {

					node.getConnections().add(other);
					other.getConnections().add(node);
				}
				nodes.add(node);

			}
		}
		OrganicNode jitter = new JitterNode(nodes.get(nodes.size()-1), x-15, y, 1);
		organicNodes.add(jitter);
		grid.addCircle(jitter);
	}

	private void createSnake(CollisionGrid grid, int x, int y, int width) {
		OrganicNode last = null;
		for (int i = 0; i < width; i++) {
			OrganicNode node = new OrganicNode(last, x + (i*7), y, 1);
			organicNodes.add(node);
			grid.addCircle(node);
			last = node;
		}
		OrganicNode jitter = new JitterNode(last, x-15, y, 1);
		grid.addCircle(jitter);
		organicNodes.add(jitter);
	}
}
