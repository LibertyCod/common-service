package com.liberty.share.exception.errorcode;

import java.util.Properties;

/**
 * ErrorCode 版本类
 * </p>
 * 方便从错误代码获取错误提示
 * </p>
 * @author leejianhao
 *
 */
public final class ErrorCodeHelper {

	private static final Properties props = new Properties();
	
	private ErrorCodeHelper() {
		throw new UnsupportedOperationException();
	}
	
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}

	public static String get(int key) {
		return props.getProperty(key+"");
	}
}
