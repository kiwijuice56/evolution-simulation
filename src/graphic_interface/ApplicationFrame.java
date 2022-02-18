package graphic_interface;

import physics.CollisionGrid;
import javax.swing.*;
import java.awt.*;

/**
 * Contains all GUI
 */
public class ApplicationFrame extends JFrame {
	private final JLabel fpsLabel;

	public ApplicationFrame(CollisionGrid grid) {
		setTitle("Evolution simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		GridPanel panel = new GridPanel(grid);
		getContentPane().add(panel, BorderLayout.CENTER);

		fpsLabel = new JLabel("0");
		getContentPane().add(fpsLabel, BorderLayout.SOUTH);

		setSize(grid.getWidth(), grid.getHeight());
		setVisible(true);
	}

	public void updateFPS(double fps) {
		fpsLabel.setText(String.format("fps: %.2f", fps));
	}
}
