package com.appleframework.data.hbase.hql.node.binary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class IsGreaterEqualNodeHandler extends BinaryNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        IsGreaterEqualNode isGreaterEqualNode = new IsGreaterEqualNode();
        super.handle(isGreaterEqualNode, node);
        return isGreaterEqualNode;
    }
}
