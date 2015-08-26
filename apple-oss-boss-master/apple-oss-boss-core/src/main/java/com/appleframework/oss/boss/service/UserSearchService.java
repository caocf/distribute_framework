package com.appleframework.oss.boss.service;

import java.util.List;
import java.util.Map;

import com.appleframework.exception.ServiceException;


public interface UserSearchService {

	public List<Map<String, Object>> getMapListByState(Integer state, String keyword) throws ServiceException;
	
	public List<Map<String, Object>> getMapListByDepartment(Integer departmentId, String keyword) throws ServiceException;
	
	public List<Map<String, Object>> getMapListByRole(Integer roleId, String keyword) throws ServiceException;
	
}
