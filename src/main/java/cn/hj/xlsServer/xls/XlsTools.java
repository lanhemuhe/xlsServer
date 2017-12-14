package cn.hj.xlsServer.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import cn.hj.xlsServer.util.FileUtil;
import cn.hj.xlsServer.xls.config.XlsReadConfig;
import cn.hj.xlsServer.xls.constant.ConfigConstants;
import cn.hj.xlsServer.xls.constant.FileType;
import cn.hj.xlsServer.xls.reader.Excel2007Reader;
import cn.hj.xlsServer.xls.reader.ExcelReader;
import cn.hj.xlsServer.xls.reader.ReaderFactory;
import cn.hj.xlsServer.xls.writer.ExcelWriter;
import cn.hj.xlsServer.xls.writer.WriterFactory;

public class XlsTools {
	public static void saveFile(FileInputStream fs,String filePath) throws Exception{
		FileOutputStream fos;
		if(fs!=null){
			fos = new FileOutputStream(new File(filePath));
		    byte[] buffer=new byte[1024];
		    int len=0;
		    while((len=fs.read(buffer))!=-1){
		        fos.write(buffer, 0, len);
		    }
		    fos.close();
		    fs.close();
		}
	}
	public static List<Map<String,String>> getXlsData(String filePath,String templatePath,String sheetName) throws Exception{
		String suffix = filePath.substring(filePath.lastIndexOf(".")+1);
		File file = new File(filePath);
		File templatefile = new File(templatePath);
		ExcelReader er = ReaderFactory.getReader(FileType.get(suffix), sheetName, file, templatefile);
		return er.read();
	}
	/**
	 * 根据数据生成Excel文件
	 * @param fileName   不含路径、后缀名（取模板后缀）
	 * @param templatePath
	 * @param sheetName
	 * @param datas
	 * @throws Exception
	 */
	public static File writeXlsData(String fileName,String templatePath,
			String sheetName,List<Map<String,String>> datas) throws Exception{
		// 取文件后缀
		String suffix = templatePath.substring(templatePath.lastIndexOf(".")+1);
		// 文件生成路径（即下载路径）
		String filePath = ConfigConstants.DOWNLOADPATH+fileName+"."+suffix;
		File file = new File(filePath);
		File templatefile = new File(templatePath);
		// 根据模板获取写配置信息
		ExcelReader er = ReaderFactory.getReader(FileType.get(suffix), sheetName, null, templatefile);
		XlsReadConfig config = er.loadConfigFromTemplate(templatefile, sheetName);
		// 复制模板文件作为写入文件
		FileUtil.forChannel(templatefile, file);
		// 将数据写入目标文件
		ExcelWriter ew = WriterFactory.getInstance(file, config, datas, FileType.get(suffix));
		File targetFile = ew.write();
		return targetFile;
	}
//	public static void main(String[] args) {
//		String filePath = "C:\\Users\\lenovo\\Desktop\\季度报表自动化.xlsx";
//		String templatePath = "C:\\Users\\lenovo\\Desktop\\季度报表自动化模板.xlsx";
//		int sheetNo  = 0;
//		String savefile = "C:\\Users\\lenovo\\Desktop\\test.xlsx";
//		JSONArray jArray = new JSONArray();
//		try {
//			List<Map<String, String>>  datas = XlsTools.getXlsData(filePath, templatePath, sheetNo);
//			for (int i = 0; i < datas.size(); i++) {
//				Map<String,String> map = datas.get(i);
//				Set keys= map.keySet();
//				for (Object object : keys) {
//					System.out.println(object.toString()+"---"+map.get(object));
//				}
//				jArray.put(map);
//			}
//			System.out.println(jArray.toString());
//			
//			XlsTools.writeXlsData(savefile, templatePath, sheetNo, datas);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
