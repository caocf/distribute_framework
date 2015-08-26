package com.appleframework.data.hbase.client;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TimeSnapshotDOResult.
 * 
 * <pre>
 * All the cell on exact same timestamp are mapped to one DO.
 * </pre>
 * 
 * @author xinzhi.zhang
 * */
public class SimpleHbaseDOResult<T> {
    /** rowkey. */
    private RowKey rowKey;
    /** The mapping result of DO. */
    private T      t;

    /** Hbase timestamp. */
    private long   timestamp;
    /** Hbase timestamp. */
    private Date   tsDate;

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        this.tsDate = new Date(this.timestamp);
    }

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public Date getTsDate() {
        return tsDate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public RowKey getRowKey() {
        return rowKey;
    }

    public void setRowKey(RowKey rowKey) {
        this.rowKey = rowKey;
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
        return ToStringBuilder.reflectionToString(this);
    }
}
