package com.jueyue.onepiece.test.request;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.http.util.IJsonServer;
import cn.afterturn.http.util.JsonServerOfJackson;

import com.jueyue.onepiece.test.entity.BaiduWeatherEntity;
import com.jueyue.onepiece.test.entity.BaiduWeatherParamEntity;
import com.jueyue.onepiece.test.spring.SpringTxTestCase;

public class ITestRequestTest extends SpringTxTestCase {

    @Autowired
    private ITestRequest testRequest;

    @Autowired
    private IJsonServer  jsonServer;

    @Test
    public void testGet() {
        String re = testRequest.testGet("北京", "json", "5slgyqGDENN7Sy7pw29IUvrZ",
            "http://api.map.baidu.com/telematics/v3/weather");
        System.out.println(re);

        BaiduWeatherEntity entity = jsonServer.parseJson(re, BaiduWeatherEntity.class);

        System.out.println(entity.getStatus());

        entity = testRequest.testGetEntity("北京", "json", "5slgyqGDENN7Sy7pw29IUvrZ");
        System.out.println(jsonServer.toJson(entity));

        BaiduWeatherParamEntity baiduWeatherParamEntity = new BaiduWeatherParamEntity();
        baiduWeatherParamEntity.setAk("5slgyqGDENN7Sy7pw29IUvrZ");
        baiduWeatherParamEntity.setLocation("北京");
        baiduWeatherParamEntity.setOutPut("json");

        entity = testRequest.testGetEntity(baiduWeatherParamEntity);
        System.out.println(jsonServer.toJson(entity));

    }

    //@Test
    public void getMethod() {
        GetMethod getMethod = null;
        try {
            getMethod = new GetMethod(
                "http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 释放连接
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
    }

}
