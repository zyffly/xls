package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsDataPoi {
	public static List<LinkedHashMap<String, String>> getData(File file) {
		if (!FileUtils.isExistsDirectory(file)) {
			return null;
		}
		List<LinkedHashMap<Integer, String>> mList = getXlsData(file);
		List<LinkedHashMap<String, String>> dataList = new ArrayList<>();
		for (int i = 0; i < mList.size(); i++) {
			LinkedHashMap<Integer, String> mMap = mList.get(i);
			if (mMap.size() > 1) {
				Iterator iter = mMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					int key = (int) entry.getKey();
					String str = mMap.get(key);
					if (key > 0 && str != null && !str.equals("")) {
						if (dataList.size() < key) {
							LinkedHashMap<String, String> map = new LinkedHashMap<>();
							map.put(mMap.get(0), str);
							dataList.add(map);
						} else {
							LinkedHashMap<String, String> map = dataList.get(key - 1);
							map.put(mMap.get(0), str);
						}
					}
				}
			}
		}

		return dataList;
	}

	public static List<LinkedHashMap<Integer, String>> getXlsData(File file) {
		List<LinkedHashMap<Integer, String>> mList = new ArrayList<>();
		try {
			// read(file);
			FileInputStream stream = new FileInputStream(file);
			String path = file.getPath();
			String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
			Workbook wb = null;
			System.out.println("path:   " + path);
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(stream);
			} else {
				System.out.println("您输入的excel格式不正确");
			}

			Iterator iteratorSheet = wb.sheetIterator();
			while (iteratorSheet.hasNext()) {
				Sheet rs = (Sheet) iteratorSheet.next();
				Iterator iteratorRow = rs.rowIterator();
				while (iteratorRow.hasNext()) {
					Row row = (Row) iteratorRow.next();
					LinkedHashMap<Integer, String> mMap = new LinkedHashMap<>();
					Iterator iteratorCell = row.cellIterator();
					while (iteratorCell.hasNext()) {
						Cell cell = (Cell) iteratorCell.next();
						String value = cell.getStringCellValue();
						mMap.put(cell.getColumnIndex(), value);
					}
					mList.add(mMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mList;
	}

	public static void read(File file) {
		try {
			FileInputStream stream = new FileInputStream(file);
			String path = file.getPath();
			String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
			Workbook wb = null;
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(stream);
			} else {
				System.out.println("您输入的excel格式不正确");
			}
			Sheet sheet1 = wb.getSheetAt(0);
			Row row = sheet1.getRow(0);
			Cell cell = row.getCell(0);
			System.out.print(cell.getStringCellValue() + "  ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
