package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener {

    public HomePage() {
        setTitle("多语言工具类");
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
        JButton button6 = new JButton("从B获取翻译文案给A");
        JButton button7 = new JButton("多平台语言合并");

        int widthJPanel = 400;
        int heightJPanel = 270;

        int spacingW = 25;
        int spacingH = 10;
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

        button6.setBounds(spacingW * 3 + widthBtn, spacingH * 5 + heightBtn * 2, spacingH * 3 +  widthBtn,
                heightBtn);

        button7.setBounds(spacingW, spacingH * 5 + heightBtn * 4, widthBtn,
                heightBtn);

        p.add(button1);
        p.add(button2);
        p.add(button3);
        p.add(button4);
        p.add(button5);
        p.add(button6);
        p.add(button7);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds((width - 300) / 2, (height - 300) / 2, widthJPanel,
                heightJPanel);
        setVisible(true);

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        button6.addActionListener(this);
        button7.addActionListener(this);
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
            case "从B获取翻译文案给A":
                new GetTextOfTranslation().setVisible(true);
                break;
            case "多平台语言合并":
                new MultiPplatformXls().setVisible(true);
                break;
        }
    }

}
