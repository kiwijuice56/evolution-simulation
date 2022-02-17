package graphic_interface;

import physics.CollisionGrid;
import javax.swing.*;

public class ApplicationFrame extends JFrame {
	public ApplicationFrame(CollisionGrid grid) {
		getContentPane().setName("Evolution Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridPanel panel = new GridPanel(grid);
		getContentPane().add(panel);
		setSize(grid.getWidth(), grid.getHeight());
		setVisible(true);
	}
}
