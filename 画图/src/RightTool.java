import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class RightTool extends JPanel implements ActionListener ,ChangeListener{
	private boolean isPen;// �ж��Ƿ���Ǧ�ʻ�����Ƥ��
	private boolean isShan;// �ж��Ƿ�������
	private JPanel borderPanel, setPanel;
	private JSpinner lineWeight;
	private JCheckBox xxCb;
	private JRadioButton yjButton,jjButton;
	private Font font2;
	private JLabel xz,kf,pz;
	public static int size = 5;
	public RightTool() {
		borderPanel = new JPanel();
		setPanel = new JPanel();
		lineWeight = new JSpinner();
		lineWeight.setValue(5);
		Font font = new Font("���Ŀ���",Font.PLAIN,18);
		JLabel dx = new JLabel("��С��");
		JLabel xx = new JLabel("���ߣ�");
		JLabel yj = new JLabel("Բ�ǣ�");
		JLabel jj = new JLabel("��ǣ�");
		//���ñ�ǩ�����С����ʽ
		dx.setFont(font);
		xx.setFont(font);
		yj.setFont(font);
		jj.setFont(font);
		
		//����ѡ��򲿷�
		xxCb = new JCheckBox();
		yjButton = new JRadioButton();
		jjButton = new JRadioButton();
		ButtonGroup bg = new ButtonGroup();
		bg.add(yjButton);
		bg.add(jjButton);
		yjButton.setSelected(true);
		
		//�߿򲿷�
		borderPanel.setLayout(new GridLayout(4, 2,0,10));
		borderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(204,204,204)) ,"�߿�",TitledBorder.LEFT,TitledBorder.TOP,font));
		borderPanel.add(dx);
		borderPanel.add(lineWeight);
		borderPanel.add(xx);
		borderPanel.add(xxCb);
		borderPanel.add(yj);
		borderPanel.add(yjButton);
		borderPanel.add(jj);
		borderPanel.add(jjButton);
		
		//�����趨����
		font2 = new Font("���Ŀ���",Font.PLAIN,18);
		setPanel.setLayout(new GridLayout(4,2,0,10));
		setPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(204,204,204)) ,"�����趨",TitledBorder.LEFT,TitledBorder.TOP,font));
		xz = new JLabel("��״:");
		xz.setFont(font2);
		kf = new JLabel("����:");
		kf.setFont(font2);
		pz = new JLabel("��״:");
		pz.setFont(font2);
		setPanel.add(xz);
		setPanel.add(kf);
		setPanel.add(pz);
		
		//��Ӽ���
		lineWeight.addChangeListener(this);
		
		this.setLayout(new GridLayout(2,1,5,10));
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
		DrawPanel.stroke = new BasicStroke(size,BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		
	}

}
