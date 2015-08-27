package cn.afterturn.http.entity.enums;

/**
 * 签名类型
 * 
 * @author JueYue
 * @date 2014年10月14日 下午3:16:55
 */
public enum SignTypeEnmu {

    NULL("不签名"), 
    MD5("按照name顺序排序签名,签名小写,名称=值这样计算"),
    DESC_MD5("按照倒叙顺序排序签名,签名小写,名称=值这样计算"),
    ORDER_MD5("按照Order顺序排序签名,签名小写,名称=值这样计算"),
    UPPER_MD5("按照name顺序排序签名,签名大写,名称=值这样计算"),
    UPPER_DESC_MD5("按照倒叙顺序排序签名,签名大写,名称=值这样计算"),
    UPPER_ORDER_MD5("按照Order顺序排序签名,签名大写,名称=值这样计算"), 
    KEY_MD5( "按照name顺序排序签名,签名小写,名称=值这样计算"), 
    KEY_DESC_MD5("按照倒叙顺序排序签名,签名小写,值相加计算"), 
    KEY_ORDER_MD5("按照Order顺序排序签名,签名小写,值相加计算"), 
    KEY_UPPER_MD5("按照name顺序排序签名,签名大写,值相加计算"), 
    KEY_UPPER_DESC_MD5("按照倒叙顺序排序签名,签名大写,值相加计算"), 
    KEY_UPPER_ORDER_MD5("按照Order顺序排序签名,签名大写,值相加计算");

    private String value;

    SignTypeEnmu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
