package com.appleframework.data.hbase.client.rowkey;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.util.BytesUtil;

/**
 * RowKeyUtil.
 * 
 * @author xinzhi.zhang
 * */
public class RowKeyUtil {
    /**
     * Used by scanners, etc when they want to start at the beginning of a
     * region
     */
    public static RowKey START_ROW = new BytesRowKey(new byte[0]);
    /**
     * Last row in a table.
     */
    public static RowKey END_ROW   = new BytesRowKey(new byte[0]);

    /**
     * Append rowKey's bytes's by appending ZERO byte at tail.
     * 
     * */
    public static RowKey appendZeroToRowKey(RowKey rowKey) {
        byte[] oldKey = rowKey.toBytes();
        byte[] newKey = BytesUtil.merge(oldKey, BytesUtil.ZERO);
        return new BytesRowKey(newKey);
    }

    /**
     * Compute the end row key of specified prefix rowkey.
     * 
     * <pre>
     * The prefixRowKey's bytes can not be empty or all of 0xFF.
     * </pre>
     * 
     * @param prefixRowKey prefixRowKey.
     * @return endRowKey for this prefixRowKey.
     * @throws Exception 
     * */
    public static RowKey getEndRowKeyOfPrefix(RowKey prefixRowKey) throws Exception {
        byte[] rowkeyBytes = prefixRowKey.toBytes();
        boolean isAllByteIsFF = true;
        for (byte b : rowkeyBytes) {
            if ((b & 0xFF) != 0xFF) {
                isAllByteIsFF = false;
                break;
            }
        }

        if (isAllByteIsFF) {
            throw new Exception(
                    "the prefix row key is all of 0xFF. prefixRowKey="
                            + prefixRowKey);
        }

        return new BytesRowKey(BytesUtil.increaseLastByte(rowkeyBytes));
    }
}
