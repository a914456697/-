import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ColorPanel extends JPanel implements MouseListener, ActionListener {

	public static Paint border_color = new Color(0, 0, 0);
	public static Paint fill_color = null;
	private int rgb[][] = {
			{ 0, 255, 128, 192, 128, 255, 128, 255, 0, 0, 0, 0, 0, 0, 128, 255, 128, 255, 0, 0, 0, 128, 0, 128, 128,
					255, 128, 255, 255, 255, 255, 255 },
			{ 0, 255, 128, 192, 0, 0, 128, 255, 128, 255, 128, 255, 0, 0, 0, 0, 128, 255, 64, 255, 128, 255, 64, 128, 0,
					0, 64, 128, 255, 255, 255, 255 },
			{ 0, 255, 128, 192, 0, 0, 0, 0, 0, 0, 128, 255, 128, 255, 128, 255, 64, 128, 64, 128, 255, 255, 128, 255,
					255, 128, 0, 64, 255, 255, 255, 255 } };

	private int r, g, b;
	private JPanel leftPanel, rightPanel, borderPanel, fillPanel;
	private JPanel[] tsbPanel;
	private BufferedImage borderImg = new BufferedImage(16, 16, BufferedImage.TYPE_3BYTE_BGR);
	private BufferedImage fillImg = new BufferedImage(16, 16, BufferedImage.TYPE_3BYTE_BGR);
	private JLabel borderLabel = new JLabel(new ImageIcon(borderImg));
	private JLabel fillLabel = new JLabel(new ImageIcon(fillImg));
	private JLabel special[] = new JLabel[4];
	private String[] imgURL = { "./src/img/icon1.gif", "./src/img/icon2.gif", "./src/img/icon3.gif",
			"./src/img/icon4.gif" };
	private JDialog jianbian;
	private JButton left, right, ok, cancel;
	private ShowColor jb_panel;
	private BufferedImage jb_img = new BufferedImage(100, 40, BufferedImage.TYPE_3BYTE_BGR);
	private JLabel jb_lb = new JLabel(new ImageIcon(jb_img));
	public static Color left_color = Color.BLACK;
	public static Color right_color = Color.WHITE;
	private boolean isOk;// 是否选择了渐变颜色

	public ColorPanel() {
		this.setLayout(null);
		// 初始化
		jianbian = new JDialog();
		left = new JButton("LEFT");
		right = new JButton("RIGHT");
		ok = new JButton("OK");
		cancel = new JButton("cancel");
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		borderPanel = new JPanel();
		fillPanel = new JPanel();
		tsbPanel = new JPanel[rgb[0].length];

		// 设置左边显示颜色块
		leftPanel.setLayout(null);
		leftPanel.setBounds(0, 0, 40, 40);
		// one
		Graphics2D g2dBorder = (Graphics2D) borderImg.getGraphics();
		g2dBorder.setColor(Color.black);
		g2dBorder.fillRect(0, 0, 18, 18);
		borderPanel.add(borderLabel);
		borderPanel.setLayout(new GridLayout(1, 1));
		borderPanel.setBounds(6, 6, 18, 18);
		borderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		// two
		Graphics2D g2dFill = (Graphics2D) fillImg.getGraphics();
		g2dFill.setColor(Color.white);
		g2dFill.fillRect(0, 0, 18, 18);
		g2dFill.setColor(Color.red);
		g2dFill.drawLine(0, 0, 18, 18);
		g2dFill.drawLine(0, 18, 18, 0);
		fillPanel.add(fillLabel);
		fillPanel.setLayout(new GridLayout(1, 1));
		fillPanel.setBounds(16, 16, 18, 18);
		fillPanel.setBorder(BorderFactory.createLoweredBevelBorder());

		leftPanel.add(borderPanel);
		leftPanel.add(fillPanel);
		leftPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		// 右边
		rightPanel.setBounds(50, 0, 16 * 16, 32);
		rightPanel.setLayout(new GridLayout(2, rgb[0].length));
		for (int i = 0; i < rgb[0].length; i++) {
			tsbPanel[i] = new JPanel();
			tsbPanel[i].addMouseListener(this);
			tsbPanel[i].setBackground(new Color(rgb[0][i], rgb[1][i], rgb[2][i]));
			tsbPanel[i].setBorder(BorderFactory.createLoweredBevelBorder());
		}
		// 添加右边四个特殊的块
		ImageIcon imageIcon[] = new ImageIcon[4];
		for (int i = 0; i < 4; i++) {
			imageIcon[i] = new ImageIcon("src/img/icon" + (i + 1) + ".gif");
			imageIcon[i].getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
			// special[i] = new JLabel(new
			// ImageIcon(imageIcon[i].getImage().getScaledInstance(16, 16,
			// Image.SCALE_DEFAULT)));
			special[i] = new JLabel(imageIcon[i]);
		}
		tsbPanel[28].add(special[0]);
		tsbPanel[29].add(special[1]);
		tsbPanel[30].add(special[2]);
		tsbPanel[31].add(special[3]);
		for (int i = 0; i < rgb[0].length; i++) {
			if (i % 2 == 0) {
				rightPanel.add(tsbPanel[i]);
			}
		}
		for (int i = 0; i < rgb[0].length; i++) {
			if (i % 2 == 1) {
				rightPanel.add(tsbPanel[i]);
			}
		}
		// 添加 渐变颜色选择器
		jianbian.setLayout(null);
		jianbian.setModal(true);
		jianbian.setBounds(new Rectangle(300, 200, 300, 200));
		jianbian.add(left);
		jianbian.add(right);
		jianbian.add(ok);
		jianbian.add(cancel);

		jb_panel = new ShowColor();
		jb_panel.add(jb_lb);

		jianbian.add(jb_panel);
		left.setFocusable(false);
		right.setFocusable(false);
		ok.setFocusable(false);
		cancel.setFocusable(false);
		left.setBounds(new Rectangle(10, 10, 70, 40));
		right.setBounds(new Rectangle(210, 10, 70, 40));
		ok.setBounds(new Rectangle(40, 100, 80, 40));
		cancel.setBounds(new Rectangle(180, 100, 80, 40));

		// 添加监听
		left.addActionListener(this);
		right.addActionListener(this);
		ok.addActionListener(this);
		cancel.addActionListener(this);

		this.add(leftPanel);
		this.add(rightPanel);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(16 * 16, 40);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object obj = e.getSource();
		Graphics2D g2d = (Graphics2D) borderImg.getGraphics();
		Graphics2D g2d2 = (Graphics2D) fillImg.getGraphics();
		for (int i = 0; i < 32; i++) {
			if (obj.equals(tsbPanel[i])) {
				if (i >= 28) {
					// if (e.getButton() == 1)
					// g2d.drawImage(new ImageIcon(imgURL[i % 14]).getImage(), 0, 0, 18, 18, null);
					// else if (e.getButton() == 3)
					// g2d2.drawImage(new ImageIcon(imgURL[i % 14]).getImage(), 0, 0, 18, 18, null);
					if (i == 28) {
						// ×
						if (e.getButton() == 1) {
							border_color = null;
							g2d.setPaint(Color.WHITE);
							g2d.fillRect(0, 0, 18, 18);
							g2d.setPaint(Color.RED);
							g2d.drawLine(0, 0, 18, 18);
							g2d.drawLine(0, 18, 18, 0);
						} else if (e.getButton() == 3) {
							fill_color = null;
							g2d2.setPaint(Color.WHITE);
							g2d2.fillRect(0, 0, 20, 20);
							g2d2.setPaint(Color.RED);
							g2d2.drawLine(0, 0, 18, 18);
							g2d2.drawLine(0, 18, 18, 0);
						}
					}
					if (i == 29) {
						// 渐变颜色
						Graphics2D g2;
						jianbian.setVisible(true);
						if (isOk) {
							if (e.getButton() == 1) {
								border_color = new GradientPaint(0, 0, left_color, 18, 18, right_color, true);
								g2d.setPaint(new GradientPaint(0, 0, left_color, 18, 18, right_color, true));
								g2d.fillRect(0, 0, 100, 40);
							} else {
								fill_color = new GradientPaint(0, 0, left_color, 18, 18, right_color, true);
								g2d2.setPaint(new GradientPaint(0, 0, left_color, 18, 18, right_color, true));
								g2d2.fillRect(0, 0, 100, 40);
							}
						}
					}
					if (i == 30) {
						// 选择图片填充
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("图片(*.jpg,*.png,*.jpeg)", "jpg","png","jpeg"));
						int flag = fileChooser.showOpenDialog(null);
						if (flag == 1)
							return;
						File iconName = fileChooser.getSelectedFile();//路径
						ImageIcon icon = new ImageIcon(iconName.getAbsolutePath());
						BufferedImage buf = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_3BYTE_BGR);
						Graphics2D g2 = buf.createGraphics();
						g2.setColor(Color.WHITE);
						g2.fillRect(0, 0, 18, 18);
						g2.drawImage(icon.getImage(), 0,0,this);
						if(e.getButton()==1) {
							g2d.drawImage(buf, 0, 0, 18, 18, this);
							border_color = new TexturePaint(buf, new Rectangle(20, 20));
						}else if(e.getButton()==3) {
							g2d2.drawImage(buf, 0, 0, 18, 18, this);
							fill_color = new TexturePaint(buf, new Rectangle(20, 20));
						}
					}
					if (i == 31) {
						// 文字
						String wenzi = JOptionPane.showInputDialog("请输入文字：");
						if (wenzi != null) {
							// 设置文字颜色
							Color font_color = new Color(0, 0, 0);
							font_color = JColorChooser.showDialog(null, "选择文字颜色", Color.black);
							BufferedImage buf = new BufferedImage(18, 18, BufferedImage.TYPE_3BYTE_BGR);

							if (e.getButton() == 1) {
								border_color = new TexturePaint(buf, new Rectangle(18, 18));
								g2d.setPaint(Color.WHITE);
								g2d.fillRect(0, 0, 18, 18);
								g2d.setPaint(Color.BLACK);
								g2d.drawString("字", 3, 12);
							} else if (e.getButton() == 3) {
								g2d2.setPaint(Color.WHITE);
								g2d2.fillRect(0, 0, 18, 18);
								g2d2.setPaint(Color.BLACK);
								g2d2.drawString("字", 3, 12);
							}
						}
					}
				} else {
					r = rgb[0][i];
					g = rgb[1][i];
					b = rgb[2][i];
					if (e.getButton() == 1) {
						border_color = new Color(r, g, b);
						g2d.setPaint(border_color);
						g2d.fillRect(0, 0, 18, 18);
					} else if (e.getButton() == 3) {
						fill_color = new Color(r, g, b);
						g2d2.setPaint(fill_color);
						g2d2.fillRect(0, 0, 18, 18);
					}
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(left)) {
			left_color = JColorChooser.showDialog(null, "左边渐变颜色", Color.BLACK);
			jb_panel.repaint();
		} else if (obj.equals(right)) {
			right_color = JColorChooser.showDialog(null, "右边渐变颜色", Color.white);
			jb_panel.repaint();
		} else if (obj.equals(ok)) {
			isOk = true;
			jianbian.dispose();
		} else if (obj.equals(cancel)) {
			isOk = false;
			jianbian.dispose();
		}
	}
}