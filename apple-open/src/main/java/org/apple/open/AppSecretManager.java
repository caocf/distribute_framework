package org.durcframework.open;


public interface AppSecretManager {

    /**
     * 获取应用程序的密钥
     *
     * @param appId
     * @return
     */
    String getSecret(String appId);

    /**
     * 是否是合法的appId
     *
     * @param appId
     * @return
     */
    boolean isValidAppId(String appId);
}