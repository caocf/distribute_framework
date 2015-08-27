package com.onepiece.requestproxy.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.afterturn.http.entity.enums.SignTypeEnmu;
import cn.afterturn.http.entity.params.ParamsEntity;
import cn.afterturn.http.util.CalculateSignUtil;

public class CalculateSignUtilTest {

    private String key = "http://api2.ofpay.com/onlineorder.do?version=6.0"
                         + "&userpws=d49a3dad5af1f95d6a07f6d35327a8de&userid=A991265"
                         + "&sporder_time=20141022174845&sporder_id=0b6007134-5d45-41c9-baa9-24055606e2ec"
                         + "&game_userid=12345678&cardnum=1&cardid=220616&md5_str=4435E4D2B4D05F8D73C296E281F70886";

    @Test
    public void test() {
        String key = "A991265 d49a3dad5af1f95d6a07f6d35327a8de 220616 1 0b6007134-5d45-41c9-baa9-24055606e2ec 20141022174845 12345678 OFCARD";
        key = key.replaceAll(" ", "");
        System.out.println(key);
        System.out.println(CalculateSignUtil.getSignMD5(key));
        List<ParamsEntity> list = new ArrayList<ParamsEntity>();
        ParamsEntity e = new ParamsEntity();
        e.setName("version");
        e.setValue("6.0");
        e.setSign(false);
        list.add(e);
        e = new ParamsEntity();
        e.setName("userid");
        e.setValue("A991265");
        e.setSign(true);
        e.setOrder(1);
        list.add(e);
        e = new ParamsEntity();
        e.setName("userpws");
        e.setValue("d49a3dad5af1f95d6a07f6d35327a8de");
        e.setSign(true);
        e.setOrder(2);
        list.add(e);
        e = new ParamsEntity();
        e.setName("cardid");
        e.setValue("220616");
        e.setSign(true);
        e.setOrder(3);
        list.add(e);
        e = new ParamsEntity();
        e.setName("cardnum");
        e.setValue("1");
        e.setSign(true);
        e.setOrder(4);
        list.add(e);
        e = new ParamsEntity();
        e.setName("sporder_id");
        e.setValue("0b6007134-5d45-41c9-baa9-24055606e2ec");
        e.setSign(true);
        e.setOrder(5);
        list.add(e);
        e = new ParamsEntity();
        e.setName("sporder_time");
        e.setValue("20141022174845");
        e.setSign(true);
        e.setOrder(6);
        list.add(e);
        e = new ParamsEntity();
        e.setName("game_userid");
        e.setValue("12345678");
        e.setSign(true);
        e.setOrder(7);
        list.add(e);
        key = CalculateSignUtil.signCal(SignTypeEnmu.KEY_UPPER_ORDER_MD5, "OFCARD", list);
        System.out.println(key);
    }

}
