package com.appleframework.data.hbase.hql.node;

import java.util.Map;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;
import com.appleframework.data.hbase.hql.HQLNode;
import com.appleframework.data.hbase.hql.HQLNodeType;
import com.appleframework.data.hbase.util.Util;

/**
 * @author xinzhi
 */
public class StatementNode extends HQLNode {
    protected StatementNode() {
        super(HQLNodeType.Statement);
    }

    @Override
    public void applyParaMap(Map<String, Object> para, StringBuilder sb,
            Map<Object, Object> context,
            SimpleHbaseRuntimeSetting runtimeSetting) {
        Util.checkNull(sb);
        Util.checkNull(context);
        Util.checkNull(runtimeSetting);

        for (HQLNode hqlNode : subNodeList) {
            hqlNode.applyParaMap(para, sb, context, runtimeSetting);
        }

    }

}
