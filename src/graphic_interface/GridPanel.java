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
		setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(15,15,15));
		for (int i = 0; i < grid.getHeight(); i += grid.getCellSize()) {
			g.drawLine(0, i, grid.getWidth(), i);
		}
		for (int j = 0; j < grid.getWidth(); j += grid.getCellSize()) {
			g.drawLine(j, grid.getHeight(), j,0);
		}
		for (Circle c : new ArrayList<>(grid.getCircles()))
			if (c != null)
				c.draw(g);
	}
}
