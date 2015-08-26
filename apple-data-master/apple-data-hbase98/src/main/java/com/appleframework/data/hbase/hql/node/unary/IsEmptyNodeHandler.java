package com.appleframework.data.hbase.hql.node.unary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsEmptyNodeHandler extends UnaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsEmptyNode isEmptyNode = new IsEmptyNode();
        super.handle(isEmptyNode, node);
        return isEmptyNode;
    }

}
