package graphic_interface;

import biotic.organic_nodes.ReproductiveNode;
import physics.CollisionGrid;
import simulation.Simulation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(42, 42, 50));

		text = new JTextArea(28, 16);
		text.setFont(new Font("Consolas", Font.PLAIN, 16));
		text.setEditable(true);
		text.setBackground(new Color(9, 8, 13));
		text.setForeground(new Color(200, 203, 207));
		text.setCaretColor(new Color(200, 203, 207));

		JScrollPane codeEditor = new JScrollPane(text);
		codeEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		codeEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(codeEditor);

		LineNumberingTextArea lineNumberingTextArea = new LineNumberingTextArea(text);
		codeEditor.setRowHeaderView(lineNumberingTextArea);

		text.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				lineNumberingTextArea.updateLineNumbers();
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				lineNumberingTextArea.updateLineNumbers();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent) {
				lineNumberingTextArea.updateLineNumbers();
			}
		});
		text.setText("nod 0");

		JPanel textButtonContainer = new JPanel();
		textButtonContainer.setLayout(new BoxLayout(textButtonContainer, BoxLayout.X_AXIS));

		JButton add = new JButton("Add Organism");
		add.setBackground(new Color(60,60,72));
		add.setForeground(new Color(200, 203, 207));
		add.setFocusPainted(false);
		add.addActionListener(this);
		textButtonContainer.add(add);

		JButton save = new JButton("Save As");
		save.setBackground(new Color(60,60,72));
		save.setForeground(new Color(200, 203, 207));
		save.setFocusPainted(false);
		save.addActionListener(this);
		textButtonContainer.add(save);

		add(textButtonContainer);

		errorLabel = new JLabel("Type an organism code");
		errorLabel.setForeground(new Color(200, 203, 207));
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(errorLabel);
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
						sim,
						grid));
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
