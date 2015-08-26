package com.appleframework.data.hbase.hql.node.unary;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.node.PrependNodeHandler;
import com.appleframework.data.hbase.util.XmlUtil;
/**
 * @author xinzhi
 */
abstract public class UnaryNodeHandler extends PrependNodeHandler {

    public void handle(UnaryNode unaryNode, Node node) {
        unaryNode.setProperty(XmlUtil.getAttr(node, "property"));
        super.handle(unaryNode, node);
    }
}
