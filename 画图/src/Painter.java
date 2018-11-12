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
	public static boolean isShan;//�ж��Ƿ�������
	public static int method;
	private String menuBar[] = { "�ļ�(F)", "�༭(E)", "��ͼ(V)", "˵��(H)" };
	private String menuItem[][] = { { "�½�(N)|78", "��(O)|79", "����(S)|83", "���Ϊ(A)", "�˳�(X)|88" },
			{ "����(U)|85", "�ظ�(R)|82", "����(T)|84", "����(C)|67", "ճ��(P)|80" },
			{ "������(T)|84", "ɫ��(C)|76", "״̬��(S)", "������(M)" }, { "������ϲèè����(A)" } };
	private String ButtonName[] = { "Ǧ��", "��Ƥ��", "ֱ��", "�ؿ�", "��Բ", "Բ�Ǿ���", "��������", "����", "�����", "����", "ѡȡ" };
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
		// �˵�
		for (int i = 0; i < menuBar.length; i++) {
			menu[i] = new JMenu(menuBar[i]);
			menu[i].setMnemonic(menuBar[i].split("\\(")[1].charAt(0));
			mb.add(menu[i]);
		}
		// �˵���
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
		// ��๤����
		tgButtons = new JToggleButton[ButtonName.length];
		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new GridLayout(6, 2));
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < ButtonName.length; i++) {
			// ���ð�ťͼ��
			ImageIcon img = new ImageIcon(toolName[i]);
			tgButtons[i] = new JToggleButton(img);
			tgButtons[i].addActionListener(this);
			tgButtons[i].setFocusable(false);
			bg.add(tgButtons[i]);
			toolBar.add(tgButtons[i]);
		}
		tgButtons[0].setSelected(true);
		// ÿ������װ��һ��Panel����
		toolPanel = new JPanel();
		rightPanel = new JPanel();
		downPanel = new JPanel();
		toolPanel.add(toolBar);
		// ����м�λ�õĻ���
		uDP = new UnderDrawPanel();
		right_Tool = new RightTool();
		rightPanel.add(new RightTool());

		// �·����
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
		this.setTitle("���ݵĻ�ͼ��");
		this.setVisible(true);
		this.setSize(Painter.WIDTH, Painter.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// �������
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
