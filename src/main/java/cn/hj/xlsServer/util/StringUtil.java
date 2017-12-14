package cn.hj.xlsServer.util;

public class StringUtil {
	private static final String EMPTY = "";
	
	public static boolean isEmpty(String str){
		return str == null ? true : str.trim().equals(EMPTY);
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
