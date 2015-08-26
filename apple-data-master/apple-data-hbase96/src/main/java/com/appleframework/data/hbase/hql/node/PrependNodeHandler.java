package com.appleframework.data.hbase.hql.node;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNodeHandler;
import com.appleframework.data.hbase.util.XmlUtil;
/**
 * @author xinzhi
 */
abstract public class PrependNodeHandler implements HQLNodeHandler {

    public void handle(PrependNode prependNode, Node node) {
        prependNode.setPrependValue(XmlUtil.getAttr(node, "prepend"));
    }
}
