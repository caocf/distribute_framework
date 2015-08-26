package com.appleframework.oss.boss.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.appleframework.core.utils.ObjectUtility;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.orm.hibernate.types.Finder;
import com.appleframework.oss.boss.entity.User;

@Repository
public class UserDAO extends BaseDAO {

	/**
	 * 分页获取信息.
	 * 
	 */
	public Pagination findPage(Integer pageNo, Integer pageSize, Search search) {
		StringBuilder hqlBuilder = new StringBuilder();
		String keyword = search.getKeyword();
		String orderField = search.getOrderField();
		String orderDirection = search.getOrderDirection();

		if (StringUtils.isBlank(orderField)) {
			orderField = "updateTime";
		}
		if (StringUtils.isBlank(orderDirection)) {
			orderDirection = "desc";
		}
		hqlBuilder.append("from User where state <> 2 ");

		if (!StringUtils.isBlank(keyword)) {
			hqlBuilder.append("and (username like '%" + keyword.trim() + "%' ");
			hqlBuilder.append("or realname like '%" + keyword.trim() + "%' )");
		}
		hqlBuilder.append("order by " + orderField + " " + orderDirection + ", id desc");
		return hibernateBaseDAO.find(Finder.create(hqlBuilder.toString()), pageNo, pageSize);
	}

	public boolean isExistByUsername(String username) {
		String hql = " from User where state <> 2 and username = '" + username + "'";
		if (hibernateBaseDAO.countQueryResult(Finder.create(hql), null) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public User getByUsername(String username) {
		String hql = " from User where state <> 2 and username = '" + username + "'";
		return (User) hibernateBaseDAO.findOne(hql, null);
	}

	public User get(Integer id) {
		return (User) hibernateBaseDAO.load(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getByStatus(Integer status, String keyword) {
		String hql = "select id as id,username as username,realname as realname,state as state,isadmin as isadmin," +
				"mobile as mobile,email as email, roles as roles,departPath as departPath from User where 1=1 ";
		if(null != status)
			hql += "and state =" + status + " ";
		if(ObjectUtility.isNotEmpty(keyword))
			hql += "and (username like '%" + keyword + "%' or realname like '%" + keyword + "%')  ";
		hql += "order by id asc ";
		return (List<Map<String, Object>>)hibernateBaseDAO.getMapListByHql(hql, null).getList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getByDepartment(Integer departmentId, String keyword) {
		String hql = "select id as id,username as username,realname as realname,state as state,isadmin as isadmin," +
				"mobile as mobile,email as email, roles as roles,departPath as departPath from User where departPath like '%" + departmentId + "%' ";
		if(ObjectUtility.isNotEmpty(keyword))
			hql += "and (username like '%" + keyword + "%' or realname like '%" + keyword + "%')  ";
		hql += "order by id asc ";
		return (List<Map<String, Object>>)hibernateBaseDAO.getMapListByHql(hql, null).getList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getByRole(Integer roleId, String keyword) {
		String hql = "select id as id,username as username,realname as realname,state as state,isadmin as isadmin," +
				"mobile as mobile,email as email, roles as roles,departPath as departPath from User where roles like '%" + roleId + "%' ";
		if(ObjectUtility.isNotEmpty(keyword))
			hql += "and (username like '%" + keyword + "%' or realname like '%" + keyword + "%')  ";
		
		hql += "order by id asc ";
		return (List<Map<String, Object>>)hibernateBaseDAO.getMapListByHql(hql, null).getList();
	}
	
	public int countByDepartment(Integer departmentId) {
		String hql = " from User where departPath like '%" + departmentId + "%'";
		return hibernateBaseDAO.countQueryResult(Finder.create(hql), null);
	}
}