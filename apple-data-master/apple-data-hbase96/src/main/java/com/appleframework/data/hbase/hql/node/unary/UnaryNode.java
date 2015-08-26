package com.appleframework.data.hbase.hql.node.unary;

import com.appleframework.data.hbase.hql.HQLNodeType;
import com.appleframework.data.hbase.hql.node.ConditionNode;
/**
 * @author xinzhi
 */
abstract public class UnaryNode extends ConditionNode {

    private String property;

    protected UnaryNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

}
