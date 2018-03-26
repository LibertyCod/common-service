package com.liberty.share.core;


import com.liberty.share.util.PropertiesUtil;

import java.util.Properties;

/**
 * APPLICATION SERVICE VERSION
 * @author leejianhao
 */
public final class Version {

	private final static Properties VERSION = PropertiesUtil.getProperties("version.properties");
	public static final String SERVICE_NAME = VERSION.getProperty("name");
	public static final String SERVICE_DESCRIPTION = VERSION.getProperty("description");
	// 当前服务版本
	public static final String SERVICE_VERSION = VERSION.getProperty("version");
	public static final String SERVICE_DEPLOY_TIMESTAMP = VERSION.getProperty("time");
}