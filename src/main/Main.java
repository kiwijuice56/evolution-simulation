package main;

import graphic_interface.ApplicationFrame;
import physics.CollisionGrid;
import simulation.Simulation;

import java.util.concurrent.TimeUnit;

public class Main {
	private static final long MIN_WAIT_PER_FRAME = 2000000;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;

	public static void main(String[] args) throws InterruptedException {
		CollisionGrid grid = new CollisionGrid(WIDTH, HEIGHT, 20);
		Simulation sim = new Simulation(grid);
		ApplicationFrame frame = new ApplicationFrame(grid, sim);
		int iterCount = 0;
		while (true) {
			long start = System.nanoTime();

			grid.stepCollision();
			sim.stepSimulation();
			if (frame.isGraphicsActive())
				frame.repaint();

			long end = System.nanoTime();
			if (frame.isLimitedFPS())
				TimeUnit.NANOSECONDS.sleep(Math.max(0, MIN_WAIT_PER_FRAME - (end - start)));
			if (iterCount % 100 == 0) {
				iterCount = 0;
				frame.updateFPS(1/((System.nanoTime()-start)*.000000001));
				frame.updateNodes(sim.getNodeCount());
				frame.updateCircles(grid.getCircles().size());
			}
			iterCount++;
		}
	}
}
