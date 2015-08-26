package com.appleframework.data.hbase.literal.interpreter.ext;

import com.appleframework.data.hbase.literal.AbstractLiteralInterpreter;
import com.appleframework.data.hbase.type.ext.HexBytes;

/**
 * HexBytesInterpreter.
 * 
 * @author xinzhi.zhang
 * */
public class HexBytesInterpreter extends AbstractLiteralInterpreter {

    @Override
    public Class<?> getTypeCanInterpret() {
        return HexBytes.class;
    }

    @Override
    protected Object interpret_internal(String literalValue) {
        return new HexBytes(literalValue);
    }
}
