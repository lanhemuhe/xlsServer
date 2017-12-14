package cn.hj.xlsServer.xls.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.hj.xlsServer.xls.config.XlsReadConfig;

public abstract class ExcelWriter {
	protected File file;
	protected XlsReadConfig config;
	protected List<Map<String,String>> data;
	/**
	 * KEY ：title
	 * VALUE : property
	 */
	protected Map<String, String> propMap = null;
	public ExcelWriter(List<Map<String,String>> data, File file, XlsReadConfig config){
		this.file = file;
		this.data = data;
		this.config = config;
	}
	
	protected abstract Workbook getWorkbook(InputStream in);

	public File write(){
		Workbook book;
		FileInputStream in = null;
		FileOutputStream outFile = null;
		try {
			in = new FileInputStream(this.file);
			book = this.getWorkbook(in);
			Sheet sheet = book.getSheet(this.config.getsheetName());
			// 获取标题行
			Row titlerow = sheet.getRow(this.config.getBeginRow());
			int rowNum = this.config.getBeginRow() +1;
			// 获取写入标记行，作为模板
			Row writeTemplate = sheet.getRow(rowNum);
			for(int i = 0, len = data.size(); i < len; i++){
				// 获取写入行
				Row writeRow = sheet.getRow(rowNum++);
				// 按模板复制一行
				if(writeRow==null){
					writeRow = sheet.createRow(rowNum-1);
					for (int j = writeTemplate.getFirstCellNum(); j < writeTemplate.getLastCellNum(); j++) {
						writeRow.createCell(j).setCellStyle(writeTemplate.getCell(j).getCellStyle());
					}
				}
				this.appendRow(titlerow, data.get(i), writeRow);
				// 插入一个空行
				if(sheet.getRow(rowNum)!=null&&(i+1)<len){
					sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1, true, true);
				}
			}
			outFile = new FileOutputStream(this.file);
			book.write(outFile);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {in.close();} catch (IOException e) {e.printStackTrace();}
			}
			if(outFile != null){
				try {outFile.close();} catch (IOException e) {e.printStackTrace();}
			}
		} 
		return file;
	}
	
	private void appendRow(Row titlerow,  Map<String,String> data, Row writeRow ){
		// 根据标题填入行数据
		for (int i = this.config.getBeginColum(); i <= this.config.getEndColum(); i++) {
			Cell titlecell = titlerow.getCell(i);
			String value = data.get(titlecell.getStringCellValue().trim());
			Cell cell = writeRow.getCell(i);
			if(cell==null){
				cell = writeRow.createCell(i);
			}
			cell.setCellValue(value);
		}
	}
}

