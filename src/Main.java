import graphic_interface.ApplicationFrame;
import physics.CollisionGrid;
import simulation.Simulation;

import java.util.concurrent.TimeUnit;

public class Main {
	private static final int MIN_MILLIS_PER_FRAME = 2;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;

	public static void main(String[] args) throws InterruptedException {
		CollisionGrid grid = new CollisionGrid(WIDTH, HEIGHT, 20);
		Simulation sim = new Simulation(grid);
		ApplicationFrame frame = new ApplicationFrame(grid, sim);
		while (true) {
			long start = System.currentTimeMillis();

			grid.stepCollision();
			sim.stepSimulation();
			frame.repaint();

			long end = System.currentTimeMillis();
			TimeUnit.MILLISECONDS.sleep(Math.max(0, MIN_MILLIS_PER_FRAME - (end - start)));
			frame.updateFPS(1/((System.currentTimeMillis()-start) / 1000.0));
		}
	}
}
