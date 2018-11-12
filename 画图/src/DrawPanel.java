import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener, ItemListener {
	private BufferedImage huabu;
	private JLabel label;
	private BufferedImage[] history;
	private Line2D.Double line = new Line2D.Double();// 直线
	private Ellipse2D.Double oval = new Ellipse2D.Double();// 圆形
	private Rectangle2D.Double rect = new Rectangle2D.Double();// 矩形
	private CubicCurve2D.Double cubicCurve = new CubicCurve2D.Double();// 贝氏曲线
	private RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double();// 圆角矩形
	private Arc2D.Double arc = new Arc2D.Double();// 扇形
	private Polygon polygon = new Polygon();// 多边形
	int x, y, x1, x2, y1, y2;
	int xx, yy;// 游标的坐标
	private Shape shape = new Line2D.Double();
	private int index;
	private int press;
	private int isEr;
	private Ellipse2D.Double pen = new Ellipse2D.Double();
	private float[] dash = { 5, 5 };
	public static Stroke stroke = new BasicStroke(RightTool.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
	public static final int W = Painter.WIDTH * 2 / 3 + 8;
	public static final int H = Painter.HEIGHT * 2 / 3 + 8;
	int step;// 控制贝氏曲线的步骤
	int t_x1, t_y1, t_x2, t_y2, t_x3, t_y3, t_x4, t_y4;// 贝氏曲线的点位置
	private double center_x;
	private double center_y;
	private double start;
	private double end;
	private int click;
	private int first;
	private String[] zixing = { "宋体", "华文楷体" };
	private JDialog dialog;// 文字选择
	private JTextField tf = new JTextField("加V看学习视频");
	private JTextField tf2 = new JTextField("华文楷体");
	private JSpinner js = new JSpinner();
	private JCheckBox ct = new JCheckBox("粗体", true), xt = new JCheckBox("斜体", true);
	private JButton bn1 = new JButton("确定"), bn2 = new JButton("取消");
	private int s_xt, s_ct;

	public DrawPanel() {

		this.setLayout(null);
		this.setBounds(0, 0, W, H);
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

		// 画文字
		ct.addItemListener(this);
		xt.addItemListener(this);
		js.setValue(100);
		JPanel p = new JPanel();
		p.add(ct);
		p.add(xt);
		bn1.addActionListener(this);
		bn2.addActionListener(this);
		dialog = new JDialog();
		dialog.setTitle("设置文字大小、样式");
		dialog.setModal(true);
		dialog.setLayout(new GridLayout(5, 2));
		dialog.add(new JLabel("内容"));
		dialog.add(tf);
		dialog.add(new JLabel("样式"));
		dialog.add(tf2);
		dialog.add(new JLabel("大小"));
		dialog.add(js);
		dialog.add(new JLabel("属性"));
		dialog.add(p);
		dialog.add(bn1);
		dialog.add(bn2);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setSize(250, 200);

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
		if (Painter.method != 9)
			repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		xx = x2 = e.getX();
		yy = y2 = e.getY();
		UnderDrawPanel.label.setText(xx + "," + yy);
		click = 0;
		if (Painter.method == 0 || Painter.method == 1) {
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (click == 1) {
			toDraw();
		}
		click = 1;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		if (first == 0) {
			polygon = new Polygon();
			polygon.addPoint(x1, x2);
			first = 1;
		}
		press = 1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		
		if (Painter.method == 8 && click != 1) {
			polygon.addPoint(x2, y2);
			repaint();
		}
		if (Painter.method == 6 || Painter.method == 7)
			step++;
		if ((Painter.method == 6 && step == 3) || (Painter.method == 7 && step == 3) || Painter.method == 0
				|| Painter.method == 1 || Painter.method == 2 || Painter.method == 3 || Painter.method == 4
				|| Painter.method == 5 || Painter.method == 9 || Painter.method == 10) {
			toDraw();
			press = 0;
			step = 0;
		}
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
			if (ColorPanel.fill_color != null) {
				graphics2d.setPaint(ColorPanel.fill_color);
				graphics2d.fill(shape);
			}
			if (ColorPanel.border_color != null) {
				graphics2d.setPaint(ColorPanel.border_color);
				if (RightTool.isXuxian)
					DrawPanel.stroke = new BasicStroke(RightTool.size, BasicStroke.CAP_BUTT, BasicStroke.CAP_BUTT,
							10.0f, dash, 0);
				else {
					stroke = new BasicStroke(RightTool.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
				}
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
		first = 0;// 开始画下一个多边形
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
			// 圆角
			shape = roundRect;
			int w = Math.abs(x2 - x1);
			int h = Math.abs(y2 - y1);
			if (x2 > x1 && y2 < y1) {
				roundRect.setRoundRect(x1, y2, w, h, 10.0, 10.0);
			} else if (x2 < x1 && y2 < y1) {
				roundRect.setRoundRect(x2, y2, w, h, 10.0, 10.0);
			} else if (x2 < x1 && y2 > y1) {
				roundRect.setRoundRect(x2, y1, w, h, 10.0, 10.0);
			} else {
				roundRect.setRoundRect(x1, y1, w, h, 10.0, 10.0);
			}
		} else if (method == 6) {
			// 贝氏曲线
			shape = cubicCurve;
			if (step == 0) {
				cubicCurve.setCurve(x1, y1, x1, y1, x2, y2, x2, y2);
				t_x1 = x1;
				t_y1 = y1;
				t_x4 = x2;
				t_y4 = y2;
			} else if (step == 1) {
				cubicCurve.setCurve(t_x1, t_y1, x2, y2, x2, y2, t_x4, t_y4);
				t_x2 = x2;
				t_y2 = y2;
			} else if (step == 2) {
				cubicCurve.setCurve(t_x1, t_y1, t_x2, t_y2, x2, y2, t_x4, t_y4);
			}
		} else if (method == 7) {
			// 扇形
			if (step == 0) {
				shape = oval;
				oval.setFrame(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y2 - y1));
				t_x1 = x1;
				t_y1 = y1;
				t_x2 = x2;
				t_y2 = y2;
			} else if (step == 1) {
				shape = arc;
				//找到扇形的中点
				center_x = Math.min(t_x1, t_x2) + Math.abs(t_x2 - t_x2) / 2;
				center_y = Math.min(t_y1, t_y2) + Math.abs(t_y1 - t_y2) / 2;
				double a = Math.pow(Math.pow(x2 - center_x, 2) + Math.pow(y2 - center_y, 2), 0.5);
				double b = x2 - center_x;
				if (y2 > center_y)
					start = 360 + Math.acos(b / a) / Math.PI * -180;
				else
					start = Math.acos(b / a) / Math.PI * 180;

				arc.setArc(Math.min(t_x1, t_x2), Math.min(t_y1, t_y2), Math.abs(t_x1 - t_x2), Math.abs(t_y1 - t_y2),
						start, 0, Arc2D.PIE);
			} else if (step == 2) {
				shape = arc;

				double a = Math.pow(Math.pow(x2 - center_x, 2) + Math.pow(y2 - center_y, 2), 0.5);
				double b = x2 - center_x;
				if (y2 > center_y)
					end = 360 + Math.acos(b / a) / Math.PI * -180 - start;
				else
					end = Math.acos(b / a) / Math.PI * 180 - start;
				if (end < 0) {
					end = 360 - Math.abs(end);
				}

				arc.setArc(Math.min(t_x1, t_x2), Math.min(t_y1, t_y2), Math.abs(t_x1 - t_x2), Math.abs(t_y1 - t_y2),
						start, end, Arc2D.PIE);
			}
		} else if (method == 8) {
			// 多边形
			shape = polygon;
		} else if (method == 9) {
			// 文字框
			dialog.setVisible(true);

			Graphics2D g2d = huabu.createGraphics();
			FontRenderContext frc = g2d.getFontRenderContext();
			Font font = new Font(tf2.getText(), s_ct + s_xt, (int) js.getValue());
			String str = tf.getText();
			TextLayout tl = new TextLayout(str, font, frc);
			double sw = tl.getBounds().getWidth();
			double sh = tl.getBounds().getHeight();

			AffineTransform at = AffineTransform.getScaleInstance(1, 1);// 获取缩放的字体
			at.translate(x2, y2 + sh);// 在指定位置画出文字
			shape = tl.getOutline(at);
		} else {
			// 虚线框
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.setVisible(false);
		if (e.getSource().equals(bn1)) {
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// 选中粗体属性
		if (e.getItem().equals(ct)) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				s_ct = Font.BOLD;
			} else {
				s_ct = Font.PLAIN;
			}
		}
		if (e.getItem().equals(xt)) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				s_xt = Font.ITALIC;
			} else {
				s_xt = Font.PLAIN;
			}
		}
	}
}
