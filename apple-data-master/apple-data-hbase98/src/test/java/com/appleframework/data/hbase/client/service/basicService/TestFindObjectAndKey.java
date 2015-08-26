package com.appleframework.data.hbase.client.service.basicService;

import org.junit.Assert;
import org.junit.Test;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.SimpleHbaseDOWithKeyResult;
import com.appleframework.data.hbase.client.rowkey.BytesRowKey;
import com.appleframework.data.hbase.myrecord.MyRecord;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;

/**
 * @author xinzhi
 */
public class TestFindObjectAndKey extends MyRecordTestBase {

    @Test
    public void findObjectAndKey() {

        MyRecord myRecord = mockSlim(0);
        RowKey rowKey = myRecord.rowKey();

        simpleHbaseClient.putObject(rowKey, myRecord);
        SimpleHbaseDOWithKeyResult<MyRecord> resultRecord = simpleHbaseClient
                .findObjectAndKey(rowKey, MyRecord.class);

        Assert.assertTrue(myRecord.equals(resultRecord.getT()));
        Assert.assertEquals(new BytesRowKey(rowKey.toBytes()),
                resultRecord.getRowKey());
    }
}
