package com.liberty.share.configurer;

import com.liberty.share.exception.errorcode.ErrorCodeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author nickChen
 * @since 2018-01-17
 */
@Configuration
public class RootConfigurer {
    @Bean
    public ErrorCodeConfigurer errorCodeConfigurer() {
        return new ErrorCodeConfigurer(new Resource[] { new ClassPathResource("errorcode.properties")});
    }
}
