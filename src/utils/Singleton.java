package utils;

public class Singleton {
	private String inPutPath;
	private String inPutPath2;
	private String outPutPath;
	private static Singleton single = null;

	// 静态工厂方法
	public static Singleton getInstance() {
		if (single == null) {
			single = new Singleton();
		}
		return single;
	}

	public String getInPutPath() {
		return inPutPath;
	}

	public void setInPutPath(String inPutPath) {
		this.inPutPath = inPutPath;
	}

	public String getOutPutPath() {
		return outPutPath;
	}

	public void setOutPutPath(String outPutPath) {
		this.outPutPath = outPutPath;
	}

	public String getInPutPath2() {
		return inPutPath2;
	}

	public void setInPutPath2(String inPutPath2) {
		this.inPutPath2 = inPutPath2;
	}
	
	
}
