package com.appleframework.data.hbase.client.rowkey.handler;

import com.appleframework.data.hbase.client.RowKey;

/**
 * Convert hbase's rowkey to rowkey object.
 * 
 * @author xinzhi
 * */
public interface RowKeyHandler {

    /**
     * Convert hbase row key bytes to rowkey Object.
     * 
     * @param row hbase row key.
     * @return rowkey Object.
     * */
    public RowKey convert(byte[] row);
}
