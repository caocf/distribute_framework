package com.appleframework.data.hbase;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import com.appleframework.data.core.page.Pagination;

/**
 * Callback handling scanner results.
 * Implementations of this interface perform the actula work of extracting results from the
 * {@link ResultScanner} but  without having to worry about exception handling or resource management.
 * 
 * @author Costin Leau
 */
public interface PageExtractor<T> {
	
	/**
	 * Implementations must implement this method to process the entire {@link ResultScanner}.  
	 *  
	 * @param results {@link ResultScanner} to extract data from. Implementations should not close this; it will be closed
	 * automatically by the calling {@link HbaseTemplate}
	 * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case). 
	 * @throws Exception if an Hbase exception is encountered
	 */
	Pagination<T> extractData(ResultScanner results, HTableInterface htable, long pageNo, long pageSize) throws Exception;
}

