package com.appleframework.oss.boss.service;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.entity.RtsRights;

public interface RtsRightsService {
	
	public RtsRights get(Integer id) throws ServiceException;
	
}
