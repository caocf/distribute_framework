package com.appleframework.data.hbase.hql.node.text;

import com.appleframework.data.hbase.hql.HQLNode;
import com.appleframework.data.hbase.hql.HQLNodeType;
/**
 * @author xinzhi
 */
abstract public class BaseTextNode extends HQLNode {

    private String textValue;

    protected BaseTextNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String value) {
        this.textValue = value;
    }
}
