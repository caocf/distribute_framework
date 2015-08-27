package com.jueyue.onepiece.test.request;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jueyue.onepiece.test.spring.SpringTxTestCase;

public class IRequestWebserverTest extends SpringTxTestCase {

    @Autowired
    private IRequestWebserver iRequestWebserver;

    @Test
    public void test() {
         String beijignWeather = iRequestWebserver.testGet("beijing", "china");
         System.out.println(beijignWeather);
        /*  String  qq = iRequestWebserver.testQQ("909217383");
          System.out.println(qq);*/
    }

}
