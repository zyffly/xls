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

public class XlsNoTranslation extends JFrame implements ActionListener {
	JLabel lable1, lable2;
	TextField text1, text2;

	XlsNoTranslation() {
		super();
		setTitle("获取没有全部翻译的ID");

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();

		JPanel p = new JPanel();
		p.setLayout(null);
		add(p);
		lable1 = new JLabel("资源路径1：");
		lable2 = new JLabel("生成路径：");

		text1 = new TextField(10);
		text2 = new TextField(10);

		JButton selectButton1 = new JButton("...");
		selectButton1.setActionCommand("select1");
		JButton selectButton2 = new JButton("...");
		selectButton2.setActionCommand("select2");

		JButton button1 = new JButton("xls");

		lable1.setBounds(10, 50, 70, 30);
		lable2.setBounds(10, 100, 70, 30);
		text1.setBounds(90, 50, 150, 30);
		text2.setBounds(90, 100, 150, 30);
		selectButton1.setBounds(250, 50, 30, 30);
		selectButton2.setBounds(250, 100, 30, 30);
		button1.setBounds(50, 200, 100, 30);

		p.add(lable1);
		p.add(lable2);
		p.add(text1);
		p.add(text2);
		p.add(selectButton1);
		p.add(selectButton2);
		p.add(button1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((width - 300) / 2, (height - 300) / 2, 300, 300);
		setVisible(true);

		selectButton1.addActionListener(this);
		selectButton2.addActionListener(this);
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
		case "xls":
			Singleton.getInstance().setInPutPath(text1.getText());
			Singleton.getInstance().setOutPutPath(text2.getText());
			getNoTranslationString();
			break;
		default:
			JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题",
					JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	private void getNoTranslationString() {
		List<LinkedHashMap<Integer, String>> dataList = getXlsData(Singleton
				.getInstance().getInPutPath());
		List<LinkedHashMap<Integer, String>> mList = new ArrayList<LinkedHashMap<Integer, String>>();
		LinkedHashMap<Integer, String> titleMap1 = dataList.get(0);
		mList.add(titleMap1);
		for (int i = 0; i < dataList.size(); i++) {
			LinkedHashMap<Integer, String> map = dataList.get(i);
			if (map.size() < titleMap1.size()) {
				Iterator titleIter = titleMap1.entrySet().iterator();
				String mapVal_en = null;
				String mapVal_zh_rCN = null;
				boolean flog = false;
				while (titleIter.hasNext()) {
					Map.Entry titleEntry = (Map.Entry) titleIter.next();
					Object titleKey = titleEntry.getKey();
					Object titleVal = titleEntry.getValue();
					String titleValue = titleVal.toString();
					if (titleValue != null && titleValue.equals("en")) {
						mapVal_en = map.get((int) titleKey);
					} else if (titleValue != null
							&& titleValue.equals("zh-rCN")) {
						mapVal_zh_rCN = map.get((int) titleKey);
					}
				}
				// if (mapVal_en != null && !mapVal_en.equals("")
				// && mapVal_zh_rCN != null && !mapVal_zh_rCN.equals("")) {
					if (mapVal_en != null && !mapVal_en.equals("")) {
					mList.add(map);
				}
			} else {
				Iterator iter = map.entrySet().iterator();
				String xxValue = null;
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					xxValue = val.toString();
					
					if (map.get(0).equals("http")) {
						System.out.println("大小-----"  +  map.size()  + "      " + xxValue  + "    " + (xxValue == null || xxValue.equals("")));
					}
					if (xxValue == null || xxValue.equals("")) {
						mList.add(map);
						break;
					}
				}
			}
		}
		new WriteXlsxPoi(mList, "没有翻译的文");
	}

	private List<LinkedHashMap<Integer, String>> getXlsData(String path) {
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
