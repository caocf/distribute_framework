package com.appleframework.oss.boss.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.dao.RtsRoleDAO;
import com.appleframework.oss.boss.service.RoleSearchService;

/**
 * @author xusm
 * 
 */
@Service("roleSearchService")
public class RoleSearchServiceImpl implements RoleSearchService {

	@Resource
	private RtsRoleDAO rtsRoleDAO;

	public List<Map<String, Object>> getMapListByState(Integer status) throws ServiceException {
		return rtsRoleDAO.getMapListByState(status);
	}

}
