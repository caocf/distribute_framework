package com.appleframework.data.hbase.antlr.manual.visitor;

import com.appleframework.data.hbase.antlr.auto.StatementsBaseVisitor;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.Rowkeyrange_endContext;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.Rowkeyrange_onerowkeyContext;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.Rowkeyrange_startAndEndContext;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.Rowkeyrange_startContext;
import com.appleframework.data.hbase.antlr.manual.ContextUtil;
import com.appleframework.data.hbase.antlr.manual.RowKeyRange;
import com.appleframework.data.hbase.client.RowKey;
import com.appleframework.data.hbase.client.rowkey.RowKeyUtil;
import com.appleframework.data.hbase.config.SimpleHbaseRuntimeSetting;

/**
 * RowKeyRange visitor.
 * 
 * @author xinzhi
 * */
public class RowKeyRangeVisitor extends StatementsBaseVisitor<RowKeyRange> {
    private SimpleHbaseRuntimeSetting simpleHbaseRuntimeSetting;

    public RowKeyRangeVisitor(
            SimpleHbaseRuntimeSetting simpleHbaseRuntimeSetting) {
        this.simpleHbaseRuntimeSetting = simpleHbaseRuntimeSetting;
    }

    @Override
    public RowKeyRange visitRowkeyrange_start(
            Rowkeyrange_startContext startContext) {
        RowKeyRange range = new RowKeyRange();
        range.setStart(ContextUtil.parseRowKey(startContext.rowkeyexp(),
                simpleHbaseRuntimeSetting));
        range.setEnd(RowKeyUtil.END_ROW);
        return range;
    }

    @Override
    public RowKeyRange visitRowkeyrange_end(Rowkeyrange_endContext endContext) {
        RowKeyRange range = new RowKeyRange();
        range.setStart(RowKeyUtil.START_ROW);
        range.setEnd(ContextUtil.parseRowKey(endContext.rowkeyexp(),
                simpleHbaseRuntimeSetting));
        return range;
    }

    @Override
    public RowKeyRange visitRowkeyrange_startAndEnd(
            Rowkeyrange_startAndEndContext startAndEndContext) {
        RowKeyRange range = new RowKeyRange();
        range.setStart(ContextUtil.parseRowKey(startAndEndContext.rowkeyexp(0),
                simpleHbaseRuntimeSetting));
        range.setEnd(ContextUtil.parseRowKey(startAndEndContext.rowkeyexp(1),
                simpleHbaseRuntimeSetting));
        return range;
    }

    @Override
    public RowKeyRange visitRowkeyrange_onerowkey(
            Rowkeyrange_onerowkeyContext ctx) {
        RowKeyRange range = new RowKeyRange();
        RowKey rowKey = ContextUtil.parseRowKey(ctx.rowkeyexp(),
                simpleHbaseRuntimeSetting);
        range.setStart(rowKey);
        range.setEnd(rowKey);
        return range;
    }
}
