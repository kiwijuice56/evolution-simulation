package graphic_interface;

import physics.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Renders the collision grid
 */
public class GridPanel extends JPanel {
	private final CollisionGrid grid;
	private final int width, height;

	public GridPanel(CollisionGrid grid) {
		this.width = grid.getWidth();
		this.height = grid.getHeight();
		this.grid = grid;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		for (Circle c : new ArrayList<>(grid.getCircles()))
			c.draw(g);
	}
}
