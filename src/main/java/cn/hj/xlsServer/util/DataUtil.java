package cn.hj.xlsServer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {
	/**取得当前时间*/
	public static java.util.Date getNow(){
		java.util.Date now = new java.util.Date();
		return now;
	}
	/**格式化时间 (yyyyMMddHHmmss)*/
	public static String fmtTime(java.util.Date time){
		String result = "";
		if(time==null){
			time = new java.util.Date();
		}
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
			result = sdf.format(time);
		}catch(Exception e){
		}	
		return result;
	}
	
	/**格式化时间 (HHmmss)*/
	public static String fmtLitleTime(java.util.Date time){
		String result = "";
		if(time==null){
			time = new java.util.Date();
		}
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss"); 
			result = sdf.format(time);
		}catch(Exception e){
		}	
		return result;
	}
	/**格式化日期 (yyyyMMdd)*/
	public static String fmtDate(java.util.Date date){
		String result = "";
		if(date==null){
			date = new java.util.Date();
		}
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			result = sdf.format(date);
		}catch(Exception e){
		}	
		return result;
	}
	/**从字符串yyyyMMddHHmmss转换成yyyy-MM-dd HH:mm:ss*/
	public static String strTime(String time){
		String line = "-";
		String s = ":";
		String strTime = time.substring(0,4)+line+time.substring(4,6)+line+time.substring(6,8)
		+" "+time.substring(8,10)+s+time.substring(10,12)+s+time.substring(12,14);
		return strTime;
	}
	/**获取指定时间*/
	public static java.util.Date getDate(String str){
		java.util.Date result=new java.util.Date();
		java.util.Calendar ca =java.util.Calendar.getInstance();
		try{
			Integer[] nums= getInts(str);
			ca.set(nums[0], nums[1]-1, nums[2], nums[3], nums[4], nums[5]);
			result = ca.getTime();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**获取指定字符串的日期  YYYYMMDD*/
	public static java.util.Date strToDate(String str){
		Date result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
			result = sdf.parse(str);
		}catch(Exception e){
		}	
		return result;
	}
	
	/**将时间格式的字符串以年月日时分秒的格式转化成Integer[]返回*/
	private static Integer[] getInts(String str) throws Exception{
		Integer [] result = {0,0,0,0,0,0,0};
		try{
			str=str.replaceAll("[.]| |\\\\|/|-|:", "");
			int length = str.length();
			if(length>=15){
				int n = length>17 ? 17 :length; 
				result[6]=Integer.parseInt(str.substring(14, n));
			}
			if(length>=14){
				result[5]=Integer.parseInt(str.substring(12, 14));
			}
			if(length>=12){
				result[4]=Integer.parseInt(str.substring(10, 12));
			}
			if(length>=10){
				result[3]=Integer.parseInt(str.substring(8, 10));
			}
			if(length>=8){
				result[2]=Integer.parseInt(str.substring(6, 8));
			}
			if(length>=6){
				result[1]=Integer.parseInt(str.substring(4, 6));
			}
			if(length>=4){
				result[0]=Integer.parseInt(str.substring(0, 4));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	/**
	 * 获取指定日期的前后日期
	 * @param date
	 * @param add 前后天数 负数为前几天 正数为后几天 
	 * @return
	 */
	public static Date getAddDate(Date date,int add){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
        c.add(java.util.Calendar.DAY_OF_MONTH, add);   
        date = c.getTime();
        //进行时间转换
		return date;
	}
}
