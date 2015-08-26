package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;

public class FloatInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Float.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return Float.parseFloat(literalValue);
    }
}