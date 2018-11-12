import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;	
import javax.swing.JFrame;
import javax.swing.JMenu; 
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Painter extends JFrame implements ActionListener {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static boolean isShan;//判断是否是扇形
	public static int method;
	private String menuBar[] = { "文件(F)", "编辑(E)", "视图(V)", "说明(H)" };
	private String menuItem[][] = { { "新建(N)|78", "打开(O)|79", "保存(S)|83", "另存为(A)", "退出(X)|88" },
			{ "撤消(U)|85", "重复(R)|82", "剪切(T)|84", "复制(C)|67", "粘贴(P)|80" },
			{ "工具箱(T)|84", "色块(C)|76", "状态栏(S)", "属性栏(M)" }, { "关于七喜猫猫画板(A)" } };
	private String ButtonName[] = { "铅笔", "橡皮擦", "直线", "矩开", "椭圆", "圆角矩形", "贝氏曲线", "扇型", "多边形", "文字", "选取" };
	private String toolName[] = { "src/img/tool7.gif", "src/img/tool6.gif", "src/img/tool1.gif", "src/img/tool2.gif",
			"src/img/tool3.gif", "src/img/tool4.gif", "src/img/tool5.gif", "src/img/tool8.gif", "src/img/tool9.gif",
			"src/img/tool10.gif", "src/img/tool11.gif" };
	private JMenuBar mb;
	private JMenu[] menu;
	private JMenuItem[][] menuI;
	private JToggleButton[] tgButtons;
	private JPanel rightPanel;
	private JPanel toolPanel;
	private JPanel downPanel;
	private UnderDrawPanel uDP;
	private ColorPanel cPanel;
	private RightTool right_Tool;
	public Painter() {
		mb = new JMenuBar();
		menu = new JMenu[menuBar.length];
		menuI = new JMenuItem[menuItem.length][menuItem[0].length];
		// 菜单
		for (int i = 0; i < menuBar.length; i++) {
			menu[i] = new JMenu(menuBar[i]);
			menu[i].setMnemonic(menuBar[i].split("\\(")[1].charAt(0));
			mb.add(menu[i]);
		}
		// 菜单项
		for (int i = 0; i < menuItem.length; i++) {
			for (int j = 0; j < menuItem[i].length; j++) {
				if (i != 2) {
					if (i == 0 && j == 4 || i == 1 && j == 2) {
						menu[i].addSeparator();
					}
					menuI[i][j] = new JMenuItem(menuItem[i][j].split("\\|")[0]);
					if (menuItem[i][j].split("\\|").length > 1) {
						menuI[i][j].setMnemonic(Integer.parseInt(menuItem[i][j].split("\\|")[1]));
						menuI[i][j].setAccelerator(KeyStroke
								.getKeyStroke(Integer.parseInt(menuItem[i][j].split("\\|")[1]), ActionEvent.CTRL_MASK));
					}
					menu[i].add(menuI[i][j]);
				} else {
					JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(menuItem[i][j].split("\\|")[0]);
					cbmi.setMnemonic(menuItem[i][j].split("\\(")[1].charAt(0));
					menu[i].add(cbmi);
				}
			}
		}
		// 左侧工具栏
		tgButtons = new JToggleButton[ButtonName.length];
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new GridLayout(6, 2));
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < ButtonName.length; i++) {
			// 设置按钮图标
			ImageIcon img = new ImageIcon(toolName[i]);
			tgButtons[i] = new JToggleButton(img);
			tgButtons[i].addActionListener(this);
			tgButtons[i].setFocusable(false);
			bg.add(tgButtons[i]);
			toolBar.add(tgButtons[i]);
		}
		tgButtons[0].setSelected(true);
		// 每个部分装入一个Panel里面
		toolPanel = new JPanel();
		rightPanel = new JPanel();
		downPanel = new JPanel();
		toolPanel.add(toolBar);
		// 添加中间位置的画布
		uDP = new UnderDrawPanel();
		right_Tool = new RightTool();
		rightPanel.add(new RightTool());

		// 下方面板
		cPanel = new ColorPanel();
		downPanel.setLayout(new BorderLayout());
		downPanel.add(new JPanel(), BorderLayout.NORTH);
		downPanel.add(cPanel, BorderLayout.CENTER);
		downPanel.add(uDP.label, BorderLayout.SOUTH);

		this.setJMenuBar(mb);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(downPanel, BorderLayout.SOUTH);
		this.add(uDP, BorderLayout.CENTER);
		this.setTitle("范捷的画图板");
		this.setVisible(true);
		this.setSize(Painter.WIDTH, Painter.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置最大化
		 this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public static void main(String[] args) {
		Painter painter = new Painter();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JToggleButton tb = (JToggleButton)e.getSource();
		for(int i=0;i<tgButtons.length;i++) {
			if(tb.equals(tgButtons[i])) {
				method = i;
			}
		}
	}

}
