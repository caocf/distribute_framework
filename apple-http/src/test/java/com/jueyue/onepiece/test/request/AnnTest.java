package com.jueyue.onepiece.test.request;

import org.junit.Test;

import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.util.BaseAnnotation;
import cn.afterturn.http.util.JsonServerOfJackson;

import com.AmapStationModel;

public class AnnTest {

    @Test
    public void test() {
//        try {
//            IRequestMethod iRequest = BaseAnnotation.class.getMethod("requestMethod")
//                .getAnnotation(IRequestMethod.class);
//            System.out.println(iRequest.encode().getValue());
//        } catch (SecurityException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        AmapStationModel m  = new AmapStationModel();
        m.setStatus("12312");
        m.setRecord("112312");
        System.err.println(new JsonServerOfJackson().toJson(m));
        String str = "{\"status\":\"12312\",\"d\":112312}";
        AmapStationModel list = new JsonServerOfJackson().parseJson(str, AmapStationModel.class);                
        System.out.println(list);
    }

}
