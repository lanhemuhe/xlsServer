package cn.hj.xlsServer.xls.writer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import cn.hj.xlsServer.xls.config.XlsReadConfig;

public class Excel2003Writer extends ExcelWriter {

	/**
	 * @param data
	 * @param writeName
	 * @param model
	 */
	public Excel2003Writer(List<Map<String,String>> data, File file,XlsReadConfig config) {
		super(data, file, config);
	}

	@Override
	protected Workbook getWorkbook(InputStream in) {
		Workbook book = null;
		try {
			book= new HSSFWorkbook(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book;
	}

}

