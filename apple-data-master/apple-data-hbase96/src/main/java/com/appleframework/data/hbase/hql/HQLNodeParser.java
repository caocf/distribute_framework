package com.appleframework.data.hbase.hql;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.appleframework.data.hbase.hql.node.DynamicNodeHandler;
import com.appleframework.data.hbase.hql.node.StatementNodeHandler;
import com.appleframework.data.hbase.hql.node.binary.IsEqualNodeHandler;
import com.appleframework.data.hbase.hql.node.binary.IsGreaterEqualNodeHandler;
import com.appleframework.data.hbase.hql.node.binary.IsGreaterThanNodeHandler;
import com.appleframework.data.hbase.hql.node.binary.IsLessEqualNodeHandler;
import com.appleframework.data.hbase.hql.node.binary.IsLessThanNodeHandler;
import com.appleframework.data.hbase.hql.node.binary.IsNotEqualNodeHandler;
import com.appleframework.data.hbase.hql.node.text.CDATASectionNodeHandler;
import com.appleframework.data.hbase.hql.node.text.CommentNodeHandler;
import com.appleframework.data.hbase.hql.node.text.TextNodeHandler;
import com.appleframework.data.hbase.hql.node.unary.IsEmptyNodeHandler;
import com.appleframework.data.hbase.hql.node.unary.IsNotEmptyNodeHandler;
import com.appleframework.data.hbase.hql.node.unary.IsNotNullNodeHandler;
import com.appleframework.data.hbase.hql.node.unary.IsNotPropertyAvailableNodeHandler;
import com.appleframework.data.hbase.hql.node.unary.IsNullNodeHandler;
import com.appleframework.data.hbase.hql.node.unary.IsPropertyAvailableNodeHandler;
import com.appleframework.data.hbase.util.Util;

/**
 * HQLNodeParser.
 * 
 * @author xinzhi
 * */
public class HQLNodeParser {

    private static Map<HQLNodeType, HQLNodeHandler> hsqlNodeHandlers = new HashMap<HQLNodeType, HQLNodeHandler>();

    private static void register(HQLNodeType hqlNodeType,
            HQLNodeHandler hqlNodeHandler) {
        hsqlNodeHandlers.put(hqlNodeType, hqlNodeHandler);
    }

    static {
        register(HQLNodeType.Statement, new StatementNodeHandler());

        register(HQLNodeType.Text, new TextNodeHandler());
        register(HQLNodeType.Comment, new CommentNodeHandler());
        register(HQLNodeType.CDATASection, new CDATASectionNodeHandler());

        register(HQLNodeType.Dynamic, new DynamicNodeHandler());

        register(HQLNodeType.IsNull, new IsNullNodeHandler());
        register(HQLNodeType.IsNotNull, new IsNotNullNodeHandler());

        register(HQLNodeType.IsEmpty, new IsEmptyNodeHandler());
        register(HQLNodeType.IsNotEmpty, new IsNotEmptyNodeHandler());

        register(HQLNodeType.IsPropertyAvailable,
                new IsPropertyAvailableNodeHandler());
        register(HQLNodeType.IsNotPropertyAvailable,
                new IsNotPropertyAvailableNodeHandler());

        register(HQLNodeType.IsEqual, new IsEqualNodeHandler());
        register(HQLNodeType.IsNotEqual, new IsNotEqualNodeHandler());

        register(HQLNodeType.IsGreaterThan, new IsGreaterThanNodeHandler());
        register(HQLNodeType.IsGreaterEqual, new IsGreaterEqualNodeHandler());

        register(HQLNodeType.IsLessThan, new IsLessThanNodeHandler());
        register(HQLNodeType.IsLessEqual, new IsLessEqualNodeHandler());

    }

    public static HQLNode parse(Node node) {

        Util.checkNull(node);

        HQLNodeType hqlNodeType = HQLNodeType.findHQLNodeType(node);
        HQLNodeHandler hqlNodeHandler = hsqlNodeHandlers.get(hqlNodeType);
        HQLNode hqlNode = hqlNodeHandler.handle(node);
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);
            HQLNode subHqlNode = parse(subNode);
            subHqlNode.setParent(hqlNode);
            hqlNode.addSubHQLNode(subHqlNode);
        }

        return hqlNode;
    }
}
