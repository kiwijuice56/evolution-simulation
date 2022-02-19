package biotic.organic_nodes;

import physics.Node;
import simulation.Simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReproductiveNode extends OrganicNode {
	private final Simulation sim;

	private static final double GENERATIONAL_ERROR_GROWTH = 0.005;
	private static final double MAX_GENERATIONAL_ERROR = 0.5;
	private static final double INSTRUCTION_INSERTION_CHANCE = 0.015;
	private static final double INSTRUCTION_DELETION_CHANCE = 0.005;
	private static final double WORD_TRANSFORM_CHANCE = 0.05;
	private static final double DIGIT_INSERT_CHANCE = 0.00045;
	private static final double DIGIT_DELETE_CHANCE = 0.00015;
	private static final double DIGIT_TRANSFORM_CHANCE = 0.00025;

	private static final String[] NODE_TYPES = {"nod", "jit", "rot", "eat", "pre"};

	private final List<String> code;
	private final List<String> permCode;
	private final List<OrganicNode> organism;
	private int children = 0;

	public ReproductiveNode(Node linkedNode, double x, double y, double energy, List<String> code, Simulation sim) {
		super(linkedNode, x, y, energy);
		this.sim = sim;

		this.permCode = new ArrayList<>(code);
		this.code = new ArrayList<>(code);
		Collections.reverse(this.code);

		this.organism = new ArrayList<>();
		organism.add(this);

		this.hunger = 0.00015;
		this.maxEnergy = 5.0;
		this.radius = 4.0;
		this.mass = 5.0;
		this.color = new Color(255, 255, 255);
	}

	@Override
	public boolean stepLife() {
		boolean isAlive = super.stepLife();
		if (isAlive && code.size() > 0 && getEnergy() >= 1.667) {
			createNode(code.remove(code.size()-1));
		}
		if (isAlive && code.size() == 0 && getEnergy() >= 2) {
			reproduce();
			setEnergy(0);
		}
		return isAlive;
	}

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
			default -> new OrganicNode();
		};

		node.setEnergy(0.0);
		if (root == 0) {
			node.setX(getX());
			node.setY(getY());
			connect(node);
		} else {
			node.setX(organism.get(root).getX());
			node.setY(organism.get(root).getY());
		}
		for (String num : nums) {
			int idx = Integer.parseInt(num);
			if (idx < organism.size()) {
				organism.get(idx).connect(node);
			}
		}
		organism.add(node);
		sim.addOrganicNode(node);
	}

	private void reproduce() {
		children++;
		ReproductiveNode child = new ReproductiveNode(null, getX(), getY(), 2.5, shuffleCode(permCode, children), sim);
		sim.addOrganicNode(child);
		child.setvX(2*(Math.random() - 0.5));
		child.setvY(2*(Math.random() - 0.5));
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			List<String> x = shuffleCode(new ArrayList<>(Arrays.asList("nod 0", "nod 0 1")), 0);
			if (!x.equals(new ArrayList<>(Arrays.asList("nod 0", "nod 0 1"))))
				System.out.println(x);
		}
	}

	private static List<String> shuffleCode(List<String> permCode, int children){
		List<String> newCode = new ArrayList<>();
		double genError = Math.min(MAX_GENERATIONAL_ERROR, children * GENERATIONAL_ERROR_GROWTH);
		int[] frameShift = new int[permCode.size()+1];
		for (int i = 0; i < permCode.size(); i++) {

			// delete a node
			frameShift[i+1] = frameShift[i];
			if (genError + INSTRUCTION_DELETION_CHANCE > Math.random()) {
				frameShift[i+1]++;
				continue;
			}

			String word = permCode.get(i).substring(0, permCode.get(i).indexOf(" "));
			String[] nums = permCode.get(i).substring(permCode.get(i).indexOf(" ")+1).split(" ");

			String newInstruction;
			StringBuilder newNums = new StringBuilder();

			// swap this node's word
			if (genError + WORD_TRANSFORM_CHANCE > Math.random())
				newInstruction = NODE_TYPES[(int) (Math.random() * NODE_TYPES.length)];
			// normal circumstances
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
				newNums.append(num-frameShift[num]).append(" ");
			}

			if (genError + DIGIT_INSERT_CHANCE > Math.random()) {
				int num = (int)(Math.random() * i);
				newNums.append(num);
			}

			newCode.add(newInstruction + " " + newNums.toString().strip());
		}

		// chance to add insert mode nodes
		if (genError + INSTRUCTION_INSERTION_CHANCE > Math.random())
			newCode.add(NODE_TYPES[(int) (Math.random() * NODE_TYPES.length)] + " " + (int)(Math.random() * (newCode.size() + 1)));

		return newCode;
	}

	public double getHunger() {
		return hunger * (children+1);
	}

	public List<OrganicNode> getOrganism() {
		return organism;
	}
}