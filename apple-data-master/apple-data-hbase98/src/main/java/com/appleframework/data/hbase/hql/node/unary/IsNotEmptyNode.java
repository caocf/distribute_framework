package com.appleframework.data.hbase.hql.node.unary;

import java.util.Collection;
import java.util.Map;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;
import com.appleframework.data.hbase.exception.SimpleHBaseException;
import com.appleframework.data.hbase.hql.HQLNodeType;

/**
 * @author xinzhi
 */
public class IsNotEmptyNode extends UnaryNode {

    protected IsNotEmptyNode() {
        super(HQLNodeType.IsNotEmpty);
    }

    @Override
    protected boolean isConditionSatisfied(Map<String, Object> para,
            SimpleHbaseRuntimeSetting runtimeSetting) {
        if (para.containsKey(getProperty())) {
            Object value = para.get(getProperty());
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return !String.class.cast(value).isEmpty();
            }
            if (value instanceof Collection) {
                return !Collection.class.cast(value).isEmpty();
            }
        }
        throw new SimpleHBaseException("IsNotEmptyNode invalid value.");
    }
}
