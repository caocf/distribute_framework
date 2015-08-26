package com.appleframework.data.hbase.hql.node.text;

import java.util.Map;

import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;
import com.appleframework.data.hbase.hql.HQLNodeType;

/**
 * @author xinzhi
 */
public class TextNode extends BaseTextNode {

    protected TextNode() {
        super(HQLNodeType.Text);
    }

    @Override
    public void applyParaMap(Map<String, Object> para, StringBuilder sb,
            Map<Object, Object> context,
            SimpleHbaseRuntimeSetting runtimeSetting) {
        if (getTextValue() != null) {
            sb.append(BlankSpace);
            sb.append(getTextValue());
        }
    }
}
