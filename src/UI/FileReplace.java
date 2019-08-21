package UI;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class FileReplace extends JFrame implements ActionListener {
	JLabel lable1, lable2;
	TextField text1, text2;

	FileReplace() {
		setTitle("stings文件替换");
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();

		JPanel p = new JPanel();
		p.setLayout(null);
		add(p);
		lable1 = new JLabel("被替换的文件：");
		lable2 = new JLabel("替换的文件：");

		text1 = new TextField(10);
		text2 = new TextField(10);

		JButton selectButton1 = new JButton("...");
		selectButton1.setActionCommand("select1");
		JButton selectButton2 = new JButton("...");
		selectButton2.setActionCommand("select2");

		JButton button1 = new JButton("替换");

		lable1.setBounds(10, 50, 100, 30);
		lable2.setBounds(10, 100, 100, 30);
		text1.setBounds(120, 50, 150, 30);
		text2.setBounds(120, 100, 150, 30);
		selectButton1.setBounds(290, 50, 30, 30);
		selectButton2.setBounds(290, 100, 30, 30);
		button1.setBounds(50, 200, 100, 30);

		p.add(lable1);
		p.add(lable2);
		p.add(text1);
		p.add(text2);
		p.add(selectButton1);
		p.add(selectButton2);
		p.add(button1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((width - 300) / 2, (height - 300) / 2, 400, 300);
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
		case "替换":
			// utils.Singleton.getInstance().setInPutPath(text1.getText());
			// utils.Singleton.getInstance().setOutPutPath(text2.getText());
			replace(text1.getText(), text2.getText());
			break;
		default:
			JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	public LinkedHashMap<String, String> getFiles(File fn) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		assert fn.isDirectory();
		File[] files = fn.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String name = f.getName();
				System.out.println("name  " + name);
				if (name.startsWith("values")) {
					map.put(name, f.getPath());
				}
			}
		}
		System.out.println("map  " + map.size());
		return map;
	}

	private void replace(String oldFile, String newFile) {
		LinkedHashMap<String, String> replacedMap = getFiles(new File(oldFile));
		LinkedHashMap<String, String> replaceMap = getFiles(new File(newFile));
		Iterator iterator = replaceMap.entrySet().iterator();
		System.out.println("iterator  " + iterator.hasNext() + "   " + replaceMap.size());
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			System.out.println("0000000  " + isHasStringFile(val) + "    " + replacedMap.containsKey(key));
			if (isHasStringFile(val) && replacedMap.containsKey(key)) {
				String valed = replacedMap.get(key);
				System.out.println("0000001  " + isHasStringFile(valed));
				if (isHasStringFile(valed)) {
					File file = new File(valed + File.separator + "strings.xml");
					System.out.println("0000002   " + file.exists());
					if (file.exists()) {
						System.out.println("0000003");
						file.delete();
					}
					copyFile(val + File.separator + "strings.xml", valed + File.separator + "strings.xml");
				}
			}
		}
	}

	private boolean isHasStringFile(String path) {
		if (path != null && !path.equals("")) {
			File[] files = new File(path).listFiles();
			for (File f : files) {
				if (!f.isDirectory()) {
					String name = f.getName();
					if (name.equals("strings.xml")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				System.out.println("复制单个文件操作完成");
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
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
