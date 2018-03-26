package com.liberty.share.exception;

/**
 * 非受检异常基类
 * 
 * @author leejianhao
 *
 */
public class BaseRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码
	 */
	protected int errorCode;
	
	/**
	 * 错误提示信息
	 */
	protected String errorMsg;
	
	/**
	 * 额外信息
	 */
	protected Object data;
	
	public BaseRuntimeException() {}
	
	public BaseRuntimeException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public BaseRuntimeException(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public BaseRuntimeException(int errorCode, String errorMsg, Object data) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public Object getData() {
		return data;
	}
	
}
