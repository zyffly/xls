package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteXlsxPoi {

	public WriteXlsxPoi(List<LinkedHashMap<Integer, String>> mList) {
		try {
			write(new FileOutputStream(Singleton.getInstance().getOutPutPath()
					+ "/文案.xlsx"), mList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WriteXlsxPoi(List<LinkedHashMap<Integer, String>> mList, String name) {
		try {
			write(new FileOutputStream(Singleton.getInstance().getOutPutPath()
					+ "/" + name + ".xlsx"), mList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void write(OutputStream os,
			List<LinkedHashMap<Integer, String>> mList) throws Exception {
		Workbook wb = null;
		// if ("xls".equals(excelExtName)) {
		// wb = new HSSFWorkbook();
		// } else if ("xlsx".equals(excelExtName)) {
		wb = new XSSFWorkbook();
		// } else {
		// throw new Exception("当前文件不是excel文件");
		// }

		Sheet sheet = wb.createSheet("First Sheet");

		for (int i = 0; i < mList.size(); i++) {
			LinkedHashMap<Integer, String> map = mList.get(i);
			Iterator iter = map.keySet().iterator();
			Row row = sheet.createRow(i);
			while (iter.hasNext()) {
				int key = (int) iter.next();
				String val = map.get(key);
				Cell cell = row.createCell(key);
				cell.setCellValue(val);
			}
		}

		// 把创建的内容写入到输出流中，并关闭输出流
		wb.write(os);
		wb.close();
		os.close();
	}
}
