
package com.appleframework.data.hbase.config;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;

@SuppressWarnings("deprecation")
public class PooledHTableFactory implements HTableInterfaceFactory {

	private HTablePool tablePool;

	public PooledHTableFactory(HTablePool tablePool) {
		this.tablePool = tablePool;
	}

	@Override
	public HTableInterface createHTableInterface(Configuration config, byte[] tableName) {
		return tablePool.getTable(tableName);
	}

	@Override
	public void releaseHTableInterface(HTableInterface table) throws IOException {
		table.close();
	}

}