package com.appleframework.oss.boss.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.oss.boss.dao.UserDAO;
import com.appleframework.oss.boss.service.UserSearchService;

/**
 * @author xusm
 * 
 */
@Service("userSearchService")
public class UserSearchServiceImpl implements UserSearchService {

	@Resource
	private UserDAO userDAO;

	public List<Map<String, Object>> getMapListByState(Integer status, String keyword) {
		return userDAO.getByStatus(status, keyword);
	}
	
	public List<Map<String, Object>> getMapListByDepartment(Integer departmentId, String keyword) {
		return userDAO.getByDepartment(departmentId, keyword);
	}
	
	public List<Map<String, Object>> getMapListByRole(Integer roleId, String keyword) {
		return userDAO.getByRole(roleId, keyword);
	}

}
