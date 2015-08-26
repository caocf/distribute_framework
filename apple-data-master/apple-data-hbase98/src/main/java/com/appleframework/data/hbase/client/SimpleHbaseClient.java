package com.appleframework.data.hbase.client;

import com.appleframework.data.hbase.client.service.BasicService;
import com.appleframework.data.hbase.client.service.HBaseDataSourceAware;
import com.appleframework.data.hbase.client.service.HBaseTableConfigAware;
import com.appleframework.data.hbase.client.service.HbaseMultipleVersionService;
import com.appleframework.data.hbase.client.service.HbaseRawService;
import com.appleframework.data.hbase.client.service.HbaseService;
import com.appleframework.data.hbase.client.service.SimpleHbaseRuntimeSettingAware;
import com.appleframework.data.hbase.client.service.SimpleHbaseVersionedService;

/**
 * SimpleHbaseClient.
 * 
 * <pre>
 * The main entry point to use SimpleHbase framework.
 * </pre>
 * 
 * @author xinzhi
 * */
public interface SimpleHbaseClient extends BasicService,
        SimpleHbaseVersionedService, HbaseService, HbaseMultipleVersionService,
        HBaseDataSourceAware, SimpleHbaseRuntimeSettingAware,
        HBaseTableConfigAware, HbaseRawService {

}
