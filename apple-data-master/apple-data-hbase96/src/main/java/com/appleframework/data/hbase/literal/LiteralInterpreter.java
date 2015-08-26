package com.appleframework.data.hbase.literal;

import com.appleframework.data.hbase.core.NotNullable;

/**
 * Convert literal value to object of specified type.
 * 
 * <pre>
 * In general, one literal interpreter interpret one specified java type.
 * A java type and its boxer type would be interpreted in one interpreter.
 * </pre>
 * 
 * @author xinzhi
 * */
public interface LiteralInterpreter {

    /**
     * Interpret literalValue to java object.
     * 
     * @param literalValue literalValue.
     * 
     * @return java object.
     * */
    @NotNullable
    public Object interpret(@NotNullable String literalValue);

    /**
     * Type to interpret.
     * 
     * @return Type to interpret.
     * */
	@NotNullable
    public Class<?> getTypeCanInterpret();
}
