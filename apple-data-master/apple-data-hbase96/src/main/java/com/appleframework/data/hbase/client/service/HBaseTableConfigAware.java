package com.appleframework.data.hbase.client.service;

import com.appleframework.data.hbase.config.HBaseTableConfig;

/**
 * HBaseTableConfigAware
 * 
 * @author xinzhi.zhang
 * */
public interface HBaseTableConfigAware {

    public HBaseTableConfig getHbaseTableConfig();

    public void setHbaseTableConfig(HBaseTableConfig hbaseTableConfig);
}
