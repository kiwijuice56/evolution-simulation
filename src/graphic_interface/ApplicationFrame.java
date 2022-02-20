package graphic_interface;

import physics.CollisionGrid;
import simulation.Simulation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Contains all GUI
 */
public class ApplicationFrame extends JFrame {
	private final JLabel fpsLabel;
	private final JLabel nodeLabel;
	private final JLabel circleLabel;

	public ApplicationFrame(CollisionGrid grid, Simulation sim) {
		setTitle("Evolution simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(42, 42, 50));

		GridPanel panel = new GridPanel(grid);
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel statisticPanel = new JPanel();
		statisticPanel.setLayout(new BoxLayout(statisticPanel, BoxLayout.X_AXIS));
		statisticPanel.setBackground(new Color(42, 42, 50));

		fpsLabel = new JLabel("fps: 0");
		fpsLabel.setForeground(new Color(200, 203, 207));
		fpsLabel.setBorder(new EmptyBorder(0,0,0,16));
		statisticPanel.add(fpsLabel);

		nodeLabel = new JLabel("nodes:     0");
		nodeLabel.setForeground(new Color(200, 203, 207));
		nodeLabel.setBorder(new EmptyBorder(0,0,0,16));
		statisticPanel.add(nodeLabel);

		circleLabel = new JLabel("nodes:     0");
		circleLabel.setForeground(new Color(200, 203, 207));
		circleLabel.setBorder(new EmptyBorder(0,0,0,16));
		statisticPanel.add(circleLabel);

		getContentPane().add(statisticPanel, BorderLayout.SOUTH);

		OrganismCreator organismCreator = new OrganismCreator(sim, grid);
		getContentPane().add(organismCreator, BorderLayout.EAST);

		setSize(grid.getWidth(), grid.getHeight());
		setVisible(true);
	}

	public void updateFPS(double fps) {
		fpsLabel.setText(String.format("fps: %.2f", fps));
	}
	public void updateNodes(int nodes) {
		nodeLabel.setText(String.format("nodes: %5d", nodes));
	}
	public void updateCircles(int circles) {
		circleLabel.setText(String.format("circles: %5d", circles));
	}
}
