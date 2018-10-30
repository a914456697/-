import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class ShowColor extends JPanel{

	public ShowColor() {
		this.setBounds(new Rectangle(100, 10, 100, 40));
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(0, 0, ColorPanel.left_color, 100, 40, ColorPanel.right_color, true));
		g2d.fillRect(0, 0, 100, 40);
	}
}
