package com.appleframework.data.hbase;


import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.springframework.data.hadoop.hbase.RowMapper;

import com.appleframework.data.core.page.Pagination;


/**
 * Interface that specifies a basic set of Hbase operations, implemented by {@link HbaseTemplate}. Not often used, but a useful option to enhance testability, as it can easily be mocked or stubbed.
 * @author Costin Leau
 */
public interface HbaseOperations {

	/**
	 * Executes the given action against the specified table handling resource management.
	 * <p>
	 * Application exceptions thrown by the action object get propagated to the caller (can only be unchecked). 
	 * Allows for returning a result object (typically a domain object or collection of domain objects).
	 * 
	 * @param tableName the target table
	 * @param action callback object that specifies the action
	 * @param <T> action type
	 * @return the result object of the callback action, or null
	 */
	<T> T execute(String tableName, TableCallback<T> action);
	
	<T> Pagination<T> execute(String tableName, PageCallback<T> action);

	/**
	 * Scans the target table, using the given family. The content is processed by the given action typically
	 * returning a domain object or collection of domain objects.
	 * 
	 * @param tableName target table
	 * @param family column family
	 * @param action action handling the scanner results
	 * @param <T> action type
	 * @return the result object of the callback action, or null
	 */
	<T> T find(String tableName, String family, final ResultsExtractor<T> action);
	
	
	/**
	 * Scans the target table, using the given column family and qualifier. 
	 * The content is processed by the given action typically returning a domain object or collection of domain objects.
	 * 
	 * @param tableName target table
	 * @param family column family
	 * @param qualifier column qualifier
	 * @param action action handling the scanner results
	 * @param <T> action type
	 * @return the result object of the callback action, or null
	 */
	<T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action);

	/**
	 * Scans the target table using the given {@link Scan} object. Suitable for maximum control over the scanning
	 * process.
	 * The content is processed by the given action typically returning a domain object or collection of domain objects.
	 * 
	 * @param tableName target table
	 * @param scan table scanner
	 * @param action action handling the scanner results
	 * @param <T> action type
	 * @return the result object of the callback action, or null
	 */
	<T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action);	

	/**
	 * Scans the target table, using the given column family. 
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 * 
	 * @param tableName target table
	 * @param family column family
	 * @param action row mapper handling the scanner results
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */
	<T> List<T> find(String tableName, String family, final RowMapper<T> action);
	
	<T> Pagination<T> page(String tableName, String family, final RowMapper<T> action,
			long pageNo, long pageSize);


	/**
	 * Scans the target table, using the given column family. 
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 * 
	 * @param tableName target table
	 * @param family column family
	 * @param qualifier column qualifier
	 * @param action row mapper handling the scanner results
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */
	<T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action);
	
	<T> Pagination<T> page(String tableName, String family, String qualifier, final RowMapper<T> action,
			long pageNo, long pageSize);


	/**
	 * Scans the target table using the given {@link Scan} object. Suitable for maximum control over the scanning
	 * process.
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 * 
	 * @param tableName target table
	 * @param scan table scanner
	 * @param action row mapper handling the scanner results
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */
	<T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action);
	
	/**
	 * Scans the target table using the given {@link Scan} object. Suitable for maximum control over the scanning
	 * process.
	 * The content is processed row by row by the given action, returning a list of domain objects.
	 * 
	 * @param tableName target table
	 * @param scan table scanner
	 * @param action row mapper handling the scanner results
	 * @param <T> action type
	 * @return a list of objects mapping the scanned rows
	 */
	<T> Pagination<T> page(String tableName, final Scan scan, final RowMapper<T> action, long pageNo, long pageSize);
	
	/**
	 * Scans the target table using the given {@link Scan} object. Suitable for maximum control over the scanning
	 * process.
	 * The content is processed by the given action typically returning a domain object or collection of domain objects.
	 * 
	 * @param tableName target table
	 * @param scan table scanner
	 * @param action action handling the scanner results
	 * @param <T> action type
	 * @return the result object of the callback action, or null
	 */
	<T> Pagination<T> page(String tableName, final Scan scan, final PageExtractor<T> action, 
			long pageNo, long pageSize);


	/**
	 * Gets an individual row from the given table. The content is mapped by the given action.
	 * 
	 * @param tableName target table
	 * @param rowName row name
	 * @param mapper row mapper
	 * @param <T> mapper type
	 * @return object mapping the target row
	 */
	<T> T get(String tableName, String rowName, final RowMapper<T> mapper);

	/**
	 * Gets an individual row from the given table. The content is mapped by the given action.
	 * 
	 * @param tableName target table
	 * @param rowName row name
	 * @param familyName column family
	 * @param mapper row mapper
	 * @param <T> mapper type
	 * @return object mapping the target row
	 */
	<T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper);

	/**
	 * Gets an individual row from the given table. The content is mapped by the given action.
	 * 
	 * @param tableName target table
	 * @param rowName row name
	 * @param familyName family 
	 * @param qualifier column qualifier
	 * @param mapper row mapper
	 * @param <T> mapper type
	 * @return object mapping the target row
	 */
	<T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper);
}