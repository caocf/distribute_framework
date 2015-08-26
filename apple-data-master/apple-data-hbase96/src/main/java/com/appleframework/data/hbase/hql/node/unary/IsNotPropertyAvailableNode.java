package com.appleframework.data.hbase.hql.node.unary;

import java.util.Map;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;
import com.appleframework.data.hbase.hql.HQLNodeType;

/**
 * @author xinzhi
 */
public class IsNotPropertyAvailableNode extends UnaryNode {

    protected IsNotPropertyAvailableNode() {
        super(HQLNodeType.IsNotPropertyAvailable);
    }

    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para,
            SimpleHbaseRuntimeSetting runtimeSetting) {
        return !para.containsKey(getProperty());
    }

}