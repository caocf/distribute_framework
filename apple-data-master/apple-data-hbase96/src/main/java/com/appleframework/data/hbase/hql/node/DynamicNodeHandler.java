package com.appleframework.data.hbase.hql.node;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class DynamicNodeHandler extends PrependNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        DynamicNode dynamicNode = new DynamicNode();
        super.handle(dynamicNode, node);
        return dynamicNode;
    }
}