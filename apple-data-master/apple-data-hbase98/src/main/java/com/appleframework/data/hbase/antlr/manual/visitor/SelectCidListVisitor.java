package com.appleframework.data.hbase.antlr.manual.visitor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appleframework.data.hbase.antlr.auto.StatementsBaseVisitor;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.CidList_CidListContext;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.CidList_RegxContext;
import com.appleframework.data.hbase.antlr.auto.StatementsParser.CidList_StarContext;
import com.appleframework.data.hbase.antlr.manual.ContextUtil;

import com.appleframework.data.hbase.config.HBaseColumnSchema;
import com.appleframework.data.hbase.config.HBaseTableConfig;
import com.appleframework.data.hbase.config.SimpleHbaseConstants;
import com.appleframework.data.hbase.util.Util;
/**
 * @author xinzhi
 * */
public class SelectCidListVisitor extends
        StatementsBaseVisitor<List<HBaseColumnSchema>> {

    private HBaseTableConfig hbaseTableConfig;

    public SelectCidListVisitor(HBaseTableConfig hbaseTableConfig) {
        this.hbaseTableConfig = hbaseTableConfig;
    }

    @Override
    public List<HBaseColumnSchema> visitCidList_CidList(
            CidList_CidListContext ctx) {

        return ContextUtil.parseHBaseColumnSchemaList(hbaseTableConfig,
                ctx.cidList());
    }

    @Override
    public List<HBaseColumnSchema> visitCidList_Star(CidList_StarContext ctx) {
        return hbaseTableConfig.getHbaseTableSchema().findAllColumnSchemas();
    }

    @Override
    public List<HBaseColumnSchema> visitCidList_Regx(CidList_RegxContext ctx) {
        String regx = ctx.TEXT().getText();
        Util.checkEmptyString(regx);

        List<HBaseColumnSchema> list = hbaseTableConfig.getHbaseTableSchema()
                .findAllColumnSchemas();
        Pattern p = Pattern.compile(regx);

        for (int i = list.size() - 1; i >= 0; i--) {
            HBaseColumnSchema hBaseColumnSchema = list.get(i);
            String s = hBaseColumnSchema.getFamily()
                    + SimpleHbaseConstants.Family_Qualifier_Separator
                    + hBaseColumnSchema.getQualifier();
            Matcher matcher = p.matcher(s);
            if (!matcher.matches()) {
                list.remove(i);
            }
        }

        return list;
    }
}
