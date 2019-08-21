package UI;

import utils.*;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

/**
 * xml 与 Xls 互换
 * @author felix
 *
 */
public class XmlWithXls extends JFrame implements ActionListener {
	JLabel lable1, lable2;
	TextField text1, text2;

	XmlWithXls() {
		super();
		setTitle("xml 与 Xls 互换");
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();

		JPanel p = new JPanel();
		p.setLayout(null);
		add(p);
		lable1 = new JLabel("资源路径：");
		lable2 = new JLabel("生成路径：");

		text1 = new TextField(10);
		text2 = new TextField(10);

		JButton selectButton1 = new JButton("...");
		selectButton1.setActionCommand("select1");
		JButton selectButton2 = new JButton("...");
		selectButton2.setActionCommand("select2");

		JButton button1 = new JButton("xml to xls");
		JButton button2 = new JButton("xls to xml");

		lable1.setBounds(10, 50, 70, 30);
		lable2.setBounds(10, 100, 70, 30);
		text1.setBounds(90, 50, 150, 30);
		text2.setBounds(90, 100, 150, 30);
		selectButton1.setBounds(250, 50, 30, 30);
		selectButton2.setBounds(250, 100, 30, 30);
		button1.setBounds(50, 200, 100, 30);
		button2.setBounds(150, 200, 100, 30);

		p.add(lable1);
		p.add(lable2);
		p.add(text1);
		p.add(text2);
		p.add(selectButton1);
		p.add(selectButton2);
		p.add(button1);
		p.add(button2);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((width - 300) / 2, (height - 300) / 2, 300, 300);
		setVisible(true);

		selectButton1.addActionListener(this);
		selectButton2.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getActionCommand()) {
		case "select1":
			text1.setText(getPath());
			break;
		case "select2":
			text2.setText(getPath());
			break;
		case "xml to xls":
			Singleton.getInstance().setInPutPath(text1.getText());
			Singleton.getInstance().setOutPutPath(text2.getText());
			xmlToXls(Singleton.getInstance().getInPutPath());
			break;
		case "xls to xml":
			Singleton.getInstance().setInPutPath(text1.getText());
			Singleton.getInstance().setOutPutPath(text2.getText());
			xlsToXml(Singleton.getInstance().getInPutPath());
			break;
		default:
			JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	private void xlsToXml(String path) {
		if (!path.endsWith("xls") && !path.endsWith("xlsx")) {
			JOptionPane.showMessageDialog(null, "资源文件请选择xls或者xlsx文件", "标题",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		List<LinkedHashMap<String, String>> mLis = XlsDataPoi.getData(new File(
				path));
		new WriteXml(mLis);
	}

	private static void xmlToXls(String path) {
		try {
			XmlData mXmlData = new XmlData();
			List<LinkedHashMap<String, String>> mData = mXmlData
					.parseFromFolder(new File(path));

			List<LinkedHashMap<Integer, String>> mList = new ArrayList<>();
			List<String> idList = new ArrayList<>();

			for (int i = 0; i < mData.size(); i++) {
				LinkedHashMap<String, String> mMap = mData.get(i);
				Iterator iter = mMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					if (idList.contains(key.toString())) {
						LinkedHashMap<Integer, String> mSaveMap = mList
								.get(idList.indexOf(key.toString()));
						System.out.println(key.toString());
						mSaveMap.put(i + 1, val.toString());
					} else {
						idList.add(key.toString());
						LinkedHashMap<Integer, String> mSaveMap = new LinkedHashMap<>();
						mSaveMap.put(0, key.toString());
						mSaveMap.put(i + 1, val.toString());
						mList.add(mSaveMap);
					}
				}
			}
			new WriteXlsxPoi(mList,"Xml生成的Xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getPath() {
		int result = 0;
		File file = null;
		String path = null;
		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); // 注意了，这里重要的一句
		System.out.println(fsv.getHomeDirectory()); // 得到桌面路径
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("请选择要上传的文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		result = fileChooser.showOpenDialog(getParent());
		if (JFileChooser.APPROVE_OPTION == result) {
			path = fileChooser.getSelectedFile().getPath();
			System.out.println("path: " + path);
		}
		return path;
	}

}
