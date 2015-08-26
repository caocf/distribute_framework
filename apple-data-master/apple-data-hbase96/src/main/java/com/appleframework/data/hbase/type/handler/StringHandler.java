package com.appleframework.data.hbase.type.handler;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.data.hbase.type.AbstractTypeHandler;

/**
 * @author xinzhi
 * */
public class StringHandler extends AbstractTypeHandler {

    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == String.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((String) value);
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
