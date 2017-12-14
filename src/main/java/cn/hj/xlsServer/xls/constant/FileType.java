package cn.hj.xlsServer.xls.constant;

public enum FileType {

	
	EXCEL2003(1, "EXCEL2003", "xls"),
	EXCEL2007(2, "EXCEL2007", "xlsx");
	
	private int code;
	
	private String name;
	
	private String mark;

	private FileType(int code, String name, String mark) {
		this.code = code;
		this.name = name;
		this.mark = mark;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getMark() {
		return mark;
	}
	
	
	public static FileType get(String mark){
		FileType res = null;
		if("xls".equals(mark)){
			res = EXCEL2003;
		}else if("xlsx".equals(mark)){
			res = EXCEL2007;
		}
		
		return res;
	}
	
}

