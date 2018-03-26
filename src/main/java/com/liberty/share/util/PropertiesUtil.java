package com.liberty.share.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * PropertiesUtil 工具类
 * </p>
 * @author leejianhao
 *
 */
public class PropertiesUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static Properties getProperties(String location) {
		Properties props = null;
		try {
			logger.info("加载资源[" + location + "]");
			props = PropertiesLoaderUtils
					.loadProperties(new EncodedResource(new ClassPathResource(
							location), "UTF-8"));
		} catch (IOException e) {
			logger.error("加载资源[" + location + "]失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return props;
	}
}