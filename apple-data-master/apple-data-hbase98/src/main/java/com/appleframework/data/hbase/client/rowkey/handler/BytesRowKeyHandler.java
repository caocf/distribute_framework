package com.appleframework.data.hbase.client.rowkey.handler;

import com.appleframework.data.hbase.client.rowkey.BytesRowKey;

/**
 * @author xinzhi
 */
public class BytesRowKeyHandler implements RowKeyHandler {

    @Override
    public BytesRowKey convert(byte[] row) {
        return new BytesRowKey(row);
    }
}
