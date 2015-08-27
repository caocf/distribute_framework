package com.jueyue.onepiece.test.entity;

import java.util.List;

/**
 * 欧飞价格对象
 * 
 * @author JueYue
 * @date 2014年10月11日 下午5:04:18
 */
public class OfPayPriceEntity {
    private String errMsg;
    /**
     * 返回吗
     */
    private String retcode;

    private List<CardEntity> retCardinfos;

    public String getErrMsg() {
        return errMsg;
    }

    public List<CardEntity> getRetCardinfos() {
        return retCardinfos;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setRetCardinfos(List<CardEntity> retCardinfos) {
        this.retCardinfos = retCardinfos;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }
}
