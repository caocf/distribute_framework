package com.appleframework.data.core.page;

/**
 * 分页接口
 * 
 * @author xusm
 * 
 */
public interface Paginable {
	
	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public long getTotalCount();

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public long getTotalPage();

	/**
	 * 每页记录数
	 * 
	 * @return
	 */
	public long getPageSize();

	/**
	 * 当前页号
	 * 
	 * @return
	 */
	public long getPageNo();

	/**
	 * 是否第一页
	 * 
	 * @return
	 */
	public boolean isFirstPage();

	/**
	 * 是否最后一页
	 * 
	 * @return
	 */
	public boolean isLastPage();

	/**
	 * 返回下页的页号
	 */
	public long getNextPage();

	/**
	 * 返回上页的页号
	 */
	public long getPrePage();

}
