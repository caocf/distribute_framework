package com.appleframework.data.hbase.literal.interpreter;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;
import com.appleframework.data.hbase.util.Util;

/**
 * CharInterperter.
 * 
 * @author xinzhi.zhang
 * */
public class CharInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return Character.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        Util.checkLength(literalValue, 1);
        return literalValue.charAt(0);
    }
}