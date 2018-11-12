import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RightTool extends JPanel implements ActionListener, ChangeListener, ItemListener {
	private boolean isPen;// �ж��Ƿ���Ǧ�ʻ�����Ƥ��
	private JPanel borderPanel, setPanel;
	private JSpinner lineWeight;
	private JCheckBox xxCb;
	public static boolean isXuxian;
	private JRadioButton yjButton, jjButton;
	public JRadioButton xzButton;
	public JRadioButton kfButton;
	public JRadioButton pzButton;
	private Font font2;
	private JLabel xz, kf, pz;
	public static int size = 5;
	private float[] dash = { 5, 6 };

	public RightTool() {
		borderPanel = new JPanel();
		setPanel = new JPanel();
		lineWeight = new JSpinner();
		lineWeight.setValue(5);
		Font font = new Font("���Ŀ���", Font.PLAIN, 18);
		JLabel dx = new JLabel("��С��");
		JLabel xx = new JLabel("���ߣ�");
		JLabel yj = new JLabel("Բ�ǣ�");
		JLabel jj = new JLabel("��ǣ�");
		// ���ñ�ǩ�����С����ʽ
		dx.setFont(font);
		xx.setFont(font);
		yj.setFont(font);
		jj.setFont(font);

		// ����ѡ��򲿷�
		xxCb = new JCheckBox();
		yjButton = new JRadioButton();
		jjButton = new JRadioButton();
		xzButton = new JRadioButton();
		kfButton = new JRadioButton();
		pzButton = new JRadioButton();
		ButtonGroup bg1 = new ButtonGroup();
		ButtonGroup bg2 = new ButtonGroup();
		bg1.add(yjButton);
		bg1.add(jjButton);
		bg2.add(xzButton);
		bg2.add(kfButton);
		bg2.add(pzButton);
		yjButton.setSelected(true);

		// �߿򲿷�
		borderPanel.setLayout(new GridLayout(4, 2, 0, 10));
		borderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)),
				"�߿�", TitledBorder.LEFT, TitledBorder.TOP, font));
		borderPanel.add(dx);
		borderPanel.add(lineWeight);
		borderPanel.add(xx);
		borderPanel.add(xxCb);
		borderPanel.add(yj);
		borderPanel.add(yjButton);
		borderPanel.add(jj);
		borderPanel.add(jjButton);

		// �����趨����
		font2 = new Font("���Ŀ���", Font.PLAIN, 18);
		setPanel.setLayout(new GridLayout(4, 2, 0, 10));
		setPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)),
				"�����趨", TitledBorder.LEFT, TitledBorder.TOP, font));
		xz = new JLabel("��״:");
		xz.setFont(font2);
		kf = new JLabel("����:");
		kf.setFont(font2);
		pz = new JLabel("��״:");
		pz.setFont(font2);
		setPanel.add(xz);
		setPanel.add(xzButton);
		setPanel.add(kf);
		setPanel.add(kfButton);
		setPanel.add(pz);
		setPanel.add(pzButton);
		// ��ťĬ�ϲ��ܵ��
		xzButton.setEnabled(false);
		pzButton.setEnabled(false);
		kfButton.setEnabled(false);
		// ��Ӽ���
		lineWeight.addChangeListener(this);
		xxCb.addItemListener(this);
		yjButton.addItemListener(this);
		jjButton.addItemListener(this);
		xzButton.addItemListener(this);
		kfButton.addItemListener(this);
		pzButton.addItemListener(this);
		this.setLayout(new GridLayout(2, 1, 5, 10));
		this.add(borderPanel);
		this.add(setPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner sp = (JSpinner) e.getSource();
		size = (int) sp.getValue();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (xxCb.isSelected()) {
			isXuxian = true;
		} else if (!xxCb.isSelected()) {
			isXuxian = false;
		}
		if (yjButton.isSelected()) {

		}
		if (jjButton.isSelected()) {

		}
	}

}
