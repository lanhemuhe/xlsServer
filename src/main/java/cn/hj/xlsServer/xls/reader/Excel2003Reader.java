package cn.hj.xlsServer.xls.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import cn.hj.xlsServer.xls.XlsException;
import cn.hj.xlsServer.xls.config.XlsReadConfig;

public class Excel2003Reader extends ExcelReader {


	/**
	 * @param in
	 * @param model
	 * @throws Exception 
	 */
	public Excel2003Reader(String sheetName,File file, File templateFile) throws Exception {
		super(sheetName,file, templateFile);
	}

	@Override
	protected Workbook getWorkbook(InputStream in) {
		Workbook book = null;
		try {
			book = new HSSFWorkbook(in);
		} catch (Exception e) {
			e.printStackTrace();
			throw new XlsException(e.getMessage());
		}
		return book;
	}


}

