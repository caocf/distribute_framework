package com.appleframework.data.hbase.client.rowkey.handler;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.data.hbase.client.rowkey.IntRowKey;

/**
 * @author xinzhi
 */
public class IntRowKeyHandler implements RowKeyHandler {

    @Override
    public IntRowKey convert(byte[] row) {
        return new IntRowKey(Bytes.toInt(row));
    }
}
