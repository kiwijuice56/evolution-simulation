import graphic_interface.ApplicationFrame;
import physics.Circle;
import physics.Node;
import physics.CollisionGrid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Simulation {
	private static final int MIN_MILLIS_PER_FRAME = 5;

	public Simulation(CollisionGrid grid, ApplicationFrame frame) {
		chainNodes(grid);

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Run collision steps in infinite loop
		while (true) {
			long start = System.currentTimeMillis();
			grid.stepCollision();
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

	// Temporary testing method to demonstrate features
	private void chainNodes(CollisionGrid grid) {
		List<Node> nodes = new ArrayList<>();
		// Randomly place circles to test physics
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				Node node = new Node(null, 200 + (i*50), 200 + (j*50), 15, 15, Color.WHITE);
				node.setvX(.5);
				for (Node other : nodes) {
					node.getConnections().add(other);
					other.getConnections().add(node);
				}
				nodes.add(node);
				grid.addCircle(node);
			}
		}

		for (int i = 1; i < 35; i++) {
			for (int j = 1; j < 50; j++) {
				int size = (int)(Math.random()*2)+5;
				Node a = new Node(null, 50+j*15, 50+i*15, size, size, Color.WHITE);
				boolean isColliding = false;
				for (Node other : nodes)
					if (other.isColliding(a))
						isColliding = true;
				if (isColliding)
					continue;
				a.setvX(Math.random()-.5);
				a.setvY(Math.random()-.5);
				grid.addCircle(a);
			}
		}
	}
}
