import graphic_interface.ApplicationFrame;
import physics.CollisionGrid;

public class Main {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;

	public static void main(String[] args) {
		CollisionGrid grid = new CollisionGrid(WIDTH, HEIGHT, 20);
		ApplicationFrame frame = new ApplicationFrame(grid);
		Simulation sim = new Simulation(grid, frame);
	}
}
