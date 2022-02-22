package graphic_interface;

import physics.CollisionGrid;
import simulation.Simulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Contains all GUI for the program
 */
public class ApplicationFrame extends JFrame  {
	private JLabel fpsLabel;
	private JLabel nodeLabel;
	private JLabel circleLabel;
	private boolean graphicsActive = true;
	private boolean limitedFPS = true;
	private boolean paused = false;

	public ApplicationFrame(CollisionGrid grid, Simulation sim) {
		setTitle("Evolution simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Image icon;
		try {
			icon = ImageIO.read(ApplicationFrame.class.getResource("/icon.png"));
		} catch (IOException ex) {
			System.out.println(Arrays.toString(ex.getStackTrace()));
			return;
		}
		setIconImage(icon);

		getContentPane().setBackground(new Color(42, 42, 50));

		GridPanel panel = new GridPanel(grid);
		getContentPane().add(panel, BorderLayout.CENTER);

		getContentPane().add(createBottomPanel(), BorderLayout.SOUTH);

		getContentPane().add(new OrganismCreator(sim, grid), BorderLayout.EAST);

		setSize(1200, 700);
		setVisible(true);
	}

	/**
	 * Initializes bottom panel and all associated labels and buttons
	 * @return the initialized panel
	 */
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBackground(new Color(42, 42, 50));

		fpsLabel = new JLabel(" fps: 0");
		fpsLabel.setForeground(new Color(200, 203, 207));
		fpsLabel.setBorder(new EmptyBorder(0,0,0,16));
		bottomPanel.add(fpsLabel);

		nodeLabel = new JLabel("nodes:      0");
		nodeLabel.setForeground(new Color(200, 203, 207));
		nodeLabel.setBorder(new EmptyBorder(0,0,0,16));
		bottomPanel.add(nodeLabel);

		circleLabel = new JLabel("circles:      0");
		circleLabel.setForeground(new Color(200, 203, 207));
		circleLabel.setBorder(new EmptyBorder(0,0,0,16));
		bottomPanel.add(circleLabel);

		DarkJButton pauseButton = new DarkJButton("Pause");
		pauseButton.addActionListener(e -> {
			if (e.getActionCommand().equals("Pause")) {
				ApplicationFrame.this.setPaused(true);
				pauseButton.setText("Unpause");
			} else if (e.getActionCommand().equals("Unpause")) {
				ApplicationFrame.this.setPaused(false);
				pauseButton.setText("Pause");
			}
		});
		bottomPanel.add(pauseButton);

		DarkJButton graphicsToggle = new DarkJButton("Turn Graphics Off");
		graphicsToggle.addActionListener(e -> {
			if (e.getActionCommand().equals("Turn Graphics Off")) {
				ApplicationFrame.this.setGraphicsActive(false);
				graphicsToggle.setText("Turn Graphics On");
			} else if (e.getActionCommand().equals("Turn Graphics On")) {
				ApplicationFrame.this.setGraphicsActive(true);
				graphicsToggle.setText("Turn Graphics Off");
			}
		});
		bottomPanel.add(graphicsToggle);

		DarkJButton fpsToggle = new DarkJButton("Unlimited FPS");
		fpsToggle.addActionListener(e -> {
			if (e.getActionCommand().equals("Unlimited FPS")) {
				ApplicationFrame.this.setLimitedFPS(false);
				fpsToggle.setText("Limited FPS");
			} else if (e.getActionCommand().equals("Limited FPS")) {
				ApplicationFrame.this.setLimitedFPS(true);
				fpsToggle.setText("Unlimited FPS");
			}
		});
		bottomPanel.add(fpsToggle);
		return bottomPanel;
	}

	/* * * * * * * * * * * * * * * * * * * * * */

	public void updateFPS(double fps) {
		fpsLabel.setText(String.format(" fps: %05d", Math.round(fps)));
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

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}
