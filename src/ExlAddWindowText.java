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
 * 两个Xls合并
 * 
 * @author felix
 * 
 */
public class ExlAddWindowText extends JFrame implements ActionListener {
	JLabel lable1, lable2, lable3;
	TextField text1, text2, text3;

	ExlAddWindowText() {
		super();
		setTitle("两个Xls合并");
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();

		JPanel p = new JPanel();
		p.setLayout(null);
		add(p);
		lable1 = new JLabel("资源路径1：");
		lable2 = new JLabel("资源路径2：");
		lable3 = new JLabel("生成路径：");

		text1 = new TextField(10);
		text2 = new TextField(10);
		text3 = new TextField(10);

		JButton selectButton1 = new JButton("...");
		selectButton1.setActionCommand("select1");
		JButton selectButton2 = new JButton("...");
		selectButton2.setActionCommand("select2");
		JButton selectButton3 = new JButton("...");
		selectButton3.setActionCommand("select3");

		JButton button1 = new JButton("xls_add");

		lable1.setBounds(10, 50, 70, 30);
		lable2.setBounds(10, 100, 70, 30);
		lable3.setBounds(10, 150, 70, 30);
		text1.setBounds(90, 50, 150, 30);
		text2.setBounds(90, 100, 150, 30);
		text3.setBounds(90, 150, 150, 30);
		selectButton1.setBounds(250, 50, 30, 30);
		selectButton2.setBounds(250, 100, 30, 30);
		selectButton3.setBounds(250, 150, 30, 30);
		button1.setBounds(50, 200, 100, 30);

		p.add(lable1);
		p.add(lable2);
		p.add(lable3);
		p.add(text1);
		p.add(text2);
		p.add(text3);
		p.add(selectButton1);
		p.add(selectButton2);
		p.add(selectButton3);
		p.add(button1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((width - 300) / 2, (height - 300) / 2, 300, 300);
		setVisible(true);

		selectButton1.addActionListener(this);
		selectButton2.addActionListener(this);
		selectButton3.addActionListener(this);
		button1.addActionListener(this);

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
		case "select3":
			text3.setText(getPath());
			break;
		case "xls_add":
			Singleton.getInstance().setInPutPath(text1.getText());
			Singleton.getInstance().setInPutPath2(text2.getText());
			Singleton.getInstance().setOutPutPath(text3.getText());
			getXlsData();
			break;
		default:
			JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	private void getXlsData() {
		List<LinkedHashMap<Integer, String>> mLis1 = getInXlsData(Singleton
				.getInstance().getInPutPath());
		List<LinkedHashMap<Integer, String>> mLis2 = getInXlsData(Singleton
				.getInstance().getInPutPath2());
		List<LinkedHashMap<Integer, String>> mLis3 = new ArrayList<LinkedHashMap<Integer, String>>();

		LinkedHashMap<Integer, String> mMap1 = mLis1.get(0);
		LinkedHashMap<Integer, String> mMap2 = mLis2.get(0);
		for (int j = 1; j < mLis1.size(); j++) {
			LinkedHashMap<Integer, String> map1 = mLis1.get(j);
			for (int i = 1; i < mLis2.size(); i++) {
				LinkedHashMap<Integer, String> map2 = mLis2.get(i);
				System.out.println(map1.get(i) + "------" + map1.get(0));
				if (map1.get(0).equals(map2.get(0))) {
					Iterator iter1 = mMap1.entrySet().iterator();
					while (iter1.hasNext()) {
						Map.Entry entry1 = (Map.Entry) iter1.next();
						Object key1 = entry1.getKey();
						Object val1 = entry1.getValue();
						Iterator iter2 = mMap2.entrySet().iterator();
						while (iter2.hasNext()) {
							Map.Entry entry2 = (Map.Entry) iter2.next();
							Object key2 = entry2.getKey();
							Object val2 = entry2.getValue();
							if (val2.toString().equals(val1.toString())) {
								map1.put((int) key1, map2.get((int) key2));
							}
						}
					}
				}
			}
		}
		// mLis1.addAll(mLis3);
		new WriteXlsxPoi(mLis1, "两个Xls合并");
		// List<LinkedHashMap<Integer, String>> mList = mLis1;
		// List<LinkedHashMap<String, String>> dataList = new ArrayList<>();
		// for (int i = 0; i < mList.size(); i++) {
		// LinkedHashMap<Integer, String> mMap = mList.get(i);
		// if (mMap.size() > 1) {
		// for (int j = 1; j < mMap.size(); j++) {
		// String str = mMap.get(j);
		// if (str != null && !str.equals("")) {
		// if (dataList.size() < j) {
		// LinkedHashMap<String, String> map = new LinkedHashMap<>();
		// map.put(mMap.get(0), mMap.get(j));
		// dataList.add(map);
		// }
		// else {
		// LinkedHashMap<String, String> map = dataList.get(j - 1);
		// map.put(mMap.get(0), mMap.get(j));
		// }
		// }
		// }
		// }
		// }
	}

	private List<LinkedHashMap<String, String>> getXlsData(String path) {
		if (!path.endsWith("xls") && !path.endsWith("xlsx")) {
			JOptionPane.showMessageDialog(null, "资源文件请选择xls或者xlsx文件", "标题",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return XlsDataPoi.getData(new File(path));
	}

	private List<LinkedHashMap<Integer, String>> getInXlsData(String path) {
		if (!path.endsWith("xls") && !path.endsWith("xlsx")) {
			JOptionPane.showMessageDialog(null, "资源文件请选择xls或者xlsx文件", "标题",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return XlsDataPoi.getXlsData(new File(path));
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
