package cn.hj.xlsServer.xls.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.hj.xlsServer.util.StringUtil;
import cn.hj.xlsServer.xls.XlsException;
import cn.hj.xlsServer.xls.config.XlsReadConfig;
import cn.hj.xlsServer.xls.constant.ConfigConstants;


public abstract class ExcelReader {
	/**
	 * Excel文件
	 */
	protected File file;
	/**
	 * Excel文件模板
	 */
	protected File templateFile;
	/**
	 * 文件读取配置
	 */
	protected XlsReadConfig config = new XlsReadConfig();
	/**
	 * 返回带列标题的指定行列的表数据
	 *    <单行数据<列标题，数据>>
	 */
	private List<Map<String, String>> datas;
	
	protected InputStream in;
	
	/**
	 * @param file
	 * @param model
	 * @throws Exception 
	 */
	public ExcelReader(String sheetName,File file, File templateFile) throws Exception {
		this.file = file;
		this.templateFile = templateFile;
		this.config.setsheetName(sheetName);;
		this.datas = new ArrayList<Map<String, String>>();
		this.config = loadConfigFromTemplate(templateFile,sheetName);
	}
	
	public XlsReadConfig loadConfigFromTemplate(File templateFile,String sheetName) throws Exception{
		XlsReadConfig xlsConfig = new XlsReadConfig();
		InputStream xlsin = null;
		try {
			xlsin = new FileInputStream(templateFile);
			xlsConfig.setsheetName(sheetName);;
			Workbook book = this.getWorkbook(xlsin);
			Sheet sheet = book.getSheet(xlsConfig.getsheetName());
			xlsConfig.setBeginRow(0);
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if(row==null){continue;}
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if(cell==null){continue;}
					// 找到指定数据标记的单元格
					if(ConfigConstants.TEMPLATEDATAFLAG.equals(cell.getStringCellValue())){
						// 第一个标记单元格设置前一行为标题行
						if(xlsConfig.getBeginRow()==0){  
							if(i>1){
								xlsConfig.setBeginRow(i-1);
							}else{
								throw new XlsException("模板文件无标题行！"+templateFile);
							}
							//第一个标记单元格设置起始列
							xlsConfig.setBeginColum(j);
							//第一个单元格下一行的单元格设置为结束行标记
							Row endFlagrow = sheet.getRow(i+1);
							if(endFlagrow!=null&&endFlagrow.getCell(j)!=null){
								xlsConfig.setEndRowFlag(endFlagrow.getCell(j).getStringCellValue());
							}else{
								xlsConfig.setEndRowFlag(ConfigConstants.NULLFLAG);
							}
						}
						// 最后一个标记单元格设置为结束列
						xlsConfig.setEndColum(j);
					}
				}
			}
		}finally{
			if(xlsin != null){
				try {xlsin.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
		return xlsConfig;
	}
	public List<Map<String, String>> read() throws Exception {
		try {
			this.in = new FileInputStream(this.file);
			Workbook book = this.getWorkbook(this.in);
			this.readSheet(book.getSheet(this.config.getsheetName()));
		}finally{
			if(this.in != null){
				try {this.in.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
		return this.datas;
	}
	
	
	/**
	 * 读取一个Sheet的方法
	 * @param sheet
	 * @param propIndexMap
	 * @return
	 */
	private void readSheet(Sheet sheet){
		Map<Integer, String> propIndexMap = this.createTitleIndex(sheet);
		int lastRow = sheet.getLastRowNum();
		// 标题行下一行开始
		for(int i = this.config.getBeginRow()+1; i <= lastRow; i++){
			Row row = sheet.getRow(i);
			if(!this.isEmptyRow(row)){
				Map<String, String> rowData = this.readRow(row, propIndexMap);
				if(rowData!=null){   // 遇到结束标记返回为null
					this.datas.add(rowData);
				}else{
					break;
				}
			}
		}
	}
	/**
	 * 读取 列号-标题 对应关系
	 * @param sheet
	 * @return
	 */
	private Map<Integer, String> createTitleIndex(Sheet sheet){
		Row row = sheet.getRow(this.config.getBeginRow());
		Map<Integer, String> res = new HashMap<Integer, String>();
		
		if(row!=null){
			int endColum = row.getLastCellNum();
			int beginColum = this.config.getBeginColum();
			if(this.config.getEndColum()>0&&this.config.getEndColum()< endColum){
				endColum = this.config.getEndColum();
			}
			if(beginColum>endColum){
				beginColum = endColum;
			}
			for(int i = beginColum; i <= endColum; i++){
				Cell cell = row.getCell(i);
				if(cell == null){
					continue;
				}
				String titleVal = row.getCell(i).getStringCellValue();
				if(StringUtil.isEmpty(titleVal)){
					continue;
				}
				String title = titleVal.trim();
				res.put(i, title);
			}
		}else{
			throw new XlsException("(标题)起始行号错误！无法获取数据！");
		}
		return res;
	}
	/**
	 * 从Row中读取一条记录
	 * @param row
	 * @return
	 */
	private Map<String, String> readRow(Row row, Map<Integer, String> propIndexMap){
		Map<String, String> rowData = new HashMap<String, String>();
		for(Integer index : propIndexMap.keySet()){
			Cell cell = row.getCell(index);
			String titleName = propIndexMap.get(index);
			String cellValue = cell.getStringCellValue();
			// 遇到结束标记退出   返回为null
			if(StringUtil.isNotEmpty(this.config.getEndRowFlag())&&this.config.getEndRowFlag().equals(cellValue)){
				rowData = null;
				break;
			}else{
				rowData.put(titleName, cellValue);
			}
		}
		return rowData;
	}
	
	private boolean isEmptyRow(Row row){
		if(row == null || row.getLastCellNum() == -1){
			return true;
		}
		boolean empty = true;
		
		int last = row.getLastCellNum();
		for(int i = 0; i < last; i++){
			Cell cell = row.getCell(i);
			if(cell == null){
				continue;
			}
			if(StringUtil.isEmpty(cell.getStringCellValue())){
				continue;
			}else{
				empty = false;
				break;
			}
		}
		return empty;
	}
	
	protected abstract Workbook getWorkbook(InputStream in);
}

