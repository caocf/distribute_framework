package com.appleframework.data.hbase.type.handler.string;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.data.hbase.type.AbstractTypeHandler;

/**
 * @author xinzhi
 * */
public class StringIntegerHandler extends AbstractTypeHandler {

    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {

        String str = String.valueOf(((Integer) value));
        return Bytes.toBytes(str);

    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        String str = Bytes.toString(bytes);
        return Integer.parseInt(str);
    }

}
