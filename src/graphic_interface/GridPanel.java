package graphic_interface;

import physics.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Renders the collision grid
 */
public class GridPanel extends JPanel implements MouseMotionListener, MouseListener {
	private final CollisionGrid grid;
	private final int width, height;
	private int xOffset, yOffset;
	private int initialX, initialY;

	public GridPanel(CollisionGrid grid) {
		this.width = grid.getWidth();
		this.height = grid.getHeight();
		this.grid = grid;
		xOffset = 0;
		yOffset = 0;
		setBorder(BorderFactory.createLineBorder(new Color(95,95,115)));
		addMouseListener(this);
		addMouseMotionListener(this);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				xOffset = Math.min(0, Math.max(-grid.getWidth() + getWidth(), xOffset));
				yOffset = Math.min(0, Math.max(-grid.getHeight() + getHeight(), yOffset));
			}
		});
	}

	/**
	 * Redraws all grid lines and circles
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		// Draws grid lines
		g.setColor(new Color(22,22,25));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		for (int i = 0; i <= grid.getHeight(); i += grid.getCellSize())
			g2.drawLine(xOffset, i+getyOffset(), grid.getWidth()+xOffset, i+getyOffset());
		for (int j = 0; j <= grid.getWidth(); j += grid.getCellSize())
			g2.drawLine(j+getxOffset(), grid.getHeight()+yOffset, j+getxOffset(), yOffset);

		for (Circle c : new ArrayList<>(grid.getCircles()))
			if (c != null)
				c.draw(g, xOffset, yOffset);
	}

	/**
	 * Implements MouseMotionListener to capture mouse direction
	 * @param e the MouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		xOffset -= (initialX - e.getX());
		yOffset -= (initialY - e.getY());
		if (Math.abs(initialX - xOffset) > 64)
			initialX = e.getX();
		if (Math.abs(initialY - yOffset) > 64)
			initialY = e.getY();
		xOffset = Math.min(0, Math.max(-grid.getWidth() + getWidth(), xOffset));
		yOffset = Math.min(0, Math.max(-grid.getHeight() + getHeight(), yOffset));
	}

	/**
	 * Implements MouseListener to capture mouse presses for initializing drag reception
	 * @param e the MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		initialX = e.getX();
		initialY = e.getY();
	}

	/* * * * * * * * * * * * * * * * * * * * * */

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	/* * * * * * * * * * * * * * * * * * * * * */
}
