package graphic_interface;

import physics.CollisionGrid;
import simulation.Simulation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Contains all GUI
 */
public class ApplicationFrame extends JFrame  {
	private final JLabel fpsLabel;
	private final JLabel nodeLabel;
	private final JLabel circleLabel;
	private boolean graphicsActive = true;
	private boolean limitedFPS = true;

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

		nodeLabel = new JLabel("nodes:      0");
		nodeLabel.setForeground(new Color(200, 203, 207));
		nodeLabel.setBorder(new EmptyBorder(0,0,0,16));
		statisticPanel.add(nodeLabel);

		circleLabel = new JLabel("circles:      0");
		circleLabel.setForeground(new Color(200, 203, 207));
		circleLabel.setBorder(new EmptyBorder(0,0,0,16));
		statisticPanel.add(circleLabel);

		JButton graphicsToggle = new JButton("Turn Graphics Off");
		graphicsToggle.addActionListener(e -> {
			if (e.getActionCommand().equals("Turn Graphics Off")) {
				ApplicationFrame.this.setGraphicsActive(false);
				graphicsToggle.setText("Turn Graphics On");
			} else if (e.getActionCommand().equals("Turn Graphics On")) {
				ApplicationFrame.this.setGraphicsActive(true);
				graphicsToggle.setText("Turn Graphics Off");
			}
		});
		graphicsToggle.setBackground(new Color(60,60,72));
		graphicsToggle.setForeground(new Color(200, 203, 207));
		graphicsToggle.setFocusPainted(false);
		statisticPanel.add(graphicsToggle);

		JButton fpsToggle = new JButton("Unlimited FPS");
		fpsToggle.addActionListener(e -> {
			if (e.getActionCommand().equals("Unlimited FPS")) {
				ApplicationFrame.this.setLimitedFPS(false);
				fpsToggle.setText("Limited FPS");
			} else if (e.getActionCommand().equals("Limited FPS")) {
				ApplicationFrame.this.setLimitedFPS(true);
				fpsToggle.setText("Unlimited FPS");
			}
		});
		fpsToggle.setBackground(new Color(60,60,72));
		fpsToggle.setForeground(new Color(200, 203, 207));
		fpsToggle.setFocusPainted(false);
		statisticPanel.add(fpsToggle);


		getContentPane().add(statisticPanel, BorderLayout.SOUTH);

		OrganismCreator organismCreator = new OrganismCreator(sim, grid);
		getContentPane().add(organismCreator, BorderLayout.EAST);

		setSize(grid.getWidth(), grid.getHeight());
		setVisible(true);
	}

	public void updateFPS(double fps) {
		fpsLabel.setText(String.format("fps: %05d", Math.round(fps)));
	}

	public void updateNodes(int nodes) {
		nodeLabel.setText(String.format("nodes: %07d", nodes));
	}

	public void updateCircles(int circles) {
		circleLabel.setText(String.format("circles: %07d", circles));
	}

	public boolean isGraphicsActive() {
		return graphicsActive;
	}

	public void setGraphicsActive(boolean graphicsActive) {
		this.graphicsActive = graphicsActive;
	}

	public boolean isLimitedFPS() {
		return limitedFPS;
	}

	public void setLimitedFPS(boolean limitedFPS) {
		this.limitedFPS = limitedFPS;
	}
}
