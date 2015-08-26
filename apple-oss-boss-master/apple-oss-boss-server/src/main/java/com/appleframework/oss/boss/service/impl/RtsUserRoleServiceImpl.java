package com.appleframework.oss.boss.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.appleframework.oss.boss.dao.RtsUserRoleDAO;
import com.appleframework.oss.boss.entity.RtsUserRole;
import com.appleframework.oss.boss.service.RtsUserRoleService;

/**
 * @author xusm
 * 
 */

@Service("rtsUserRoleService")
public class RtsUserRoleServiceImpl implements RtsUserRoleService {

	@Resource
	private RtsUserRoleDAO rtsUserRoleDAO;

	public void saveOrUpdate(RtsUserRole rtsUserRole) {
		rtsUserRoleDAO.saveOrUpdate(rtsUserRole);
	}
	
	public void save(RtsUserRole rtsUserRole) {
		rtsUserRoleDAO.save(rtsUserRole);
	}
	
	public void delete(RtsUserRole rtsUserRole) {
		rtsUserRoleDAO.delete(rtsUserRole);
	}
	
	public void delete(Integer id) {
		RtsUserRole rtsUserRole = get(id);
		rtsUserRoleDAO.delete(rtsUserRole);
	}
	
	public RtsUserRole get(Integer id) {
		return rtsUserRoleDAO.get(id);
	}
	
	public List<RtsUserRole> getByUserId(Integer userId) {
		return rtsUserRoleDAO.getListByUserId(userId);
	}
	
	public List<RtsUserRole> getByRoleId(Integer roleId) {
		return rtsUserRoleDAO.getListByRoleId(roleId);
	}

}
