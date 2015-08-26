package com.appleframework.data.hbase.hql;

import org.w3c.dom.Node;

import com.appleframework.data.hbase.exception.SimpleHBaseException;
import com.appleframework.data.hbase.util.Util;

/**
 * HQL node type.
 * 
 * @author xinzhi
 * */
public enum HQLNodeType {
    //text.
    CDATASection("#cdata-section"),

    Text("#text"),

    Comment("#comment"),

    //top level statement.
    Statement("statement"),

    //dynamic node.
    Dynamic("dynamic"),

    //binary condition node.
    IsEqual("isEqual"),

    IsNotEqual("isNotEqual"),

    IsGreaterThan("isGreaterThan"),

    IsGreaterEqual("isGreaterEqual"),

    IsLessThan("isLessThan"),

    IsLessEqual("isLessEqual"),

    //unary condition node.
    IsNull("isNull"),

    IsNotNull("isNotNull"),

    IsEmpty("isEmpty"),

    IsNotEmpty("isNotEmpty"),

    IsPropertyAvailable("isPropertyAvailable"),

    IsNotPropertyAvailable("isNotPropertyAvailable"),

    ;

    /**
     * The name of xml's node.
     * */
    private String xmlNodeName;

    private HQLNodeType(String xmlNodeName) {
        this.xmlNodeName = xmlNodeName;
    }

    public static HQLNodeType findHQLNodeType(Node node) {

        Util.checkNull(node);

        for (HQLNodeType hqlNodeType : HQLNodeType.values()) {
            if (node.getNodeName().equals(hqlNodeType.xmlNodeName)) {
                return hqlNodeType;
            }
        }

        throw new SimpleHBaseException("parse err. node = " + node);
    }

}
