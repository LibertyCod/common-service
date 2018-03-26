package com.liberty.share.exception.errorcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 错误代码配置
 * </p>
 * @author leejianhao
 */
public class ErrorCodeConfigurer implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorCodeConfigurer.class); 
	
	private Resource[] locations;
	
    public ErrorCodeConfigurer() {}
    
    public ErrorCodeConfigurer(Resource[] locations) {
    	this.locations = locations;
    }
    
    public ErrorCodeConfigurer(Resource location) {
    	if (this.locations == null) {
    		this.locations = new Resource[1];
    	}
    	this.locations[0] = location;
    }
    
	public Resource[] getLocations() {
		return locations;
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.buildErrorCode();
	}
	
	private void buildErrorCode() {
		if (locations == null || locations.length == 0) {
			LOG.warn("error code load fail. cause not specify any resource location");
			return ;
		}
		for (Resource location : this.locations) {
			try {
				
				Properties prop = PropertiesLoaderUtils
						.loadProperties(new EncodedResource(location,"UTF-8"));
				
				for (Entry<Object, Object> entry : prop.entrySet()) {
					ErrorCodeHelper.setProperty(entry.getKey().toString(), entry.getValue().toString());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
