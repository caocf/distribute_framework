package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

/**
 * ByteInterpreter.
 * 
 * @author xinzhi.zhang
 * */
public class ByteInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Byte.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return Byte.parseByte(literalValue);
    }
}