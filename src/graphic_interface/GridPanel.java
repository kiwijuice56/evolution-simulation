package graphic_interface;

import physics.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(25,25,25));
		for (int i = 0; i <= grid.getHeight(); i += grid.getCellSize()) {
			g.drawLine(xOffset, i+getyOffset(), grid.getWidth()+xOffset, i+getyOffset());
		}
		for (int j = 0; j <= grid.getWidth(); j += grid.getCellSize()) {
			g.drawLine(j+getxOffset(), grid.getHeight()+yOffset, j+getxOffset(), yOffset);
		}
		for (Circle c : new ArrayList<>(grid.getCircles()))
			if (c != null)
				c.draw(g, xOffset, yOffset);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		xOffset -= (initialX - e.getX());
		yOffset -= (initialY - e.getY());
		if (Math.abs(initialX - xOffset) > 64)
			initialX = e.getX();
		if (Math.abs(initialY - yOffset) > 64)
			initialY = e.getY();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		initialX = e.getX();
		initialY = e.getY();
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
