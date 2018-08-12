package com.rsp.rsp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置文件
 * @author sjb
 */
@Component
@PropertySource("classpath:config.properties")
public class PropertiesConif {

    @Value("${test.hello}")
    private String pro;

    @Value("${upload.picturePath}")
    private String localPath;

    public String getPro() {
        return pro;
    }

    public String getLocalPath() {
        return localPath;
    }
}
