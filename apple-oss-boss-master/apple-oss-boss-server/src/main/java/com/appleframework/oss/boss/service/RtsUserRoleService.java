package com.appleframework.oss.boss.service;

import java.util.List;

import com.appleframework.oss.boss.entity.RtsUserRole;

public interface RtsUserRoleService {

	public void save(RtsUserRole rtsUserRole);
	
	public void saveOrUpdate(RtsUserRole rtsUserRole);
	
	public void delete(RtsUserRole rtsUserRole);
	
	public void delete(Integer id);
	
	public RtsUserRole get(Integer id);
	
	public List<RtsUserRole> getByUserId(Integer userId);
	
	public List<RtsUserRole> getByRoleId(Integer roleId);
}
