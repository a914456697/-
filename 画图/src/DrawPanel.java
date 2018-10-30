import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
	private BufferedImage huabu;
	private JLabel label;
	private BufferedImage[] history;
	private Line2D.Double line = new Line2D.Double();
	private Ellipse2D.Double oval = new Ellipse2D.Double();
	private Rectangle2D.Double rect = new Rectangle2D.Double();
	private Rectangle2D.Double fillRect = new Rectangle2D.Double();
	int x, y, x1, x2, y1, y2;
	int xx, yy;// 游标的坐标
	private Shape shape = new Line2D.Double();
	private int index;
	private int press;
	private int isEr;
	private Ellipse2D.Double pen = new Ellipse2D.Double();
	public static Stroke stroke = new BasicStroke(RightTool.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

	public DrawPanel() {

		this.setLayout(null);
		this.setBounds(0, 0, Painter.WIDTH * 2 / 3 + 8, Painter.HEIGHT * 2 / 3 + 8);
		// 设置画布
		huabu = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		label = new JLabel(new ImageIcon(huabu));
		label.setBounds(new Rectangle(0, 0, getWidth(), getHeight()));
		label.addMouseMotionListener(this);
		label.addMouseListener(this);
		history = new BufferedImage[1000];

		// 画空白
		Graphics2D g2d = (Graphics2D) huabu.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, huabu.getWidth(), huabu.getHeight());

		history[index] = new BufferedImage(huabu.getWidth(), huabu.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d_history = (Graphics2D) history[index].getGraphics();
		g2d_history.drawImage(huabu, 0, 0, this);
		index++;

		this.add(label);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		xx = e.getX();
		yy = e.getY();
		if (Painter.method == 0 || Painter.method == 1) {
			draw(x1, y1, x2, y2);
			x1 = e.getX();
			y1 = e.getY();
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		xx = x2 = e.getX();
		yy = y2 = e.getY();
		UnderDrawPanel.label.setText(xx+","+yy);
		if (Painter.method == 0 || Painter.method == 1) {
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		press = 1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
			x2 = e.getX();
			y2 = e.getY();
			toDraw();
			press = 0;
			repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		huabu = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		label = new JLabel(new ImageIcon(huabu));
		label.addMouseListener(this);
		label.addMouseMotionListener(this);
		this.removeAll();
		this.add(label);
		label.setBounds(new Rectangle(0, 0, width, height));
		// 画出原本图形
		Graphics2D g2d = (Graphics2D) huabu.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		g2d.drawImage(history[index - 1], 0, 0, this);
		// 将画布添加到history中
		history[index] = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		g2d = (Graphics2D) history[index].getGraphics();
		g2d.drawImage(huabu, width, height, this);

		press = 0;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;
		if (press == 1 && !(x1 < 0 || y1 < 0)) {
			draw(x1, y1, x2, y2);
			if (Painter.method == 1)
				return;
			if(ColorPanel.fill_color!=null) {
				graphics2d.setPaint(ColorPanel.fill_color);
				graphics2d.fill(shape);
			}
			if (ColorPanel.border_color != null) {
				graphics2d.setPaint(ColorPanel.border_color);
				graphics2d.setStroke(stroke);
				graphics2d.draw(shape);
			}
		}
		if (Painter.method == 0) {
			// 跟随鼠标移动的游标
			graphics2d.setPaint(Color.black);
			graphics2d.setStroke(new BasicStroke(1));
			pen.setFrame(xx, yy, 5, 5);
			graphics2d.draw(pen);
		}
		if (Painter.method == 1) {
			// 橡皮擦时跟随鼠标移动的游标
			graphics2d.setPaint(Color.BLACK);
			graphics2d.setStroke(new BasicStroke(1));
			pen.setFrame(xx, yy, RightTool.size, RightTool.size);
			graphics2d.draw(pen);
		}
	}

	// 画画
	private void toDraw() {
		Graphics2D g2d = (Graphics2D) huabu.getGraphics();
		if (Painter.method != 1 && ColorPanel.fill_color != null) {
			g2d.setPaint(ColorPanel.fill_color);
			g2d.fill(shape);
		}
		if (ColorPanel.border_color != null && Painter.method != 1) {
			g2d.setPaint(ColorPanel.border_color);
			g2d.setStroke(stroke);
			g2d.draw(shape);
		}

		// 将画布加入历史画布
		history[index] = new BufferedImage(huabu.getWidth(), huabu.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d2 = (Graphics2D) history[index].getGraphics();
		g2d2.drawImage(huabu, 0, 0, huabu.getWidth(), huabu.getHeight(), null);
		index++;
	}

	// 改变画画的样式
	private void draw(int x1, int y1, int x2, int y2) {
		int method = Painter.method;
		if (method == 0 || method == 1) {
			// 铅笔
			shape = line;
			line.setLine(x1, y1, x2, y2);
			Graphics2D g2 = (Graphics2D) huabu.getGraphics();
			if (method == 0) {// 铅笔
				g2.setPaint(ColorPanel.border_color);
			} else if (method == 1) {// 橡皮擦
				g2.setPaint(Color.WHITE);
			}
			g2.setStroke(stroke);
			g2.draw(shape);

		} else if (method == 2) {
			// 直线
			shape = line;
			line.setLine(x1, y1, x2, y2);
		} else if (method == 3) {
			// 矩形
			shape = rect;
			int w = Math.abs(x2 - x1);
			int h = Math.abs(y2 - y1);
			rect.setRect(x1, x2, w, h);
			if (x2 > x1 && y2 < y1) {
				rect.setFrame(x1, y2, w, h);
			} else if (x2 < x1 && y2 < y1) {
				rect.setFrame(x2, y2, w, h);
			} else if (x2 < x1 && y2 > y1) {
				rect.setFrame(x2, y1, w, h);
			} else {
				rect.setFrame(x1, y1, w, h);
			}
		} else if (method == 4) {
			// 圆形

			shape = oval;
			int w = Math.abs(x2 - x1);
			int h = Math.abs(y2 - y1);
			if (x2 > x1 && y2 < y1) {
				oval.setFrame(x1, y2, w, h);
			} else if (x2 < x1 && y2 < y1) {
				oval.setFrame(x2, y2, w, h);
			} else if (x2 < x1 && y2 > y1) {
				oval.setFrame(x2, y1, w, h);
			} else {
				oval.setFrame(x1, y1, w, h);
			}
		} else if (method == 5) {
			// 曲线
		}
	}
}
