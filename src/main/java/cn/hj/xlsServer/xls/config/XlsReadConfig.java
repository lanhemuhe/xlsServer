package cn.hj.xlsServer.xls.config;

public class XlsReadConfig {
	private long id;
	private String templateName;
	private String sheetName;
	private int beginRow;
	private String endRowFlag;
	private int beginColum;
	private int endColum;
	private String summary;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getsheetName() {
		return sheetName;
	}
	public void setsheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public int getBeginRow() {
		return beginRow;
	}
	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}
	public String getEndRowFlag() {
		return endRowFlag;
	}
	public void setEndRowFlag(String endRowFlag) {
		this.endRowFlag = endRowFlag;
	}
	public int getBeginColum() {
		return beginColum;
	}
	public void setBeginColum(int beginColum) {
		this.beginColum = beginColum;
	}
	public int getEndColum() {
		return endColum;
	}
	public void setEndColum(int endColum) {
		this.endColum = endColum;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
