package cn.hj.xlsServer.xls.reader;

import java.io.File;
import java.io.InputStream;

import cn.hj.xlsServer.xls.XlsException;
import cn.hj.xlsServer.xls.config.XlsReadConfig;
import cn.hj.xlsServer.xls.constant.FileType;

public class ReaderFactory {
	
	public static ExcelReader getReader(FileType type,String sheetName, File file,File templatefile) throws Exception{
		XlsReadConfig config = null;
		ExcelReader reader = null;
		if(type.equals(FileType.EXCEL2003)){
			reader = new Excel2003Reader(sheetName,file, templatefile);
		}else if(type.equals(FileType.EXCEL2007)){
			reader = new Excel2007Reader(sheetName,file, templatefile);
		}else{
			throw new XlsException("不支持的文件按类型");
		}
		return reader;
	}
}

