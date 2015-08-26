package com.appleframework.data.hbase.client.service.basicService;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.appleframework.data.hbase.myrecord.Gender;
import com.appleframework.data.hbase.myrecord.MyRecordTestBase;
import com.appleframework.data.hbase.myrecord.MyRecordV2;
import com.appleframework.data.hbase.myrecord.MyRecordV3;

/**
 * @author xinzhi
 */
public class TestOtherWayToConfigJOPO extends MyRecordTestBase {

    @Test
    public void putAndGetUseInAir() {

        MyRecordV2 myRecordV2 = new MyRecordV2();
        myRecordV2.setId(0);
        myRecordV2.setAge(100L);
        myRecordV2.setDate(new Date());
        myRecordV2.setGender(Gender.FEMALE);
        myRecordV2.setName("allen");

        simpleHbaseClient.putObject(myRecordV2.rowKey(), myRecordV2);

        MyRecordV2 result = simpleHbaseClient.findObject(myRecordV2.rowKey(),
                MyRecordV2.class);

        Assert.assertTrue(myRecordV2.equals(result));
    }

    @Test
    public void putAndGetUseXmlConfig() {

        MyRecordV3 myRecordV3 = new MyRecordV3();
        myRecordV3.setId(0);
        myRecordV3.setAge(100L);
        myRecordV3.setDate(new Date());
        myRecordV3.setGender(Gender.FEMALE);
        myRecordV3.setName("allen");

        simpleHbaseClient.putObject(myRecordV3.rowKey(), myRecordV3);

        MyRecordV3 result = simpleHbaseClient.findObject(myRecordV3.rowKey(),
                MyRecordV3.class);

        Assert.assertTrue(myRecordV3.equals(result));
    }
}
