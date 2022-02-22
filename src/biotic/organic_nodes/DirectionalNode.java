package biotic.organic_nodes;

import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.util.List;
import java.util.Set;

/**
 * Abstract node that scans the surrounding nine cells to move in a direction over time
 */
public abstract class DirectionalNode extends OrganicNode {
	private final CollisionGrid grid;
	private final ReproductiveNode root;
	protected double impulseStrength = 0.035;

	public DirectionalNode(CollisionGrid grid, ReproductiveNode root) {
		this(grid, root, null, 0, 0, 1.0);
	}

	public DirectionalNode(CollisionGrid grid, ReproductiveNode root, Node linkedNode, double x, double y, double energy) {
		super(linkedNode, x, y, energy);
		this.grid = grid;
		this.root = root;
	}

	/**
	 * Overrides circle method to recalculate scan in each frame
	 */
	@Override
	public void collisionStep() {
		super.collisionStep();
		int totalCircles = 0, circlePtr = 0;
		int[][] densityMat = new int[3][3];
		List<Set<Circle>> circles = grid.getGridCells(getX(), getY());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// Get the total circles in this grid cell
				int size = circles.get(circlePtr).size();
				// Ensures circle is a valid target (implemented by subclasses) and not a part of the same organism
				for (Circle c : circles.get(circlePtr))
					if (root.getOrganism().contains(c) || !isValidTarget(c))
						size--;
				totalCircles += size;
				densityMat[i][j] = size;
				circlePtr++;
			}
		}
		if (totalCircles == 0)
			return;

		// Calculate direction using matrix
		// vX= -1  0  1      vY= 1  1  1
		//     -1  0  1 * d      0  0  0 * d
		//     -1  0  1         -1 -1 -1
		double xDir = 0, yDir = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				xDir += j*densityMat[i+1][j+1] / (double)totalCircles;
				yDir += i*densityMat[i+1][j+1] / (double)totalCircles;
			}
		}
		setvX(getvX() + xDir * impulseStrength);
		setvY(getvY() + yDir * impulseStrength);
	}

	/**
	 * Determine if circle should be considered in density calculations
	 * @param other the circle to be considered
	 * @return whether the circle is a valid target
	 */
	public abstract boolean isValidTarget(Circle other);
}
