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
import java.util.Objects;

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

		getContentPane().add(createTopPanel(), BorderLayout.NORTH);
		getContentPane().add(createBottomPanel(), BorderLayout.SOUTH);

		getContentPane().add(new OrganismCreator(panel, sim, grid), BorderLayout.WEST);

		setSize(1200, 700);
		setVisible(true);
	}

	/**
	 * Initializes top panel and all associated buttons
	 * @return the initialized panel
	 */
	private JPanel createTopPanel() throws IOException {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBackground(new Color(42, 42, 50));

		topPanel.add(Box.createHorizontalGlue());

		Icon playIcon = new ImageIcon(ImageIO.read(Objects.requireNonNull(ApplicationFrame.class.getResource("/play.png"))));
		Icon pauseIcon = new ImageIcon(ImageIO.read(Objects.requireNonNull(ApplicationFrame.class.getResource("/pause.png"))));
		DarkJButton pauseButton = new DarkJButton("");
		pauseButton.setIcon(pauseIcon);
		pauseButton.addActionListener(e -> {
			pauseButton.setIcon(isPaused() ? pauseIcon : playIcon);
			setPaused(!isPaused());
		});
		topPanel.add(pauseButton);

		Icon visibleIcon = new ImageIcon(ImageIO.read(Objects.requireNonNull(ApplicationFrame.class.getResource("/visible.png"))));
		Icon hiddenIcon = new ImageIcon(ImageIO.read(Objects.requireNonNull(ApplicationFrame.class.getResource("/hidden.png"))));
		DarkJButton graphicsToggle = new DarkJButton("");
		graphicsToggle.setIcon(visibleIcon);

		graphicsToggle.addActionListener(e -> {
			graphicsToggle.setIcon(isGraphicsActive() ? hiddenIcon : visibleIcon);
			setGraphicsActive(!isGraphicsActive());
		});
		topPanel.add(graphicsToggle);

		Icon fpsLockedIcon = new ImageIcon(ImageIO.read(Objects.requireNonNull(ApplicationFrame.class.getResource("/fps_locked.png"))));
		Icon fpsUnlockedIcon = new ImageIcon(ImageIO.read(Objects.requireNonNull(ApplicationFrame.class.getResource("/fps_unlocked.png"))));
		DarkJButton fpsToggle = new DarkJButton("");
		fpsToggle.setIcon(fpsLockedIcon);
		fpsToggle.addActionListener(e -> {
			fpsToggle.setIcon(isLimitedFPS() ? fpsUnlockedIcon : fpsLockedIcon);
			setLimitedFPS(!isLimitedFPS());
		});
		topPanel.add(fpsToggle);

		topPanel.add(Box.createHorizontalGlue());

		return topPanel;
	}

	/**
	 * Initializes bottom panel and all associated labels
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

		bottomPanel.add(Box.createHorizontalGlue());

		JLabel link = new JLabel(" https://github.com/kiwijuice56/evolution-simulation ");
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
