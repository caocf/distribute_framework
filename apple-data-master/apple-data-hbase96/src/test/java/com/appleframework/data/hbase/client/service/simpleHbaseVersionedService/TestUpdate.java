package com.appleframework.data.hbase.client.service.simpleHbaseVersionedService;

import org.junit.Assert;
import org.junit.Test;

import com.appleframework.data.hbase.myrecord.MyRecord;
import com.appleframework.data.hbase.myrecord.MyRecordRowKey;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;
/**
 * @author xinzhi
 */
public class TestUpdate extends MyRecordTestBase {

    @Test
    public void updateObjectWithVersion() {
        MyRecord myRecord = mockSlim(0);
        myRecord.setVersion(0L);

        MyRecordRowKey myRecordRowKey = new MyRecordRowKey(0);
        simpleHbaseClient.insertObject(myRecordRowKey, myRecord);

        // update with wrong version.
        Assert.assertFalse(simpleHbaseClient.updateObjectWithVersion(
                myRecordRowKey, myRecord, new Long(10L)));

        // update version.
        myRecord.setVersion(1L);
        Assert.assertTrue(simpleHbaseClient.updateObjectWithVersion(
                myRecordRowKey, myRecord, new Long(0L)));

    }

    @Test
    public void updateObject() {
        MyRecord myRecord = mockSlim(0);
        myRecord.setVersion(0L);

        MyRecordRowKey myRecordRowKey = new MyRecordRowKey(0);

        simpleHbaseClient.insertObject(myRecordRowKey, myRecord);
        MyRecord myRecordFromHbase = simpleHbaseClient.findObject(
                myRecordRowKey, MyRecord.class);
        // change version.
        myRecordFromHbase.setVersion(10L);
        Assert.assertFalse(simpleHbaseClient.updateObject(myRecordRowKey,
                myRecordFromHbase, myRecord));

        // change version.
        myRecordFromHbase.setVersion(0L);
        // update version.
        myRecord.setVersion(1L);
        Assert.assertTrue(simpleHbaseClient.updateObject(myRecordRowKey,
                myRecordFromHbase, myRecord));

    }
}
