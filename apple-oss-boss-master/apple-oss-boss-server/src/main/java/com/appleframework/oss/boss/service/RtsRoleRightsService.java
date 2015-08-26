package com.appleframework.oss.boss.service;

import java.util.List;

import com.appleframework.oss.boss.entity.RtsRoleRights;

public interface RtsRoleRightsService {

	public void save(RtsRoleRights rtsRoleRights);
	
	public void delete(RtsRoleRights rtsRoleRights);
	
	public RtsRoleRights get(Integer id);
	
	public boolean isExit(Integer roleId, Integer rightsId);
	
	public List<RtsRoleRights> getListByRoleId(Integer roleId);
}
