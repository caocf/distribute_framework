package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

/**
 * BooleanInterpreter.
 * 
 * @author xinzhi.zhang
 * */
public class BooleanInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Boolean.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return Boolean.parseBoolean(literalValue);
    }
}