package com.appleframework.data.hbase.hql.node.unary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsNotNullNodeHandler extends UnaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsNotNullNode isNotNullNode = new IsNotNullNode();
        super.handle(isNotNullNode, node);
        return isNotNullNode;
    }

}
