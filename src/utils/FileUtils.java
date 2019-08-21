package utils;

import java.io.File;


public class FileUtils {

	public static boolean makeDirs(String filePath) {
		File file = new File(filePath);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			return file.mkdir();
		}
		return true;
	}
	
	public static boolean makeDirs(File file) {
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			return file.mkdir();
		}
		return true;
	}
	
	public static boolean isExistsDirectory(String filePath) {
		File file = new File(filePath);
		// 如果文件夹不存在
		if (!file.exists() && !file.isDirectory()) {
			return false;
		}
		return true;
	}

	
	public static boolean isExistsDirectory(File file) {
		// 如果文件夹不存在
		if (!file.exists() && !file.isDirectory()) {
			return false;
		}
		return true;
	}

}
