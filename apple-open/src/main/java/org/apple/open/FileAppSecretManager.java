package org.durcframework.open;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class FileAppSecretManager implements AppSecretManager {

    private String appSecretFile = "appSecret.properties";

    private Properties properties;

    public String getSecret(String appId) {
        if (properties == null) {
            try {
                DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                Resource resource = resourceLoader.getResource(appSecretFile);
                properties =   PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) {
                throw new OpenException("在类路径下找不到appSecret.properties的应用密钥的属性文件");
            }
        }

        return properties.getProperty(appId);
    }

    public void setAppSecretFile(String appSecretFile) {
        this.appSecretFile = appSecretFile;
    }

    @Override
    public boolean isValidAppId(String appId) {
    	if(appId == null){
    		return false;
    	}
        return getSecret(appId) != null;
    }
}

