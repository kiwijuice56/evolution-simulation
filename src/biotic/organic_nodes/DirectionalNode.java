package biotic.organic_nodes;

import physics.Circle;
import physics.CollisionGrid;
import physics.Node;

import java.util.List;
import java.util.Set;

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

	@Override
	public void collisionStep() {
		super.collisionStep();
		int totalCircles = 0;
		List<Set<Circle>> circles = grid.getGridCells(getX(), getY());
		int[][] sumMat = new int[3][3];
		int circlePtr = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int size = circles.get(circlePtr).size();
				for (Circle c : circles.get(circlePtr))
					if (root.getOrganism().contains(c) || !isValidTarget(c))
						size--;
				totalCircles += size;
				sumMat[i][j] = size;
				circlePtr++;
			}
		}
		if (totalCircles == 0)
			return;
		double xDir = 0, yDir = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				xDir += j*sumMat[i+1][j+1] / (double)totalCircles;
				yDir += i*sumMat[i+1][j+1] / (double)totalCircles;
			}
		}
		setvX(getvX() + xDir * impulseStrength);
		setvY(getvY() + yDir * impulseStrength);
	}

	public abstract boolean isValidTarget(Circle other);
}
