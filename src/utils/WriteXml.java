package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXml {
	LinkedHashMap<Integer, String> mMap;

	public WriteXml(List<LinkedHashMap<String, String>> mList) {
		FileUtils.makeDirs(Singleton.getInstance().getOutPutPath()+"/res");
		write(mList);
	}

	private void write(List<LinkedHashMap<String, String>> mList) {
		for (int i = 0; i < mList.size(); i++) {
			LinkedHashMap<String, String> mMap = mList.get(i);
			createXml(mMap);
		}
	}

	public void createXml(LinkedHashMap<String, String> mMap) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			document.setXmlStandalone(true);

			Element root = document.createElement("resources"); // 创建根节点
			document.appendChild(root);
			String path = null;
			Iterator iter = mMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();
				String val = entry.getValue().toString();
				if (key.equals("ID")) {
					path = createFile(val);
					if (path == null || path.equals("")
							|| !FileUtils.isExistsDirectory(path)) {
						return;
					}
				} else if (key.matches("\\w+_\\w+") && key
						.substring(key.lastIndexOf("_") , key.length())
						.equals("_0")) {
					
					String id = key.substring(0, key.lastIndexOf("_"));
					Element array = document.createElement("string-array");
					Attr name = document.createAttribute("name");
					name.setValue(id);
					array.setAttributeNode(name);
					
					Element item = document.createElement("item");
					item.appendChild(document.createTextNode(val));
					array.appendChild(item);
					
					while (true) {
						entry = (Map.Entry) iter.next();
						key = entry.getKey().toString();
						val = entry.getValue().toString();
						if (key.matches("\\w+_\\w+")  && key.substring(key.lastIndexOf("_")+1 ,
								key.length()).matches("^[0-9]*[1-9][0-9]*$")) {
							item = document.createElement("item");
							item.appendChild(document.createTextNode(val));
							array.appendChild(item);
						}else if (key.matches("\\w+_\\w+") && key
								.substring(key.lastIndexOf("_") , key.length())
								.equals("_0")){
							root.appendChild(array);
							
							id = key.substring(0, key.lastIndexOf("_"));
							array = document.createElement("string-array");
							name = document.createAttribute("name");
							name.setValue(id);
							array.setAttributeNode(name);
							
							item = document.createElement("item");
							item.appendChild(document.createTextNode(val));
							array.appendChild(item);
							
						}else {
							System.out.println("------" + key);
							root.appendChild(array);
							
							item = document.createElement("string");
							name = document.createAttribute("name");
							name.setValue(key);
							item.setAttributeNode(name);
							item.appendChild(document.createTextNode(val));
							root.appendChild(item);
							break;
						}
					}
				} else {
					Element item = document.createElement("string");
					Attr name = document.createAttribute("name");
					name.setValue(key);
					item.setAttributeNode(name);
					item.appendChild(document.createTextNode(val));
					root.appendChild(item);
					if(key.equals("application_name")){
						System.out.println("name:  " +name);
					}
				}
			}
			// 将DOM对象document写入到xml文件中
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
			PrintWriter pw = new PrintWriter(new FileOutputStream(path
					+ "/strings.xml"));
		
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result); // 关键转换
			System.out.println("生成XML文件成功!");
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
	}

	public String createFile(String val) {
		String path;
		if (val.equals("en")) {
			path = Singleton.getInstance().getOutPutPath()+"/res/" + "values";
			FileUtils.makeDirs(path);
		} else {
			path = Singleton.getInstance().getOutPutPath()+"/res/" + "values-" + val;
		}
		FileUtils.makeDirs(path);
		return path;
	}

}
