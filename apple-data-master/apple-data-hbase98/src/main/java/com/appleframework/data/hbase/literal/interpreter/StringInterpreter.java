package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

/**
 * StringInterpreter.
 * 
 * @author xinzhi.zhang
 * */
public class StringInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return String.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return literalValue;
    }
}
