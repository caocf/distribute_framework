package com.appleframework.oss.boss.vo;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;


public class BaseBO {

	protected String id;
	protected String[] ids;

	private Pagination page = new Pagination();
	private Search search;

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

}
