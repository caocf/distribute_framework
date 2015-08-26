package com.appleframework.data.hbase.client.rowkey;

import junit.framework.Assert;

import org.junit.Test;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.type.ext.HexBytes;
import com.appleframework.data.hbase.util.EncodingUtil;

/**
 * @author xinzhi
 * */
public class TestRowKeyUtil {

    @Test
    public void getEndRowKeyOfPrefix() {
        try {
            RowKeyUtil.getEndRowKeyOfPrefix(null);
            Assert.fail();
        } catch (Exception e) {
            //ignore.
        }

        try {
            RowKeyUtil.getEndRowKeyOfPrefix(new StringRowKey(""));
            Assert.fail();
        } catch (Exception e) {
            //ignore.
        }

        try {
            RowKeyUtil.getEndRowKeyOfPrefix(new BytesRowKey(new HexBytes("FF")
                    .getData()));
            Assert.fail();
        } catch (Exception e) {
            //ignore.
        }

        try {
            RowKeyUtil.getEndRowKeyOfPrefix(new BytesRowKey(new HexBytes(
                    "FF FF").getData()));
            Assert.fail();
        } catch (Exception e) {
            //ignore.
        }

        RowKey endRowKey = RowKeyUtil.getEndRowKeyOfPrefix(new BytesRowKey(
                new HexBytes("FF AB ").getData()));
        Assert.assertEquals("FF AC ",
                EncodingUtil.toHexString(endRowKey.toBytes()));
    }
}
