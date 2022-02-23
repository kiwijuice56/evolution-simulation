package graphic_interface;

import physics.CollisionGrid;
import simulation.Simulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

	public ApplicationFrame(CollisionGrid grid, Simulation sim) throws IOException {
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

		getContentPane().add(new OrganismCreator(panel, sim, grid), BorderLayout.EAST);

		setSize(1200, 700);
		setVisible(true);
	}

	/**
	 * Initializes bottom panel and all associated labels and buttons
	 * @return the initialized panel
	 */
	private JPanel createBottomPanel() throws IOException {
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

		Icon playIcon = new ImageIcon(ImageIO.read(ApplicationFrame.class.getResource("/pause.png")));
		Icon pauseIcon = new ImageIcon(ImageIO.read(ApplicationFrame.class.getResource("/pause2.png")));
		DarkJButton pauseButton = new DarkJButton("Pause");
		pauseButton.setIcon(pauseIcon);
		pauseButton.addActionListener(e -> {
			if (e.getActionCommand().equals("Pause")) {
				ApplicationFrame.this.setPaused(true);
				pauseButton.setIcon(playIcon);
				pauseButton.setText("Play");
			} else if (e.getActionCommand().equals("Play")) {
				ApplicationFrame.this.setPaused(false);
				pauseButton.setIcon(pauseIcon);
				pauseButton.setText("Pause");
			}
		});
		bottomPanel.add(pauseButton);

		Icon visibleIcon = new ImageIcon(ImageIO.read(ApplicationFrame.class.getResource("/visible.png")));
		Icon hiddenIcon = new ImageIcon(ImageIO.read(ApplicationFrame.class.getResource("/hidden.png")));
		DarkJButton graphicsToggle = new DarkJButton("Hide Graphics");
		graphicsToggle.setIcon(visibleIcon);
		graphicsToggle.addActionListener(e -> {
			if (e.getActionCommand().equals("Hide Graphics")) {
				graphicsToggle.setIcon(hiddenIcon);
				ApplicationFrame.this.setGraphicsActive(false);
				graphicsToggle.setText("Show Graphics");
			} else if (e.getActionCommand().equals("Show Graphics")) {
				graphicsToggle.setIcon(visibleIcon);
				ApplicationFrame.this.setGraphicsActive(true);
				graphicsToggle.setText("Hide Graphics");
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

		JLabel link = new JLabel(" https://github.com/kiwijuice56/evolution-simulation");
		link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		link.setForeground(new Color(65,125,210));
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/kiwijuice56/evolution-simulation"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				link.setForeground(new Color(15,200,150));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				link.setForeground(new Color(65,95,220));
			}
		});

		bottomPanel.add(link);

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
