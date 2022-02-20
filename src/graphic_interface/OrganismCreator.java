package graphic_interface;

import biotic.organic_nodes.ReproductiveNode;
import physics.CollisionGrid;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrganismCreator extends JPanel implements ActionListener {
	private final Simulation sim;
	private final CollisionGrid grid;

	private final JTextArea text;
	private final JLabel errorLabel;

	public OrganismCreator(Simulation sim, CollisionGrid grid) {
		this.sim = sim;
		this.grid = grid;

		JPanel codeEditor = new JPanel();
		codeEditor.setLayout(new BoxLayout(codeEditor, BoxLayout.Y_AXIS));

		text = new JTextArea(28, 16);
		text.setFont(new Font("Consolas", Font.PLAIN, 16));
		codeEditor.add(text);

		JPanel textButtonContainer = new JPanel();
		textButtonContainer.setLayout(new BoxLayout(textButtonContainer, BoxLayout.X_AXIS));

		JButton add = new JButton("Add Organism");
		add.addActionListener(this);
		textButtonContainer.add(add);

		JButton save = new JButton("Save As");
		save.addActionListener(this);
		textButtonContainer.add(save);

		codeEditor.add(textButtonContainer);

		errorLabel = new JLabel("Type an organism code");
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		codeEditor.add(errorLabel);

		add(codeEditor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add Organism")) {
			List<String> code = parseInput(text.getText());
			if (validateInput(code)) {
				sim.addOrganicNode(new ReproductiveNode(
						null,
						4 + (grid.getWidth()-4) * Math.random(),
						4 + (grid.getHeight()-4) * Math.random(),
						2.5,
						code,
						sim));
			}
		} else if (e.getActionCommand().equals("Save As")) {
			JFileChooser fc = new JFileChooser();
			int result = fc.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION){
				File file = new File(fc.getSelectedFile().getAbsolutePath());
				try {
					PrintWriter writer = new PrintWriter(file);
					writer.println(text.getText());
					writer.close();
				} catch (IOException ex) {
					ex.printStackTrace();

				}
			}
		}
	}

	private List<String> parseInput(String s) {
		return new ArrayList<>(Arrays.asList(s.split("\n")));
	}

	private boolean validateInput(List<String> s) {
		errorLabel.setForeground(Color.RED);
		for (int i = 0; i < s.size(); i++) {
			String instruction = s.get(i);
			if (!instruction.contains(" ")) {
				errorLabel.setText("Line " + (i+1) + ": Invalid formatting!");
				return false;
			}
			String word = instruction.substring(0, instruction.indexOf(' '));
			boolean isValidWord = false;
			for (String validWord : ReproductiveNode.NODE_TYPES)
				if (validWord.equals(word)) {
					isValidWord = true;
					break;
				}
			if (!isValidWord) {
				errorLabel.setText("Line " + (i+1) + ": Invalid word!");
				return false;
			}
			String[] nums = instruction.substring(instruction.indexOf(' ')+1).split(" ");
			for (String sNum : nums) {
				int num;
				try {
					num = Integer.parseInt(sNum);
				} catch (Exception e) {
					errorLabel.setText("Line " + (i+1) + ": Invalid index!");
					return false;
				}
				if (num < 0 || num > i) {
					errorLabel.setText("Line " + (i+1) + ": Index is out of range!");
					return false;
				}
			}
		}
		errorLabel.setForeground(new Color(15,200,50));
		errorLabel.setText("Success!");
		errorLabel.revalidate();
		return true;
	}
}
