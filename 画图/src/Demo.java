import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Demo extends JFrame{

	public Demo() {
		JPanel panel = new JPanel();
		JLabel jLabel = new JLabel("111");
		JColorChooser cc = new JColorChooser();
		jLabel.setBounds(new Rectangle(0, 0, 100, 100));
		panel.setLayout(null);
		panel .add(jLabel);
		JColorChooser.showDialog(this, "ÑÕÉ«Ñ¡Ôñ", Color.BLACK);
		this.add(cc);
		
		this.add(panel);
		this.setVisible(true);
		this.setSize(100, 100);
	}

	public static void main(String[] args) {
		new Demo();
	}

}
