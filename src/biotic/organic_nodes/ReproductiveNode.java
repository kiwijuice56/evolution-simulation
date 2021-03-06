package biotic.organic_nodes;

import physics.CollisionGrid;
import physics.Node;
import simulation.Simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles the reproduction and growth of an organism
 */
public class ReproductiveNode extends OrganicNode {
	private final Simulation sim;
	private final CollisionGrid grid;

	private static final double ENERGY_TO_GROW = 1.6;
	private static final double GROWTH_ENERGY_MULTIPLIER = 0.65;
	private static final double ENERGY_TO_REPRODUCE = 2.0;
	private static final double REPRODUCTION_COST_PER_CHILD = 0.5;
	private static final double DISCOUNT_PER_GROWTH = 0.75;
	private static final double INITIAL_ENERGY = 2.5;
	private static final double INITIAL_SPEED = 2.5;

	private static final double GENERATIONAL_ERROR_GROWTH = 0.005;
	private static final double MAX_GENERATIONAL_ERROR = 0.5;
	private static final double INSTRUCTION_INSERTION_CHANCE = 0.015;
	private static final double INSTRUCTION_DELETION_CHANCE = 0.0025;
	private static final double WORD_TRANSFORM_CHANCE = 0.05;
	private static final double DIGIT_INSERT_CHANCE = 0.00075;
	private static final double DIGIT_DELETE_CHANCE = 0.00005;
	private static final double DIGIT_TRANSFORM_CHANCE = 0.00025;

	public static final String[] NODE_TYPES = {"nod", "jit", "rot", "eat", "pre", "sto", "fol", "run", "gat"};

	private final List<String> code;
	private final List<String> permCode;
	private final List<OrganicNode> organism;
	private int childCount = 0;

	public ReproductiveNode(Node linkedNode, double x, double y, double energy, List<String> code, Simulation sim, CollisionGrid grid) {
		super(linkedNode, x, y, energy);
		this.sim = sim;
		this.grid = grid;

		this.permCode = new ArrayList<>(code);
		this.code = new ArrayList<>(code);
		Collections.reverse(this.code);

		this.organism = new ArrayList<>();
		organism.add(this);

		this.radius = 4.0;
		this.mass = 5.0;
		this.color = new Color(255, 255, 255);
		this.hunger = 0.0048;
		this.maxEnergy = 6.0;
		this.resistance = 0.75;
	}

	/**
	 * Overrides OrganicNode method to check if the node has enough energy for reproduction and growth processes
	 * @return whether this organism is alive
	 */
	@Override
	public boolean lifeStep() {
		boolean isAlive = super.lifeStep();
		if (isAlive && code.size() > 0 && getEnergy() >= ENERGY_TO_GROW) {
			createNode(code.remove(code.size()-1));
			setEnergy(getEnergy() * GROWTH_ENERGY_MULTIPLIER);
			hunger *= DISCOUNT_PER_GROWTH;
		} else if (isSolid() && isAlive && code.size() == 0 && getEnergy() >= ENERGY_TO_REPRODUCE + REPRODUCTION_COST_PER_CHILD * childCount) {
			reproduce();
			setEnergy(0);
		}
		return isAlive;
	}

	/**
	 * Creates a new node to attach to this organism
	 * @param instruction the single line instruction from the code
	 */
	private void createNode(String instruction) {
		String type = instruction.substring(0, instruction.indexOf(' '));
		String[] nums = instruction.substring(instruction.indexOf(' ') + 1).split(" ");
		if (nums.length == 0 || nums[0].equals(""))
			return;
		int root = Integer.parseInt(nums[0]);

		OrganicNode node = switch (type) {
			case "jit" -> new JitterNode();
			case "rot" -> new RotationalNode();
			case "eat" -> new EatingNode();
			case "pre" -> new PredationNode(this);
			case "sto" -> new StorageNode();
			case "fol" -> new FollowingNode(grid, this);
			case "run" -> new RunningNode(grid, this);
			case "gat" -> new GatheringNode(grid, this);
			default -> new OrganicNode();
		};

		node.setEnergy(0.0);
		node.setX(organism.get(root).getX() + organism.get(root).getRadius() + getRadius());
		node.setY(organism.get(root).getY() + organism.get(root).getRadius() + getRadius());
		for (String num : nums) {
			int idx = Integer.parseInt(num);
			if (idx < organism.size())
				organism.get(idx).connect(node);
		}
		organism.add(node);
		sim.addOrganicNode(node);
	}

	/**
	 * Copy this node's code into a new ReproductiveNode, with some occasional code errors
	 */
	private void reproduce() {
		childCount++;
		ReproductiveNode child = new ReproductiveNode(null, getX(), getY(), INITIAL_ENERGY,
				getMutatedCode(permCode, childCount), sim, grid);
		sim.addOrganicNode(child);
		child.setvX(INITIAL_SPEED*(Math.random() - 0.5));
		child.setvY(INITIAL_SPEED*(Math.random() - 0.5));
	}

	/**
	 * Copy this node's code into a new list with errors such as deletions, insertions, and swaps
	 * @param permCode the initial code
	 * @param children the number of children this node has created
	 * @return the new code
	 */
	private static List<String> getMutatedCode(List<String> permCode, int children){
		List<String> newCode = new ArrayList<>();
		double genError = Math.min(MAX_GENERATIONAL_ERROR, children * GENERATIONAL_ERROR_GROWTH);
		int[] frameShift = new int[permCode.size()+1];
		for (int i = 0; i < permCode.size(); i++) {
			// Delete a node
			frameShift[i+1] = frameShift[i];
			if (genError + INSTRUCTION_DELETION_CHANCE > Math.random()) {
				frameShift[i+1]++;
				continue;
			}

			String word = permCode.get(i).substring(0, permCode.get(i).indexOf(" "));
			String[] nums = permCode.get(i).substring(permCode.get(i).indexOf(" ")+1).split(" ");

			String newInstruction;
			StringBuilder newNums = new StringBuilder();

			// Swap this node's word
			if (genError + WORD_TRANSFORM_CHANCE > Math.random())
				newInstruction = NODE_TYPES[(int) (Math.random() * NODE_TYPES.length)];
			// Normal word
			else
				newInstruction = word;

			int removedIdx = -1;
			if (genError + DIGIT_DELETE_CHANCE > Math.random() && nums.length > 1)
				removedIdx = (int) (Math.random() * nums.length);

			for (int j = 0; j < nums.length; j++) {
				int num = Integer.parseInt(nums[j]);
				if (genError + DIGIT_TRANSFORM_CHANCE > Math.random())
					num = (int) (Math.random() * i);
				if (j == removedIdx)
					continue;
				if (num < frameShift.length)
					newNums.append(num-frameShift[num]).append(" ");
			}

			if (genError + DIGIT_INSERT_CHANCE > Math.random()) {
				int num = (int)(Math.random() * i);
				if (num < frameShift.length)
					newNums.append(num-frameShift[num]);
			}

			newCode.add(newInstruction + " " + newNums.toString().strip());
		}

		// chance to add insert mode nodes
		if (genError + INSTRUCTION_INSERTION_CHANCE > Math.random())
			newCode.add(NODE_TYPES[(int) (Math.random() * NODE_TYPES.length)] + " " + (int)(Math.random() * (newCode.size() + 1)));

		return newCode;
	}

	/* * * * * * * * * * * * * * * * * * * * * */

	@Override
	public double getHunger() {
		return hunger * (childCount +1);
	}

	public List<OrganicNode> getOrganism() {
		return organism;
	}

	public List<String> getCode() {
		return code;
	}

	public List<String> getPermCode() {
		return permCode;
	}

	/* * * * * * * * * * * * * * * * * * * * * */
}
