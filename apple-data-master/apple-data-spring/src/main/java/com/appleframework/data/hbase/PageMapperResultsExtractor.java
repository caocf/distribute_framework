package com.appleframework.data.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.util.Assert;

import com.appleframework.data.core.page.Pagination;

/**
 * Adapter encapsulating the RowMapper callback. 
 * 
 * @author Costin Leau
 */
class PageMapperResultsExtractor<T> implements PageExtractor<T> {

	private final RowMapper<T> rowMapper;

	/**
	 * Create a new RowMapperResultSetExtractor.
	 * @param rowMapper the RowMapper which creates an object for each row
	 */
	public PageMapperResultsExtractor(RowMapper<T> rowMapper) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
	}
	
	public Pagination<T> extractData(ResultScanner results, HTableInterface htable, long pageNo, long pageSize) 
			throws Exception {
		List<T> rs = new ArrayList<T>();
		long firstIndex = (pageNo - 1) * pageSize;
		long endIndex = firstIndex + pageSize;

		int rowNum = 0;
		for (Result result : results) {
			if (rowNum >= firstIndex && rowNum < endIndex) {
				byte[] row = result.getRow();
				Get get = new Get(row);
				rs.add(this.rowMapper.mapRow(htable.get(get), rowNum));
			}
			rowNum ++;
		}
		Pagination<T> page = new Pagination<T>(pageNo, pageSize, rowNum);
		page.setList(rs);
		return page;
	}
}
