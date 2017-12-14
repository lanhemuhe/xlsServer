package cn.hj.xlsServer.xls.writer;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.hj.xlsServer.xls.XlsException;
import cn.hj.xlsServer.xls.config.XlsReadConfig;
import cn.hj.xlsServer.xls.constant.FileType;

public class WriterFactory {

	public static ExcelWriter getInstance(File file, XlsReadConfig config, List<Map<String,String>> data, FileType type){
		ExcelWriter writer = null;
		if(FileType.EXCEL2003.equals(type)){
			writer = new Excel2003Writer(data, file, config);
		}else if(FileType.EXCEL2007.equals(type)){
			writer = new Excel2007Writer(data, file, config);
		}else{
			throw new XlsException("不支持的文件按类型");
		}
		
		return writer;
	}
}

