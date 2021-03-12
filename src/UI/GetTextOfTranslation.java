package UI;

import utils.Singleton;
import utils.WriteXlsxPoi;
import utils.XlsDataPoi;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.*;

public class GetTextOfTranslation extends JFrame implements ActionListener {
    JLabel lable1, lable2, lable3;
    TextField text1, text2, text3;

    GetTextOfTranslation() {
//        super();
        initView();
    }

    private void initView() {
        setTitle("获取翻译的文案");
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

        JButton button1 = new JButton("获取翻译的文案");

        lable1.setBounds(10, 20, 70, 30);
        lable2.setBounds(10, 70, 70, 30);
        lable3.setBounds(10, 120, 70, 30);
        text1.setBounds(90, 20, 150, 30);
        text2.setBounds(90, 70, 150, 30);
        text3.setBounds(90, 120, 150, 30);
        selectButton1.setBounds(250, 20, 30, 30);
        selectButton2.setBounds(250, 70, 30, 30);
        selectButton3.setBounds(250, 120, 30, 30);
        button1.setBounds(50, 170, 200, 30);

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
        setBounds((width - 300) / 2, (height - 300) / 2, 400, 300);
        setVisible(true);

        selectButton1.addActionListener(this);
        selectButton2.addActionListener(this);
        selectButton3.addActionListener(this);
        button1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
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
            case "获取翻译的文案":
                Singleton.getInstance().setInPutPath(text1.getText());
                Singleton.getInstance().setInPutPath2(text2.getText());
                Singleton.getInstance().setOutPutPath(text3.getText());
                getUnionString();
                break;
            default:
                JOptionPane.showMessageDialog(null, "在对话框内显示的描述性的文字", "标题",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private List<LinkedHashMap<Integer, String>> getXlsData(String path) {
        if (!path.endsWith("xls") && !path.endsWith("xlsx")) {
            JOptionPane.showMessageDialog(null, "资源文件请选择xls或者xlsx文件", "标题",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return XlsDataPoi.getXlsData(new File(path));
    }

    private void getUnionString() {
        List<LinkedHashMap<Integer, String>> mLis1 = getXlsData(Singleton
                .getInstance().getInPutPath());
        List<LinkedHashMap<Integer, String>> mLis2 = getXlsData(Singleton
                .getInstance().getInPutPath2());
        List<LinkedHashMap<Integer, String>> mLis3 = new ArrayList<LinkedHashMap<Integer, String>>();
        LinkedHashMap<Integer, String> map1 = mLis1.get(0); //表一支持的国家列表
        LinkedHashMap<Integer, String> map2 = mLis2.get(0);//表二支持的国家列表
        LinkedHashMap<Integer, String> map3 = new LinkedHashMap<>();
        Iterator iter2 = map2.entrySet().iterator();
        mLis3.addAll(mLis1);
        map3.putAll(map1);
        int count = map3.size();
        while (iter2.hasNext()) {
            Map.Entry entry = (Map.Entry) iter2.next();
            boolean isGet = true;
            String value = entry.getValue().toString();
            for (String str : map1.values()) {
                if (str.equalsIgnoreCase(value)) {
                    isGet = false;
                    break;
                }
            }
//            System.out.println("map1  key----" + value);
            if (isGet) {
                map3.put(count, value);
                count++;
            }
        }

        mLis3.set(0, map3);
        for (int i = 1; i < mLis3.size(); i++) {
            LinkedHashMap<Integer, String> tmpMap = mLis3.get(i);
//            if (tmpMap.size() != map3.size()) {
                String id = tmpMap.get(0);
                LinkedHashMap<Integer, String> tmpMap2 = getLinkedHashMapFromID(id, mLis2);
                if(tmpMap2 != null) {
                    Iterator iter3 = map3.entrySet().iterator();
                    while (iter3.hasNext()) {
                        Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iter3.next();
                        int key = entry.getKey();
//                        if (!tmpMap.containsKey(key) || tmpMap.get(key) == null) {
                            iter2 = map2.entrySet().iterator();
                            String value = entry.getValue();
                            while (iter2.hasNext()) {
                                Map.Entry<Integer, String> entry2 = (Map.Entry<Integer, String>) iter2.next();
                                System.out.println("tmpMap----" + value    + "   " +  entry2.getValue());
                                if (value.equalsIgnoreCase(entry2.getValue())) {
                                    int key2 = entry2.getKey();
                                    if (tmpMap2.containsKey(key2)) {
                                        tmpMap.put(key, tmpMap2.get(key2));
                                    }
                                }
                            }
//                        }
//                    }
                }
            }
        }
        new WriteXlsxPoi(mLis3,"获取翻译后的文案");
    }

    /**
     * 从List<LinkedHashMap<Integer, String>>获取ID = id的一行
     *
     * @param id
     * @param list
     * @return
     */
    private LinkedHashMap<Integer, String> getLinkedHashMapFromID(String id, List<LinkedHashMap<Integer, String>> list) {
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> map = list.get(i);
            if (id.equalsIgnoreCase(map.get(0))) {
                return map;
            }
        }

        return null;
    }


    //求两个字符串数组的并集，利用set的元素唯一性
    public static String[] union(String[] arr1, String[] arr2) {
        Set<String> set = new HashSet<String>();
        for (String str : arr1) {
            set.add(str);
        }
        for (String str : arr2) {
            set.add(str);
        }
        String[] result = {};
        return set.toArray(result);
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
