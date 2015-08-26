package com.appleframework.data.hbase.hql.node.text;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
/**
 * @author xinzhi
 */
public class CommentNodeHandler extends BaseTextNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        CommentNode commentNode = new CommentNode();
        super.handle(commentNode, node);
        return commentNode;
    }

}
