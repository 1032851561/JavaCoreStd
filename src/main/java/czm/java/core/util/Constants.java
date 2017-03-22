package czm.java.core.util;

/**
 * 常量类
 * 
 * @author chenzhiming
 *
 */
public class Constants {

	public static final String INFILE = Constants.getResourcePath() + "InFile.txt";
	public static final String OUTFILE = Constants.getResourcePath() + "OutFile.txt";
	public static final int BUFFER_SIZE_1 = 1;
	public static final int BUFFER_SIZE_100 = 100;
	public static final int BUFFER_SIZE_1024 = 1024;

	/**
	 * 获取资源目录
	 * 
	 * @return
	 */
	static public String getResourcePath() {
		return Constants.class.getResource("/").getPath();
	}
}
