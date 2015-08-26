package com.appleframework.data.hbase.hql.node.binary;

import java.util.Map;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;

import com.appleframework.data.hbase.hql.HQLNodeType;

/**
 * @author xinzhi
 */
public class IsEqualNode extends BinaryNode {

    protected IsEqualNode() {
        super(HQLNodeType.IsEqual);
    }

    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para,
            SimpleHbaseRuntimeSetting runtimeSetting) {
        Object propertyObject = para.get(getProperty());
        Object compareObject = runtimeSetting.interpret(
                propertyObject.getClass(), getCompareValue());
        return propertyObject.equals(compareObject);
    }
}
