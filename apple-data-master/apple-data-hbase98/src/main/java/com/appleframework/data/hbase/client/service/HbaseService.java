package com.appleframework.data.hbase.client.service;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;

/**
 * HbaseService
 * 
 * @author xinzhi.zhang
 * */
public interface HbaseService {

    /**
     * Get a reference to the specified table.
     * 
     * @param tableName table name
     * @return a reference to the specified table
     */
    public HTableInterface getTable(String tableName);

    /**
     * Get HBaseAdmin.
     * 
     * @return HBaseAdmin
     * */
    public HBaseAdmin getHBaseAdmin();
}
