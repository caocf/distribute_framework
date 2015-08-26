package com.appleframework.data.hbase.client.rowkeytextfun;

import com.appleframework.data.hbase.client.RowKey;

/**
 * RowKeyTextFunc.
 * 
 * @author xinzhi.zhang
 * */
public interface RowKeyTextFunc {

    /**
     * Convert string text to one row key.
     * 
     * @param text text.
     * @return one row key.
     * */
    public RowKey func(String text);

    /**
     * Func's name.
     * 
     * @return name.
     * */
    public String funcName();

    /**
     * Func's description.
     * 
     * @return Func's description.
     * */
    public String desc();
}
