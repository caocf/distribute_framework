package com.appleframework.oss.boss.service;

import java.util.List;
import java.util.Map;

import com.appleframework.exception.ServiceException;

public interface RoleSearchService {

	public List<Map<String, Object>> getMapListByState(Integer status) throws ServiceException;
	
}
