package graphic_interface;

import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;

/**
 * Adapted from user bullywiiplaza's post: https://stackoverflow.com/questions/18768649/java-code-to-display-lines-number-in-jtextarea
 */
public class LineNumberingTextArea extends JTextArea {
	private final JTextArea textArea;

	public LineNumberingTextArea(JTextArea textArea) {
		this.textArea = textArea;
		setBackground(new Color(42, 42, 50));
		setForeground(new Color(125, 125, 130));
		setEditable(false);
		setFont(new Font("Consolas", Font.PLAIN, 16));
	}

	public void updateLineNumbers() {
		String lineNumbersText = getLineNumbersText();
		setText(lineNumbersText);
	}

	private String getLineNumbersText() {
		int caretPosition = textArea.getDocument().getLength();
		Element root = textArea.getDocument().getDefaultRootElement();
		StringBuilder lineNumbersTextBuilder = new StringBuilder();
		lineNumbersTextBuilder.append(String.format("%2d", 1)).append(System.lineSeparator());

		for (int elementIndex = 2; elementIndex < root.getElementIndex(caretPosition) + 2; elementIndex++)
			lineNumbersTextBuilder.append(String.format("%2d", elementIndex)).append(System.lineSeparator());

		return lineNumbersTextBuilder.toString();
	}
}