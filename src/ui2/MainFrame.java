package ui2;

import UI.XmlWithXls;

import javax.swing.*;
import javax.swing.plaf.ListUI;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        initView();
    }

    private void initView() {
        setTitle("多语言工具类");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screensize.getWidth();
        int height = (int) screensize.getHeight();

        int frameWidth = 800;
        int frameHeight = 500;


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.BLUE);
        tabbedPane.add("xml与Xls互换", new XmlWithXlsPanel());
        tabbedPane.add("xml与Xls互换", new XmlWithXlsPanel());
        tabbedPane.add("xml与Xls互换", new XmlWithXlsPanel());
        tabbedPane.add("xml与Xls互换", new XmlWithXlsPanel());
        tabbedPane.add("xml与Xls互换", new XmlWithXlsPanel());

        setContentPane(tabbedPane);
        setBounds((width - frameWidth) / 2, (height - frameHeight) / 2, frameWidth,
                frameHeight);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

}
