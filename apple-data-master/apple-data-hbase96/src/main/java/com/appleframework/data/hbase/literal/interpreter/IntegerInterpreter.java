package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

/**
 * IntegerInterpreter.
 * 
 * @author xinzhi.zhang
 * */
public class IntegerInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Integer.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return Integer.parseInt(literalValue);
    }

}
