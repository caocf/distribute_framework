package com.appleframework.oss.boss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.oss.boss.entity.RtsRights;

@Repository
public class RtsRightsDAO extends BaseDAO {
	
	public RtsRights get(Integer id) {
		return (RtsRights)hibernateBaseDAO.load(RtsRights.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsRights> getMenuByUsername(String username) {
		String hql = "SELECT mn FROM RtsMenu mn, User AS u, RtsUserRole AS ur, ";
		hql += " RtsRoleRights AS rr where u.username = '" + username + "' AND ur.user = u AND ";
		hql += " ur.rtsRole = rr.rtsRole AND mn.id = rr.rtsRights";
		return hibernateBaseDAO.find(hql, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsRights> getMenuById(Integer id) {
		String hql = "SELECT mn FROM RtsMenu mn, RtsUserRole AS ur, RtsRoleRights AS rr ";
		hql += "where ur.user.id = " + id + " ";
		hql += "AND ur.rtsRole = rr.rtsRole AND mn.id = rr.rtsRights";
		return hibernateBaseDAO.find(hql, null);
	}

}