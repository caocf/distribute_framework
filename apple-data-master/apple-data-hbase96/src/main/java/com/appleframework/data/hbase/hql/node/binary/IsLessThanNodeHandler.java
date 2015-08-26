package com.appleframework.data.hbase.hql.node.binary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsLessThanNodeHandler extends BinaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsLessThanNode isLessThanNode = new IsLessThanNode();
        super.handle(isLessThanNode, node);
        return isLessThanNode;
    }
}
