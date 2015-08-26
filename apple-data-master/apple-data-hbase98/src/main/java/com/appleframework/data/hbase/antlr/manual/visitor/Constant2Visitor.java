package com.appleframework.data.hbase.antlr.manual.visitor;

import com.appleframework.data.hbase.antlr.auto.StatementsBaseVisitor;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.Constant2_NotNullContext;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.Constant2_NullContext;
import com.appleframework.data.hbase.antlr.manual.ContextUtil;
import com.appleframework.data.hbase.config.HBaseColumnSchema;
import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;

/**
 * @author xinzhi
 * */
public class Constant2Visitor extends StatementsBaseVisitor<Object> {

    private HBaseColumnSchema         hbaseColumnSchema;
    private SimpleHbaseRuntimeSetting simpleHbaseRuntimeSetting;

    public Constant2Visitor(HBaseColumnSchema hbaseColumnSchema,
            SimpleHbaseRuntimeSetting simpleHbaseRuntimeSetting) {
        this.hbaseColumnSchema = hbaseColumnSchema;
        this.simpleHbaseRuntimeSetting = simpleHbaseRuntimeSetting;
    }

    @Override
    public Object visitConstant2_Null(Constant2_NullContext ctx) {
        return null;
    }

    @Override
    public Object visitConstant2_NotNull(Constant2_NotNullContext ctx) {
        return ContextUtil.parseConstant(hbaseColumnSchema, ctx.constant(),
                simpleHbaseRuntimeSetting);
    }

}
