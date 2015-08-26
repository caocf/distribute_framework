package com.appleframework.oss.boss.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.dao.RtsRightsDAO;
import com.appleframework.oss.boss.entity.RtsRights;
import com.appleframework.oss.boss.service.RtsRightsService;

@Service("rtsRightsService")
public class RtsRightsServiceImpl implements RtsRightsService {

	@Resource
	private RtsRightsDAO rtsRightsDAO;

	public RtsRights get(Integer id) throws ServiceException {
		return rtsRightsDAO.get(id);
	}

}
