package cn.hj.xlsServer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CommonUtil {
	/**
	 * 将异常信息导入字符串
	 * @param e 异常信息
	 * @return String
	 */
	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
       return "No Exception";
    }
	/**格式化Double(0.00)*/
	public static String fmtDouble(double num){
		String result = "0.00";
        try{
            DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
            nf.applyPattern("0.00");
            result = nf.format(num);
        }catch(Exception exception) { }
        return result;
	}
	/**格式化Double(0.00)*/
	public static String fmtInteger(double num){
		String result = "0";
        try{
            DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
            nf.applyPattern("0");
            result = nf.format(num);
        }catch(Exception exception) { }
        return result;
	}
	
	/**格式化字符串信息*/
	public static String setFill(String str,int length)throws Exception{
		String result = "";
		if(str!=null){
			result=str.trim();
		}
		if(result.getBytes().length>length){
			throw new Exception("输入字符串长度大于给定长度：\n字符串长度："+result.length()+" 给定长度："+length);
		}
		return result;
	}
	/**格式化信息*/
	public static String setFill(double money,int length)throws Exception{
		String str = fmtDouble(money);
		String result = "0.00";
		if(str!=null){
			result=str.trim();
		}
		if(result.getBytes().length>length){
			throw new Exception("输入数字位数大于给定最大位数：\n数字长度："+result.length()+" 给定最大长度长度："+length);
		}
		return result;
	}
	/**格式化信息*/
	public static String setFill(long money,int length)throws Exception{
		String str = money+"";
		String result = "0";
		if(str!=null){
			result=str.trim();
		}
		if(result.getBytes().length>length){
			throw new Exception("输入数字位数大于给定最大位数：\n数字长度："+result.length()+" 给定最大长度长度："+length);
		}
		return result;
	}
	/**格式化信息*/
	public static String setFill(int type,int length)throws Exception{
		String result = "";
		result=String.valueOf(type);
		if(result.getBytes().length>length){
			throw new Exception("输入数字位数大于给定最大位数：\n数字长度："+result.length()+" 给定最大长度长度："+length);
		}
		return result;
	}
	/**获取StringBuffer长度*/
	public static int builderLength(StringBuilder sb )throws Exception{
		int result = 0;
		if(sb != null){
			try{
				result = sb.toString().getBytes().length;
			}catch(Exception e){
				throw e;
			}
		}
		return result;
	}
	
	/**取得MAC信息*/
	public static String getMAC(){
		if(true){
			return "CMacmACmAcmaCmac";
		}
		if( "\\".equals(File.separator)) {//Windows 机器
			return getMACForWindows();
		}else if( "/".equals(File.separator)){//Linux 机器
			return getMACForLinux();
		}
		return "";
	}
	/**获取Linux机器MAC码*/
	private static String getMACForLinux(){
		return "CMacmACmAcmaCmac";
	}
	/**获取windows机器MAC码*/
	private static String getMACForWindows(){
		String result="";
		try {
			Process p1 = Runtime.getRuntime().exec( "ipconfig /all ");
			BufferedReader br=new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String buf=br.readLine();
			while(buf!=null){
				if(buf.indexOf("Physical Address")>=0){
					String strs[] = buf.split(":");
					if(strs!=null && strs.length>=2){
						result = strs[1].trim();
						buf=null;
					}else{
						buf = br.readLine();
					}
				}else{
					buf=br.readLine();
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
