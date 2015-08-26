package com.appleframework.data.hbase.hql.node.binary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsLessEqualNodeHandler extends BinaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsLessEqualNode isLessEqualNode = new IsLessEqualNode();
        super.handle(isLessEqualNode, node);
        return isLessEqualNode;
    }
}
