package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

/**
 * ShortInterpreter.
 * 
 * @author xinzhi.zhang
 * */
public class ShortInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Short.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return Short.parseShort(literalValue);
    }
}