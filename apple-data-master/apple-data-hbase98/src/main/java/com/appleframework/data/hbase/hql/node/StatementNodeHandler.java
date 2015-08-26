package com.appleframework.data.hbase.hql.node;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.hql.HQLNode;
import com.appleframework.data.hbase.hql.HQLNodeHandler;

/**
 * @author xinzhi
 */
public class StatementNodeHandler implements HQLNodeHandler {

    @Override
    public HQLNode handle(Node node) {
        StatementNode statementNode = new StatementNode();
        return statementNode;
    }
}
