package cn.afterturn.http.entity.enums;

/**
 * 请求
 * Created by jue on 14-4-21.
 */
public enum RequestEncodeEnum {

    UTF8("utf-8"), GBK("gbk"), GB2312("gb2312"), ISO("ISO-8859-1");

    private String value;

    RequestEncodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
