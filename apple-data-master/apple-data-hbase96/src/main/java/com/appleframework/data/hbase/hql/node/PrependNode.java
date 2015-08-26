package com.appleframework.data.hbase.hql.node;

import com.appleframework.data.hbase.hql.HQLNode;
import com.appleframework.data.hbase.hql.HQLNodeType;
/**
 * @author xinzhi
 */
abstract public class PrependNode extends HQLNode {

    private String prependValue;

    protected PrependNode(HQLNodeType hqlNodeType) {
        super(hqlNodeType);
    }

    public String getPrependValue() {
        return prependValue;
    }

    public void setPrependValue(String prependValue) {
        this.prependValue = prependValue;
    }
}
