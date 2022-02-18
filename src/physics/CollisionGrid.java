package physics;

import java.awt.Color;
import java.util.*;

public class CollisionGrid {
	private List<List<Set<Circle>>> grid;
	private Set<Circle> circles;
	private int width, height;
	private int cellSize;

	public CollisionGrid(int width, int height, int cellSize) {
		this.width = width;
		this.height = height;
		this.cellSize = cellSize;
		this.grid = new ArrayList<>();
		this.circles = new HashSet<>();
		for (int i = 0; i < height/ cellSize; i++) {
			List<Set<Circle>> row = new ArrayList<>(width);
			for (int j = 0; j < width/ cellSize; j++)
				row.add(new HashSet<>(cellSize));
			grid.add(row);
		}
	}

	public void stepCollision() {
		for (Circle c : circles) {

			// Remove circle from current cells before movement to prevent self-collision and memory leaks
			for (Set<Circle> cell : getGridCells(c))
				cell.remove(c);

			// Apply velocity
			c.setX(c.getX() + c.getvX());
			c.setY(c.getY() + c.getvY());

			// Check collision in every cell that contained this circle
			for (Set<Circle> cell : getGridCells(c)) {
				// Keep circle within bounds of the grid
				double lBound = c.getX() - c.getRadius(), rBound = c.getX() + c.getRadius();
				double bBound = c.getY() - c.getRadius(), tBound = c.getY() + c.getRadius();
				if (lBound < 0 || rBound >= width || bBound < 0 || tBound >= height) {
					c.setX(Math.min(width-c.getRadius()-1, Math.max(c.getRadius(), c.getX())));
					c.setY(Math.min(height-c.getRadius()-1, Math.max(c.getRadius(), c.getY())));
					c.setvX(c.getvX()*-1);
					c.setvY(c.getvY()*-1);
				}

				// Simulate elastic collision between each colliding circle
				for (Circle o: cell) {
					if (c.isColliding(o)) {
						c.collidedWith(o);
						o.collidedWith(c);
						if (!(c.isSolid() && o.isSolid()))
							continue;
						// Move this circle out of the collider
						double distance = c.distanceTo(o);
						double depth = (c.getRadius() + o.getRadius()) - distance;
						double xDir = (c.getX() - o.getX()) / distance;
						double yDir = (c.getY() - o.getY()) / distance;
						c.setX(c.getX() + xDir * depth);
						c.setY(c.getY() + yDir * depth);

						// Calculate new velocities using the momentum of each circle
						double cFinalvX =
								(c.getvX() * (c.getMass() - o.getMass())) / (o.getMass() + c.getMass()) +
								(o.getvX() * 2 * o.getMass()) / (c.getMass()+o.getMass());

						double oFinalvX =
								(c.getvX() * 2 *c.getMass()) / (c.getMass()+o.getMass()) +
								(o.getvX() * (o.getMass() - c.getMass())) / (o.getMass() + c.getMass());

						double cFinalvY =
								(c.getvY() * (c.getMass() - o.getMass())) / (o.getMass() + c.getMass()) +
								(o.getvY() * 2 * o.getMass()) / (c.getMass()+o.getMass());

						double oFinalvY =
								(c.getvY() * 2 *c.getMass()) / (c.getMass()+o.getMass()) +
								(o.getvY() * (o.getMass() - c.getMass())) / (o.getMass() + c.getMass());

						c.setvX(cFinalvX);
						c.setvY(cFinalvY);

						o.setvX(oFinalvX);
						o.setvY(oFinalvY);
					}

				}
			}
			for (Set<Circle> cell : getGridCells(c))
				cell.add(c);

			c.collisionStep();
		}
		// Slightly awkward, need to remove all circle ref from grid and then remove circles entirely
		// without concurrent modification exception
		circles.stream().filter(Circle::isDeletable).forEach(this::removeFromCells);
		circles.removeIf(Circle::isDeletable);

	}

	public List<Set<Circle>> getGridCells(Circle circle) {
		int x1 = (int) (circle.getX() - circle.getRadius()) / cellSize;
		int x2 = (int) (circle.getX() + circle.getRadius()) / cellSize;
		int y1 = (int) (circle.getY() - circle.getRadius()) / cellSize;
		int y2 = (int) (circle.getY() + circle.getRadius()) / cellSize;
		List<Set<Circle>> cells = new ArrayList<>();
		for (int y = y1; y <= y2; y++) {
			for (int x = x1; x <= x2; x++) {
				if (y < 0 || y >= grid.size() || x < 0 || x >= grid.get(y).size())
					continue;
				cells.add(grid.get(y).get(x));
			}
		}
		return cells;
	}

	public void removeFromCells(Circle circle) {
		for (Set<Circle> cell : getGridCells(circle))
			cell.remove(circle);
	}

	public boolean addCircle(Circle circle) {
		if (circles.contains(circle))
			return false;
		circles.add(circle);
		for (Set<Circle> cell : getGridCells(circle))
			cell.add(circle);
		return true;
	}

	public boolean removeCircle(Circle circle) {
		if (!circles.contains(circle))
			return false;
		circles.remove(circle);
		removeFromCells(circle);
		return true;
	}

	/* * * * * * * * * * * * * * * * * * * * * */

	public List<List<Set<Circle>>> getGrid() {
		return grid;
	}

	public void setGrid(List<List<Set<Circle>>> grid) {
		this.grid = grid;
	}

	public Set<Circle> getCircles() {
		return circles;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}
	/* * * * * * * * * * * * * * * * * * * * * */
}
