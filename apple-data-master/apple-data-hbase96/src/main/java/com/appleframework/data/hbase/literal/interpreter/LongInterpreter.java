package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

/**
 * LongInterperter.
 * 
 * @author xinzhi.zhang
 * */
public class LongInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Long.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return Long.parseLong(literalValue);
    }
}
