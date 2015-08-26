package com.appleframework.data.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.hadoop.hbase.HbaseAccessor;
import org.springframework.data.hadoop.hbase.HbaseUtils;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.util.Assert;

import com.appleframework.data.core.page.Pagination;

public class HbaseTemplate2 extends HbaseAccessor implements HbaseOperations {

	private boolean autoFlush = true;

	public HbaseTemplate2() {
	}

	public HbaseTemplate2(Configuration configuration) {
		setConfiguration(configuration);
		afterPropertiesSet();
	}
	
	public HTableInterface getTable(String tableName) {
		return HbaseUtils.getHTable(tableName, getConfiguration(), getCharset(), getTableFactory());
	}

	private void releaseTable(String tableName, HTableInterface table) {
		HbaseUtils.releaseTable(tableName, table, getTableFactory());
	}

	@SuppressWarnings("deprecation")
	private boolean applyFlushSetting(HTableInterface table) {
		boolean autoFlush = table.isAutoFlush();
		/*if (table instanceof HTable) {
			((HTable) table).setAutoFlush(this.autoFlush);
		}*/
		if(autoFlush != this.autoFlush) {
			table.setAutoFlush(this.autoFlush);
		}
		return autoFlush;
	}

	/*@SuppressWarnings("deprecation")
	private void restoreFlushSettings(HTableInterface table, boolean oldFlush) {
		if (table instanceof HTable) {
			if (table.isAutoFlush() != oldFlush) {
				((HTable) table).setAutoFlush(oldFlush);
			}
		}
	}*/

	private void flushIfNecessary(HTableInterface table, boolean oldFlush) throws IOException {
		if(oldFlush == true)
			table.flushCommits();
		//restoreFlushSettings(table, oldFlush);
	}

	public DataAccessException convertHbaseAccessException(Exception ex) {
		return HbaseUtils.convertHbaseException(ex);
	}
	
	/**
	 * Sets the auto flush.
	 *
	 * @param autoFlush The autoFlush to set.
	 */
	public void setAutoFlush(boolean autoFlush) {
		this.autoFlush = autoFlush;
	}
	
	public void save(String tableName, List<Put> puts) {
		Assert.notNull(puts, "Put List must not be null");
		Assert.notNull(tableName, "No table specified");

		HTableInterface table = getTable(tableName);

		try {
			boolean previousFlushSetting = applyFlushSetting(table);
			table.put(puts);
			flushIfNecessary(table, previousFlushSetting);
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseTable(tableName, table);
		}
	}
	
	public void save(String tableName, Put put) {
		Assert.notNull(put, "Put must not be null");
		Assert.notNull(tableName, "No table specified");

		HTableInterface table = getTable(tableName);

		try {
			boolean previousFlushSetting = applyFlushSetting(table);
			table.put(put);
			flushIfNecessary(table, previousFlushSetting);
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseTable(tableName, table);
		}
	}
	
	public void flushCommits(String tableName) {
		Assert.notNull(tableName, "No table specified");
		HTableInterface table = getTable(tableName);
		try {
			flushIfNecessary(table, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public <T> T find(String tableName, String family, final ResultsExtractor<T> action) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(getCharset()));
		return find(tableName, scan, action);
	}

	@Override
	public <T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
		return find(tableName, scan, action);
	}

	@Override
	public <T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action) {
		return execute(tableName, new TableCallback<T>() {
			@Override
			public T doInTable(HTableInterface htable) throws Throwable {
				ResultScanner scanner = htable.getScanner(scan);
				try {
					return action.extractData(scanner, htable);
				} finally {
					scanner.close();
				}
			}
		});
	}
	
	@Override
	public <T> Pagination<T> page(String tableName, final Scan scan, final PageExtractor<T> action, 
			final long pageNo, final long pageSize) {
		return execute(tableName, new PageCallback<T>() {
			@Override
			public Pagination<T> doInPage(HTableInterface htable) throws Throwable {
				ResultScanner scanner = htable.getScanner(scan);
				try {
					return action.extractData(scanner, htable, pageNo, pageSize);
				} finally {
					scanner.close();
				}
			}
		});
	}

	@Override
	public <T> List<T> find(String tableName, String family, final RowMapper<T> action) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(getCharset()));
		return find(tableName, scan, action);
	}
	
	@Override
	public <T> Pagination<T> page(String tableName, String family, final RowMapper<T> action,
			final long pageNo, final long pageSize) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(getCharset()));
		return page(tableName, scan, action, pageNo, pageSize);
	}

	@Override
	public <T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
		return find(tableName, scan, action);
	}
	
	@Override
	public <T> Pagination<T> page(String tableName, String family, String qualifier, final RowMapper<T> action,
			final long pageNo, final long pageSize) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
		return page(tableName, scan, action, pageNo, pageSize);
	}

	@Override
	public <T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action) {
		return find(tableName, scan, new RowMapperResultsExtractor<T>(action));
	}
	
	@Override
	public <T> Pagination<T> page(String tableName, final Scan scan, final RowMapper<T> action, long pageNo, long pageSize) {
		return page(tableName, scan, new PageMapperResultsExtractor<T>(action), pageNo, pageSize);
	}

	@Override
	public <T> T get(String tableName, String rowName, final RowMapper<T> mapper) {
		return get(tableName, rowName, null, null, mapper);
	}

	@Override
	public <T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper) {
		return get(tableName, rowName, familyName, null, mapper);
	}

	@Override
	public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier, 
			final RowMapper<T> mapper) {
		return execute(tableName, new TableCallback<T>() {
			@Override
			public T doInTable(HTableInterface htable) throws Throwable {
				Get get = new Get(rowName.getBytes(getCharset()));
				if (familyName != null) {
					byte[] family = familyName.getBytes(getCharset());

					if (qualifier != null) {
						get.addColumn(family, qualifier.getBytes(getCharset()));
					}
					else {
						get.addFamily(family);
					}
				}
				Result result = htable.get(get);
				return mapper.mapRow(result, 0);
			}
		});
	}

	@Override
	public <T> T execute(String tableName, TableCallback<T> action) {
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(tableName, "No table specified");

		HTableInterface table = getTable(tableName);

		try {
			boolean previousFlushSetting = applyFlushSetting(table);
			T result = action.doInTable(table);
			flushIfNecessary(table, previousFlushSetting);
			return result;
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseTable(tableName, table);
		}
	}
	
	@Override
	public <T> Pagination<T> execute(String tableName, PageCallback<T> action) {
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(tableName, "No table specified");

		HTableInterface table = getTable(tableName);

		try {
			boolean previousFlushSetting = applyFlushSetting(table);
			Pagination<T> result = action.doInPage(table);
			flushIfNecessary(table, previousFlushSetting);
			return result;
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseTable(tableName, table);
		}
	}
	
}
