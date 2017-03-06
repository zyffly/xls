import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HomePage().setVisible(true);
	}
}

class HomePage extends JFrame implements ActionListener {

	HomePage() {
		setTitle("HomePage");
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();

		JPanel p = new JPanel();
		p.setLayout(null);
		add(p);

		JButton button1 = new JButton("xml 与 Xls 互换");
		JButton button2 = new JButton("两个Xls合并");
		JButton button3 = new JButton("获取没有全部翻译的ID");
		JButton button4 = new JButton("xml提取指定文案");
		JButton button5 = new JButton("stings文件替换");

		int widthJPanel = 400;
		int heightJPanel = 270;

		int spacingW = 25;
		int spacingH = 30;
		int widthBtn = 150;
		int heightBtn = 30;

		button1.setBounds(spacingW, spacingH, widthBtn, heightBtn);
		button2.setBounds(spacingW * 3 + widthBtn, spacingH, widthBtn,
				heightBtn);
		button3.setBounds(spacingW, spacingH * 3 + heightBtn, widthBtn,
				heightBtn);
		button4.setBounds(spacingW * 3 + widthBtn, spacingH * 3 + heightBtn,
				widthBtn, heightBtn);
		button5.setBounds(spacingW, spacingH * 5 + heightBtn * 2, widthBtn,
				heightBtn);

		p.add(button1);
		p.add(button2);
		p.add(button3);
		p.add(button4);
		p.add(button5);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((width - 300) / 2, (height - 300) / 2, widthJPanel,
				heightJPanel);
		setVisible(true);

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getActionCommand()) {
		case "xml 与 Xls 互换":
			new XmlWithXls().setVisible(true);
			break;
		case "两个Xls合并":
			new ExlAddWindowText().setVisible(true);
			break;
		case "获取没有全部翻译的ID":
			new XlsNoTranslation().setVisible(true);
			break;
		case "xml提取指定文案":
			new XmlExtractToXls().setVisible(true);
			break;
		case "stings文件替换":
			new FileReplace().setVisible(true);
			break;
		}
	}

}
