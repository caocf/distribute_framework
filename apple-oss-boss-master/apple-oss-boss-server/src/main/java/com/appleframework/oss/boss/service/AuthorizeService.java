package com.appleframework.oss.boss.service;

import java.util.Set;

import com.appleframework.oss.boss.entity.RtsRights;

public interface AuthorizeService {
	
	public Set<RtsRights> getAuthResource(String username);
	
	public Set<RtsRights> getAuthResource(Integer id);

}
