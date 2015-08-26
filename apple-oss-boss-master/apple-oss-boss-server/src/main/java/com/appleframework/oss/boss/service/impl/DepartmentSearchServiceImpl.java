package com.appleframework.oss.boss.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.dao.DepartmentDAO;
import com.appleframework.oss.boss.service.DepartmentSearchService;

/**
 * @author xusm
 * 
 */
@Service("departmentSearchService")
public class DepartmentSearchServiceImpl implements DepartmentSearchService {

	@Resource
	private DepartmentDAO departmentDAO;

	public List<Map<String, Object>> getMapListByAll() throws ServiceException {
		return departmentDAO.getMapListByAll();
	}

}
