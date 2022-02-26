package graphic_interface;

import biotic.organic_nodes.ReproductiveNode;
import physics.CollisionGrid;
import simulation.Simulation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creates a code editor for adding new organism codes
 */
public class OrganismCreator extends JPanel {
	private final Simulation sim;
	private final CollisionGrid grid;

	private final JTextPane text;
	private final JLabel errorLabel;
	private final GridPanel gridPanel;

	private static final double SPAWN_RADIUS = 96.0;

	private boolean styleEdit = true;

	public OrganismCreator(GridPanel gridPanel, Simulation sim, CollisionGrid grid) {
		this.sim = sim;
		this.grid = grid;
		this.gridPanel = gridPanel;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(42, 42, 50));

		add(createEditorButtons());

		errorLabel = new JLabel("Type an organism code");
		errorLabel.setForeground(new Color(200, 203, 207));
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(errorLabel);

		text = new JTextPane();
		text.setFont(new Font("Consolas", Font.PLAIN, 16));
		text.setEditable(true);
		text.setBackground(new Color(9, 8, 13));
		text.setForeground(new Color(200, 203, 207));
		text.setCaretColor(new Color(200, 203, 207));

		JScrollPane codeEditor = new JScrollPane(text);
		codeEditor.setBorder(BorderFactory.createEmptyBorder());
		codeEditor.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		codeEditor.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(codeEditor);

		LineNumberingTextArea lineNumberingTextArea = new LineNumberingTextArea(text);
		codeEditor.setRowHeaderView(lineNumberingTextArea);

		SimpleAttributeSet attrs = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs, new Color(200, 203, 207));

		Runnable removeError = () -> {
			errorLabel.setText(" ");
			text.getStyledDocument().setCharacterAttributes(0, text.getText().length()+1, attrs, true);
		};
		text.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent d) {
				lineNumberingTextArea.updateLineNumbers();
				// Only update if this is not a style edit to prevent infinite loops
				if (!isStyleEdit()) {
					SwingUtilities.invokeLater(removeError);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent d) {
				lineNumberingTextArea.updateLineNumbers();
				// Only update if this is not a style edit to prevent infinite loops
				if (!isStyleEdit()) {
					SwingUtilities.invokeLater(removeError);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent d) {
				lineNumberingTextArea.updateLineNumbers();
			}
		});
		text.setText("nod 0");
	}

	/**
	 * Initialize editor buttons
	 * @return JPanel with buttons
	 */
	public JPanel createEditorButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		DarkJButton add = new DarkJButton("Add Organism");
		add.addActionListener(e -> {
			List<String> code = parseInput(text.getText());
			if (validateInput(code)) {
				sim.addOrganicNode(new ReproductiveNode(
						null,
						-gridPanel.getxOffset() + gridPanel.getWidth()/2.0 + (Math.random()-0.5)*SPAWN_RADIUS,
						-gridPanel.getyOffset() + gridPanel.getHeight()/2.0 + (Math.random()-0.5)*SPAWN_RADIUS,
						2.5, code, sim, grid));
			}
		});
		buttonPanel.add(add);

		DarkJButton save = new DarkJButton("Save As");
		save.addActionListener(e -> {
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
		});
		buttonPanel.add(save);

		return buttonPanel;
	}

	/**
	 * Convert text editor's code to a list
	 * @param s the text from the editor
	 * @return a list containing each line
	 */
	private List<String> parseInput(String s) {
		return new ArrayList<>(Arrays.asList(s.split("\n")));
	}

	/**
	 * Ensure the code can create a valid node
	 * @param s the code list
	 * @return true if the code is valid
	 */
	private boolean validateInput(List<String> s) {
		for (int i = 0; i < s.size(); i++) {
			// Ensure instruction is formatted correctly
			String instruction = s.get(i);
			if (!instruction.contains(" ")) {
				raiseError(i, "Invalid spacing");
				return false;
			}

			// Verify that the node type is registered
			String word = instruction.substring(0, instruction.indexOf(' '));
			boolean isValidNode = false;
			for (String validNode : ReproductiveNode.NODE_TYPES) {
				if (validNode.equals(word)) {
					isValidNode = true;
					break;
				}
			}
			if (!isValidNode) {
				raiseError(i, "Undefined node type");
				return false;
			}

			// Check that each index is numeric and in range
			String[] nums = instruction.substring(instruction.indexOf(' ')+1).split(" ");
			for (String sNum : nums) {
				int num;
				try {
					num = Integer.parseInt(sNum);
				} catch (Exception e) {
					raiseError(i, "Index is not a number");
					return false;
				}
				if (num < 0) {
					raiseError(i, "Index is negative");
					return false;
				} else if (num > i) {
					raiseError(i, "Index is out of range");
					return false;
				}
			}
		}
		errorLabel.setForeground(new Color(15,200,150));
		errorLabel.setText("Success!");
		return true;
	}

	/**
	 * Set the errorLabel's help text
	 * @param line the line number the error occurred at
	 * @param error the specific error
	 */
	private void raiseError(int line, String error) {
		SimpleAttributeSet attrs = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs, Color.WHITE);
		StyleConstants.setBackground(attrs, new Color(255,120,120));
		String code = text.getText();
		int offset = 0, lineCnt = 0;
		while (lineCnt != line) {
			int nextLine = code.indexOf('\n');
			code = code.substring(nextLine+1);
			offset += nextLine+1;
			lineCnt++;
		}
		setStyleEdit(true);
		if (!code.contains("\n"))
			code += "\n";
		int end = code.indexOf('\n');
		text.getStyledDocument().setCharacterAttributes(offset, end, attrs, true);
		setStyleEdit(false);
		errorLabel.setForeground(Color.RED);
		errorLabel.setText(String.format("Line %d: " + error, line+1));
	}

	public boolean isStyleEdit() {
		return styleEdit;
	}

	public void setStyleEdit(boolean styleEdit) {
		this.styleEdit = styleEdit;
	}
}
