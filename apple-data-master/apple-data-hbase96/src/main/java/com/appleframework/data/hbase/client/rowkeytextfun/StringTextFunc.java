package com.appleframework.data.hbase.client.rowkeytextfun;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.rowkey.StringRowKey;

/**
 * @author xinzhi
 * */
public class StringTextFunc implements RowKeyTextFunc {

    @Override
    public RowKey func(String text) {
        return new StringRowKey(text);
    }

    @Override
    public String funcName() {
        return "stringkey";
    }

    @Override
    public String desc() {
        return "use string as rowkey.";
    }

}