import graphic_interface.ApplicationFrame;
import physics.Circle;
import physics.CollisionGrid;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Simulation {
	private static final int MIN_MILLIS_PER_FRAME = 3;

	public Simulation(CollisionGrid grid, ApplicationFrame frame) {
		// Randomly place circles to test physics
		for (int i = 1; i < 80; i++) {
			for (int j = 1; j < 155; j++) {
				int size = (int)(Math.random()*3)+2;
				Circle a = new Circle(j*8, i*8, size, size, Color.WHITE);
				a.setvX(Math.random()-.5);
				a.setvY(Math.random()-.5);
				grid.addCircle(a);
			}

		}

		// Run collision steps in infinite loop
		while (true) {
			long start = System.currentTimeMillis();
			grid.stepCollision();
			frame.repaint();
			long end = System.currentTimeMillis();
			try {
				TimeUnit.MICROSECONDS.sleep(Math.max(0, MIN_MILLIS_PER_FRAME-end-start));
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}

		}

	}
}
