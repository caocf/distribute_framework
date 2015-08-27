package com.onepiece.requestproxy.util;

import org.junit.Test;

import cn.afterturn.http.util.JsonServerOfJackson;
import cn.afterturn.http.util.XMLParseUtil;

import com.jueyue.onepiece.test.entity.OfPayPriceEntity;

public class XMLParseUtilTest {

    private String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><cardinfo><err_msg></err_msg><retcode>1</retcode><ret_cardinfos><card><cardid>220616</cardid><cardname>Q币20元直充</cardname><pervalue>20</pervalue><inprice>19.28</inprice><sysddprice>20</sysddprice><sysdd1price>20</sysdd1price><sysdd2price>20</sysdd2price><agentprice>20</agentprice><agentprice1>20</agentprice1><agentprice2>20</agentprice2><memberprice>19.6</memberprice> <innum>30</innum><amounts>1-10</amounts></card></ret_cardinfos></cardinfo>";

    @Test
    public void test() {
        OfPayPriceEntity e = XMLParseUtil.getEntity(xml, OfPayPriceEntity.class, "gb2312");
        System.out.println(new JsonServerOfJackson().toJson(e));
    }

}
