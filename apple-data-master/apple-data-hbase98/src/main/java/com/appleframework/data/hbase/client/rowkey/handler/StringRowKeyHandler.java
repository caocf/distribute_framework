package com.appleframework.data.hbase.client.rowkey.handler;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.data.hbase.client.rowkey.StringRowKey;

/**
 * @author xinzhi
 */
public class StringRowKeyHandler implements RowKeyHandler {

    @Override
    public StringRowKey convert(byte[] row) {
        return new StringRowKey(Bytes.toString(row));
    }
}
