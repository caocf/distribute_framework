package com.jueyue.onepiece.test.entity;

import cn.afterturn.http.annotation.IRequestParam;

/**
 * Baidu参数请求标签
 * @author JueYue
 * @date 2015年3月12日
 */
public class BaiduWeatherParamEntity {

    private String location;

    @IRequestParam("output")
    private String outPut;

    private String ak;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

}
