package com.appleframework.data.hbase.client.rowkeytextfun;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.rowkey.BytesRowKey;
import com.appleframework.data.hbase.util.EncodingUtil;

/**
 * HexBytesTextFunc.
 * 
 * @author xinzhi.zhang
 * */
public class HexBytesTextFunc implements RowKeyTextFunc {

    @Override
    public RowKey func(String hexStr) {
        return new BytesRowKey(EncodingUtil.parseBytesFromHexString(hexStr));
    }

    @Override
    public String funcName() {
        return "hexkey";
    }

    @Override
    public String desc() {
        return "use hex string as rowkey";
    }

}