package com.appleframework.data.hbase.client.service.basicService;

import org.junit.Assert;
import org.junit.Test;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.myrecord.MyRecord;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;

/**
 * @author xinzhi
 */
public class TestPutObject extends MyRecordTestBase {

    @Test
    public void putObject() {

        MyRecord myRecord = mockSlim(0);
        RowKey rowKey = myRecord.rowKey();

        simpleHbaseClient.putObject(rowKey, myRecord);

        MyRecord result = simpleHbaseClient.findObject(rowKey, MyRecord.class);

        Assert.assertTrue(myRecord.equals(result));
    }

}
