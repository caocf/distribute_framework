package com.appleframework.data.hbase.hql.node.unary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsPropertyAvailableNodeHandler extends UnaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsPropertyAvailableNode isPropertyAvailableNode = new IsPropertyAvailableNode();
        super.handle(isPropertyAvailableNode, node);
        return isPropertyAvailableNode;
    }

}
