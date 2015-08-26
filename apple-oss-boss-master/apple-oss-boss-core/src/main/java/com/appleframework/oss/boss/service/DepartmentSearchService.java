package com.appleframework.oss.boss.service;

import java.util.List;
import java.util.Map;

import com.appleframework.exception.ServiceException;

public interface DepartmentSearchService {

	public List<Map<String, Object>> getMapListByAll() throws ServiceException;
	
}
