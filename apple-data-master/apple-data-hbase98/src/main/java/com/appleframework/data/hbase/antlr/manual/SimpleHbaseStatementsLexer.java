package com.appleframework.data.hbase.antlr.manual;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.apache.log4j.Logger;

import com.appleframework.data.hbase.antlr.auto.StatementsLexer;
import com.appleframework.data.hbase.exception.SimpleHBaseException;

/**
 * SimpleHbaseStatementsLexer.
 * 
 * @author xinzhi.zhang
 * */
public class SimpleHbaseStatementsLexer extends StatementsLexer {

    /** log. */
    private static Logger log = Logger.getLogger(SimpleHbaseStatementsLexer.class);

    public SimpleHbaseStatementsLexer(CharStream input) {
        super(input);
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        log.error(e);
        throw new SimpleHBaseException("lexer error.", e);
    }
}
