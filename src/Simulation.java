import biotic.Food;
import biotic.organic_nodes.EatingNode;
import biotic.organic_nodes.JitterNode;
import graphic_interface.ApplicationFrame;
import biotic.organic_nodes.RotationalNode;
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
		createSnake(grid, 600, 500, 16);
		createMembrane(grid,  16, 600, 100);
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
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 50; j++) {
				Food f = new Food((j*24) + (15*Math.random()) - 7.5, (i*24)  + (10*Math.random()) - 5);
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
			OrganicNode node = new OrganicNode(last, x + (i*10), y, 1);
			OrganicNode n1 = new OrganicNode(node,x + (i*10), y + 10, .5);
			OrganicNode n2 = new EatingNode(node,x + (i*10), y - 10, .5);
			OrganicNode n3 = new OrganicNode(i % 2 == 0 ? n1 : n2,x + (i*10), y + (i % 2 == 0 ? -20 : 20), .5);

			organicNodes.add(node);
			grid.addCircle(node);

			organicNodes.add(n1);
			grid.addCircle(n1);
			organicNodes.add(n2);
			grid.addCircle(n2);
			organicNodes.add(n3);
			grid.addCircle(n3);

			last = node;
		}
		OrganicNode jitter = new RotationalNode(last, x-15, y, 1);
		grid.addCircle(jitter);
		organicNodes.add(jitter);
	}

	private void createMembrane(CollisionGrid grid, int nodeCnt, double x, double y) {
		RotationalNode j = new RotationalNode(null, x, y,  1);
		grid.addCircle(j);
		organicNodes.add(j);
		List<OrganicNode> nodes = new ArrayList<>();
		for (int i = 0; i < nodeCnt; i++) {
			double angle = (2*Math.PI)/nodeCnt * i;
			EatingNode o = new EatingNode(nodes.size() == 0 ? null : nodes.get(nodes.size()-1), x + Math.cos(angle)*8, y + Math.sin(angle)*8, 1);
			nodes.add(o);
			grid.addCircle(o);
			organicNodes.add(o);
			o.getConnections().add(j);
			j.getConnections().add(o);

			RotationalNode e = new RotationalNode(o, x + Math.cos(angle)*16, y + Math.sin(angle)*16, 1);
			grid.addCircle(e);
			organicNodes.add(e);

			JitterNode a  = new JitterNode(e, x + Math.cos(angle)*24, y + Math.sin(angle)*24, 1.0);
			grid.addCircle(a);
			organicNodes.add(a);
		}

		nodes.get(0).getConnections().add(nodes.get(nodes.size()-1));
		nodes.get(nodes.size()-1).getConnections().add(nodes.get(0));
	}
}
