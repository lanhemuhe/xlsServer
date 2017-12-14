package cn.hj.xlsServer.bean;

public class ReturnValue {
	private int result;
	private boolean success;
	private String msg;
	public ReturnValue(){
		setResult(0);
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.success=result>=0?true:false;
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.result=success?0:-1;
		this.success = success;
	}
	
}
