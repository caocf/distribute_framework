package com.appleframework.data.hbase.client.service;

import java.util.List;

import com.appleframework.data.hbase.client.SimpleHbaseCellResult;

/**
 * HbaseRawService.
 * 
 * <pre>
 * Provides hbase raw service.
 * </pre>
 * 
 * @author xinzhi.zhang
 * */
public interface HbaseRawService {

    /**
     * put data.
     * */
    public void put(String hql);

    /**
     * select data.
     * */
    public List<List<SimpleHbaseCellResult>> select(String hql);

    /**
     * delete data.
     * */
    public void delete(String hql);

}
