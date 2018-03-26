package com.liberty.share.exception.errorcode;
/**
 * 系统异常代码
 * </p>
 * 错误码命名规则:5【系统编号(0-9)】【模块编号(0-9)】【错误码(0-9)】【错误码(0-9)】
 * </p>
 * @author leejianhao
 *
 */
public class SysErrorCode {
	
	/**
	 * 处理成功
	 */
	public static final int SUCCESS = 0;
	
	/**
	 * 系统繁忙
	 */
	public static final int BUSY = -1;
	
	/**
	 * 操作失败（多为实际数据库影应行数不对）
	 */
	public static final int FAIL = -2;
	
	/**
	 * 参数检查失败
	 */
	public static final int PARAMS_ERROR = -3;

	/**
	 * 授权信息检查失败
	 */
	public static final int UNAUTHORIZED = -4;

	/**
	 * 接口不存在
	 */
	public static final int NOT_FOUND_INTERFACE = -5;
	
}
	
