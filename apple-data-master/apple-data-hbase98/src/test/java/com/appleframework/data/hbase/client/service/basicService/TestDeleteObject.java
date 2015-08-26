package com.appleframework.data.hbase.client.service.basicService;

import org.junit.Assert;
import org.junit.Test;

import com.appleframework.data.hbase.myrecord.MyRecord;
import com.appleframework.data.hbase.myrecord.MyRecordRowKey;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;

/**
 * @author xinzhi
 */
public class TestDeleteObject extends MyRecordTestBase {
    @Test
    public void deleteObject() {

        putMockSlims(1);

        MyRecordRowKey myRecordRowKey = new MyRecordRowKey(0);

        Assert.assertNotNull(simpleHbaseClient.findObject(myRecordRowKey,
                MyRecord.class));

        simpleHbaseClient.deleteObject(myRecordRowKey, MyRecord.class);

        Assert.assertNull(simpleHbaseClient.findObject(myRecordRowKey,
                MyRecord.class));
    }

    @Test
    public void deleteObject_PutObjectManyTimes() {

        putMockSlims(1);
        sleep(2);
        putMockSlims(1);

        MyRecordRowKey myRecordRowKey = new MyRecordRowKey(0);

        Assert.assertNotNull(simpleHbaseClient.findObject(myRecordRowKey,
                MyRecord.class));

        simpleHbaseClient.deleteObject(myRecordRowKey, MyRecord.class);

        Assert.assertNull(simpleHbaseClient.findObject(myRecordRowKey,
                MyRecord.class));
    }

}