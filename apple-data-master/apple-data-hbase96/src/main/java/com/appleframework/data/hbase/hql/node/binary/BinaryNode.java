package com.appleframework.data.hbase.hql.node.binary;

import com.appleframework.data.hbase.hql.HQLNodeType;
import com.appleframework.data.hbase.hql.node.ConditionNode;

/**
 * @author xinzhi
 */
abstract public class BinaryNode extends ConditionNode {

    private String property;
    private String compareValue;

    protected BinaryNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

}
