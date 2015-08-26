package com.appleframework.data.hbase.client.service.hbaseRawService;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import com.appleframework.data.hbase.client.SimpleHbaseCellResult;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;

/**
 * @author xinzhi
 * */
public class RawServiceTestBase extends MyRecordTestBase {

    protected void assertSimpleHbaseCellResult(
            List<SimpleHbaseCellResult> result, String qualifier, Date ts,
            Object expectedObject) {
        for (SimpleHbaseCellResult cell : result) {
            if (cell.getQualifierStr().equals(qualifier)
                    && cell.getTsDate().equals(ts)) {
                Assert.assertEquals(expectedObject, cell.getValueObject());
                return;
            }
        }

        Assert.fail();
    }

    protected void assertSimpleHbaseCellResult(
            List<SimpleHbaseCellResult> result, String qualifier,
            Object expectedObject) {
        for (SimpleHbaseCellResult cell : result) {
            if (cell.getQualifierStr().equals(qualifier)) {
                Assert.assertEquals(expectedObject, cell.getValueObject());
                return;
            }
        }

        Assert.fail();
    }
}
