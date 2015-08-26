package com.appleframework.data.hbase.client.rowkey;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.util.EncodingUtil;

/**
 * BytesRowKey.
 * 
 * @author xinzhi.zhang
 * */
public class BytesRowKey implements RowKey {

    private byte[] key;

    public BytesRowKey(byte[] key) {
        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "BytesRowKey [key=" + EncodingUtil.toHexString(key) + "]";
    }
}
