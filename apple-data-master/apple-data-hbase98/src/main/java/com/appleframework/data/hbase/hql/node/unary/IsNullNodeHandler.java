package com.appleframework.data.hbase.hql.node.unary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsNullNodeHandler extends UnaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsNullNode isNullNode = new IsNullNode();
        super.handle(isNullNode, node);
        return isNullNode;
    }

}
