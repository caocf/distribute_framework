package com.appleframework.data.hbase.client.service;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;

/**
 * SimpleHbaseRuntimeSettingAware.
 * 
 * @author xinzhi.zhang
 * */
public interface SimpleHbaseRuntimeSettingAware {

    public SimpleHbaseRuntimeSetting getSimpleHbaseRuntimeSetting();

    public void setSimpleHbaseRuntimeSetting(
            SimpleHbaseRuntimeSetting simpleHbaseRuntimeSetting);
}
