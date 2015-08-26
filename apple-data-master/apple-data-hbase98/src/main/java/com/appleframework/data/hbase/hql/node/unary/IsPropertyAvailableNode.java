package com.appleframework.data.hbase.hql.node.unary;

import java.util.Map;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;

import com.appleframework.data.hbase.hql.HQLNodeType;

/**
 * @author xinzhi
 */
public class IsPropertyAvailableNode extends UnaryNode {

    protected IsPropertyAvailableNode() {
        super(HQLNodeType.IsPropertyAvailable);
    }

    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para,
            SimpleHbaseRuntimeSetting runtimeSetting) {
        return para.containsKey(getProperty());
    }

}