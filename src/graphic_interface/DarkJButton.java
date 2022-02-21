package graphic_interface;

import javax.swing.*;
import java.awt.*;

/**
 * Dark themed JButton
 */
public class DarkJButton extends JButton {
	private static final Color BG_COLOR = new Color(60,60,72);
	private static final Color HOV_COLOR = new Color(70,70,80);
	private static final Color PRESS_COLOR = new Color(95,95,115);

	public DarkJButton(String text) {
		super(text);
		setForeground(new Color(200, 203, 207));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(40,40,55)),
				BorderFactory.createEmptyBorder(6,12,6,12)));
		setContentAreaFilled(false);
		setFocusPainted(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(PRESS_COLOR);
		} else if (getModel().isRollover()) {
			g.setColor(HOV_COLOR);
		} else {
			g.setColor(BG_COLOR);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
