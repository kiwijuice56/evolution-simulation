package graphic_interface;

import physics.CollisionGrid;
import javax.swing.*;

/**
 * Contains all GUI
 */
public class ApplicationFrame extends JFrame {
	public ApplicationFrame(CollisionGrid grid) {
		setTitle("Evolution Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridPanel panel = new GridPanel(grid);
		getContentPane().add(panel);

		setSize(grid.getWidth(), grid.getHeight());
		setVisible(true);

	}
}
