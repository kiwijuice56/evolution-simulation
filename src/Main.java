import graphic_interface.ApplicationFrame;
import physics.CollisionGrid;

public class Main {
	public static void main(String[] args) {
		CollisionGrid grid = new CollisionGrid(1300, 700, 25);
		ApplicationFrame frame = new ApplicationFrame(grid);
		Simulation sim = new Simulation(grid, frame);
	}
}
