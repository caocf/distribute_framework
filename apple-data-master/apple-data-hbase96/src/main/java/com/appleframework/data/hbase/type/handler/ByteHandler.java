package com.appleframework.data.hbase.type.handler;

import com.appleframework.data.hbase.type.AbstractTypeHandler;
import com.appleframework.data.hbase.util.Util;
/**
 * @author xinzhi
 * */
public class ByteHandler extends AbstractTypeHandler {

    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == byte.class || type == Byte.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return new byte[] { ((Byte) value).byteValue() };

    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        Util.checkLength(bytes, 1);
        return bytes[0];
    }
}
