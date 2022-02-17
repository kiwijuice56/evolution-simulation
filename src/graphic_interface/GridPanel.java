package graphic_interface;

import physics.*;
import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
	private CollisionGrid grid;
	private int width, height;

	public GridPanel(CollisionGrid grid) {
		this.width = grid.getWidth();
		this.height = grid.getHeight();
		this.grid = grid;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		for (Circle c : grid.getCircles()) {
			g.setColor(c.getColor());
			g.fillOval(
					(int)Math.round(c.getX()-c.getRadius()),
					(int)Math.round(c.getY()-c.getRadius()),
					(int)Math.round(c.getRadius()*2),
					(int)Math.round(c.getRadius()*2));
		}
	}
}
