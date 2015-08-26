package com.appleframework.oss.boss.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.orm.hibernate.types.Finder;
import com.appleframework.oss.boss.entity.RtsRoleRights;

@Repository
public class RtsRoleRightsDAO extends BaseDAO {

	public boolean isExit(Integer roleId, Integer rightsId) {
		String hql = "from RtsRoleRights where rtsRole.id = " + roleId + " "
				+ "and rtsRights.id = " + rightsId;
		if(hibernateBaseDAO.countQueryResult(Finder.create(hql), null) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public RtsRoleRights get(Integer id) {
		return (RtsRoleRights)hibernateBaseDAO.load(RtsRoleRights.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsRoleRights> getListByRoleId(Integer roleId) {
		String hql = "from RtsRoleRights where rtsRole.id = " + roleId;
		return hibernateBaseDAO.find(hql, null);
	}

}