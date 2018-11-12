import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UnderDrawPanel extends JPanel implements MouseListener, MouseMotionListener {
	public static final int Width = Painter.WIDTH * 2 / 3;
	public static final int Height = Painter.HEIGHT * 2 / 3;

	public JPanel ctrl_right;
	public JPanel ctrl_left;
	public JPanel ctrl_down;
	public static JLabel label;
	float data[] = { 2 };
	// 坐标
	private int x, y;
	private DrawPanel drawPanel = new DrawPanel();

	public UnderDrawPanel() {
		// 状态栏
		label = new JLabel("111,222");
		// 三个小块来控制整个Panel的大小
		ctrl_right = new JPanel();
		ctrl_right.setBackground(new Color(0, 0, 0));
		ctrl_right.setBounds(new Rectangle(Width + 8, Height / 2 + 8, 8, 8));
		ctrl_right.addMouseListener(this);
		ctrl_right.addMouseMotionListener(this);

		ctrl_left = new JPanel();
		ctrl_left.setBounds(Width / 2 + 8, Height + 8, 8, 8);
		ctrl_left.addMouseMotionListener(this);
		ctrl_left.setBackground(new Color(0, 0, 0));
		ctrl_left.addMouseListener(this);

		ctrl_down = new JPanel();
		ctrl_down.setBounds(Width + 8, Height + 8, 8, 8);
		ctrl_down.addMouseMotionListener(this);
		ctrl_down.setBackground(new Color(0, 0, 0));
		ctrl_down.addMouseListener(this);

		this.setLayout(null);
		this.add(ctrl_down);
		this.add(ctrl_left);
		this.add(ctrl_right);
		this.add(drawPanel);

		this.setBackground(Color.gray);
	}

	// 绘制虚线框
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);

		g2d.setPaint(new Color(128, 128, 128));
		//绘制虚线
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10, data, 0));
		g2d.draw(new Rectangle2D.Double(-1, -1, x, y));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// 拖动

		Object panel = e.getSource();
		if (panel.equals(ctrl_left)) {
			x = ctrl_down.getX();
			y = e.getY() + ctrl_left.getY();
		} else if (panel.equals(ctrl_right)) {
			x = ctrl_right.getX() + e.getX();
			y = ctrl_down.getY();
		} else if (panel.equals(ctrl_down)) {
			x = e.getX() + ctrl_down.getX();
			y = e.getY() + ctrl_down.getY();
		}
		repaint();
		label.setText(x + "," + y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x1 = 0, y1 = 0;
		Object panel = e.getSource();
		if (panel.equals(ctrl_left)) { // 改变三个小块的位置
			System.out.println(ctrl_left.getX() + "..." + ctrl_left.getY());
			ctrl_left.setLocation(ctrl_left.getX(), ctrl_left.getY() + e.getY());
			ctrl_right.setLocation(ctrl_right.getX(), ctrl_right.getY() + e.getY() / 2);
			ctrl_down.setLocation(ctrl_down.getX(), ctrl_down.getY() + e.getY());
			x1 = drawPanel.getWidth();
			y1 = e.getY() + drawPanel.getHeight();

		} else if (panel.equals(ctrl_right)) {
			ctrl_right.setLocation(ctrl_right.getX() + e.getX(), ctrl_right.getY());
			ctrl_left.setLocation(ctrl_left.getX() + e.getX() / 2, ctrl_left.getY());
			ctrl_down.setLocation(ctrl_down.getX() + e.getX(), ctrl_down.getY()); // 画布大小
			x1 = e.getX() + drawPanel.getWidth();
			y1 = drawPanel.getHeight();
		} else if (panel.equals(ctrl_down)) {
			ctrl_down.setLocation(ctrl_down.getX() + e.getX(), ctrl_down.getY() + e.getY());
			ctrl_right.setLocation(ctrl_right.getX() + e.getX(), ctrl_right.getY() + e.getY() / 2);
			ctrl_left.setLocation(ctrl_left.getX() + e.getX() / 2, ctrl_left.getY() + e.getY());
			x1 = e.getX() + drawPanel.getWidth();
			y1 = e.getY() + drawPanel.getHeight();
		}
		drawPanel.setSize(x1, y1);
		drawPanel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
