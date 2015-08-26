package com.appleframework.data.hbase.hql.node.binary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsNotEqualNodeHandler extends BinaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsNotEqualNode isNotEqualNode = new IsNotEqualNode();
        super.handle(isNotEqualNode, node);
        return isNotEqualNode;
    }
}
