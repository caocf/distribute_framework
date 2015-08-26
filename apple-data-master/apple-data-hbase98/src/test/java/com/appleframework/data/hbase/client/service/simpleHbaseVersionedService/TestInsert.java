package com.appleframework.data.hbase.client.service.simpleHbaseVersionedService;

import org.junit.Assert;
import org.junit.Test;

import com.appleframework.data.hbase.myrecord.MyRecord;
import com.appleframework.data.hbase.myrecord.MyRecordRowKey;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;
/**
 * @author xinzhi
 */
public class TestInsert extends MyRecordTestBase {

    @Test
    public void insertObject() {
        MyRecord myRecord = mockSlim(0);

        MyRecordRowKey myRecordRowKey = new MyRecordRowKey(1);

        Assert.assertTrue(simpleHbaseClient.insertObject(myRecordRowKey,
                myRecord));
        Assert.assertFalse(simpleHbaseClient.insertObject(myRecordRowKey,
                myRecord));
    }
}
