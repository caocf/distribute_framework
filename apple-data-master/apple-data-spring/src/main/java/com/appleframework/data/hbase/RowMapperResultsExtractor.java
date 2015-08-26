package com.appleframework.data.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.util.Assert;

/**
 * Adapter encapsulating the RowMapper callback. 
 * 
 * @author Costin Leau
 */
class RowMapperResultsExtractor<T> implements ResultsExtractor<List<T>> {

	private final RowMapper<T> rowMapper;

	/**
	 * Create a new RowMapperResultSetExtractor.
	 * @param rowMapper the RowMapper which creates an object for each row
	 */
	public RowMapperResultsExtractor(RowMapper<T> rowMapper) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
	}

	public List<T> extractData(ResultScanner results, HTableInterface htable) throws Exception {
		List<T> rs = new ArrayList<T>();
		int rowNum = 0;
		for (Result result : results) {
			byte[] row = result.getRow();
			Get get = new Get(row);
			rs.add(this.rowMapper.mapRow(htable.get(get), rowNum++));
		}
		return rs;
	}
	
}
