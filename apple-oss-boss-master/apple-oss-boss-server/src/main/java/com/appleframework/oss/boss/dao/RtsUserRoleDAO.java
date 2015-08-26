package com.appleframework.oss.boss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.oss.boss.entity.RtsUserRole;

@Repository
public class RtsUserRoleDAO extends BaseDAO {

	public void svae(RtsUserRole rtsUserRole) {
		hibernateBaseDAO.save(rtsUserRole);
	}
	
	public RtsUserRole get(Integer id) {
		return (RtsUserRole)hibernateBaseDAO.load(RtsUserRole.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsUserRole> getListByUserId(Integer userId) {
		String hql = "from RtsUserRole where user.id = " + userId;
		return hibernateBaseDAO.find(hql, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsUserRole> getListByRoleId(Integer roleId) {
		String hql = "from RtsUserRole where rtsRole.id = " + roleId;
		return hibernateBaseDAO.find(hql, null);
	}

}