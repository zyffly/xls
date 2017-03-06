import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlData {
	public XmlData() {
	}

	public List<LinkedHashMap<String, String>> parseFromFolder(File fn)
			throws IOException, ParserConfigurationException, SAXException {
		List<LinkedHashMap<String, String>> ret = getAllData(fn, null);
		return ret;
	}

	public List<LinkedHashMap<String, String>> getAllData(File fn, String value)
			throws IOException, ParserConfigurationException, SAXException {

		List<LinkedHashMap<String, String>> ret = new ArrayList<>();
		assert fn.isDirectory();

		File keyFile = new File(fn.getPath() + File.separator + "values");
		if (keyFile.isDirectory()) {
			ret.addAll(getData(keyFile, "en"));
		}

		ret.addAll(getData(fn, null));

		return ret;
	}

	public List<LinkedHashMap<String, String>> getData(File fn, String value)
			throws IOException, ParserConfigurationException, SAXException {
		List<LinkedHashMap<String, String>> ret = new ArrayList<>();
		assert fn.isDirectory();

		File[] files = fn.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String name = f.getName();
				if (!name.equals("values")) {
					ret.addAll(getData(f, name.replace("values-", "")));
				}
			} else if (f.getName().equals("strings.xml")) {
				LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
				mMap.put("ID", value);
				LinkedHashMap<String, String> map = parseFromFile(f);
				if (map != null) {
					mMap.putAll(parseFromFile(f));
				}
				ret.add(mMap);
			}
		}
		return ret;
	}

	public static LinkedHashMap<String, String> parseFromFile(File fn)
			throws IOException, ParserConfigurationException, SAXException {
		String currentPath = System.getProperty("user.dir");
		String fp = fn.getAbsolutePath();
		// AndroidStringFile2 ret = new
		// AndroidStringFile2(fp.substring(currentPath.length()));
		assert fn.isFile();

		Document document = null;
		// DOM parser instance
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		// parse an XML file into a DOM tree
		document = builder.parse(fn);
		Element root = document.getDocumentElement();
		LinkedHashMap<String, String> mMap = new LinkedHashMap();
		NodeList nodes = root.getChildNodes();
		if (nodes.getLength() > 0) {
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeName().equals("string")) {
					String id = node.getAttributes().getNamedItem("name").getTextContent();
					String str = node.getTextContent();
					mMap.put(id, str);
				} else if (node.getNodeName().equals("string-array")) {
					String id = node.getAttributes().getNamedItem("name").getTextContent();
					NodeList arrayList = node.getChildNodes();
					int k = 0;
					for (int j = 0; j < arrayList.getLength(); ++j) {
						Node n = arrayList.item(j);
						if (n.getNodeName().equals("item")) {
							String text = n.getTextContent();
							if (text.trim().length() == 0)
								continue;
							mMap.put(id + "_" + k, n.getTextContent());
							k++;
						}
					}
				}
			}
		}

		if (mMap.size() > 0)
			return mMap;
		else
			return null;
	}

}
