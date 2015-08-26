package com.appleframework.data.hbase.client.service.hbaseMultipleVersionService;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import allen.studyhbase.TimeDepend;
import allen.test.CreateTestTable;

import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.SimpleHbaseDOResult;
import com.appleframework.data.hbase.myrecord.MyRecord;
import com.appleframework.data.hbase.myrecord.MyRecordRowKey;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;

/**
 * @author xinzhi
 * */
public class TestPutObjectMV extends MyRecordTestBase {

    @TimeDepend
    @Test
    public void putObjectMV() {
        CreateTestTable.main(null);

        RowKey rowKey = new MyRecordRowKey(1000);

        MyRecord myRecord_1 = parseSlim("id=1000,name=namea,age=1");
        simpleHbaseClient.putObjectMV(rowKey, myRecord_1, new Date());
        sleep(2);

        MyRecord myRecord_2 = parseSlim("id=1000,name=nameb,age=2");
        simpleHbaseClient.putObjectMV(rowKey, myRecord_2, new Date().getTime());
        sleep(2);

        List<SimpleHbaseDOResult<MyRecord>> result = simpleHbaseClient
                .findObjectMV(rowKey, MyRecord.class, null);
        Assert.assertTrue(result.size() == 1);
        Assert.assertEquals(myRecord_2, result.get(0).getT());

        CreateTestTable.main(null);
    }
}
