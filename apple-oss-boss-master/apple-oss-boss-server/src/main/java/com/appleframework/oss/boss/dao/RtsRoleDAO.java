package com.appleframework.oss.boss.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.appleframework.exception.ServiceException;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.orm.hibernate.types.Finder;
import com.appleframework.oss.boss.entity.RtsFunctions;
import com.appleframework.oss.boss.entity.RtsMenu;
import com.appleframework.oss.boss.entity.RtsRole;

@Repository
public class RtsRoleDAO extends BaseDAO {

	/**
	 * 分页获取信息.
	 */
	public Pagination findPage(Integer pageNo, Integer pageSize, Search search) {
		StringBuilder hqlBuilder = new StringBuilder();
		String keyword = search.getKeyword();
		String searchBy = search.getSearchBy();
		String orderField = search.getOrderField();
		String orderDirection = search.getOrderDirection();

		if (StringUtils.isBlank(orderField)) {
			orderField = "id";
		}
		if (StringUtils.isBlank(orderDirection)) {
			orderDirection = "desc";
		}
		hqlBuilder.append("from RtsRole where state <> 2 ");

		if (!StringUtils.isBlank(keyword)) {
			hqlBuilder.append("and " + searchBy + " like '%" + keyword.trim() + "%' ");
		}
		hqlBuilder.append("order by " + orderField + " " + orderDirection);
		return hibernateBaseDAO.find(Finder.create(hqlBuilder.toString()), pageNo, pageSize);
	}
	
	public boolean isExistByName(String name) {
		String hql = " from RtsRole where roleName = '" + name + "'";
	    if(hibernateBaseDAO.countQueryResult(Finder.create(hql), null) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public RtsRole get(Integer id) {
	    return (RtsRole)hibernateBaseDAO.load(RtsRole.class, id);
	}
	
	/**
	 * 查找某角色拥有的菜单资源
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RtsMenu> findHaveMenuRights(Integer roleId) throws ServiceException {
		String hql = "select me from RtsMenu as me where me.state = 1 and me.id in ( "
				+ " select rr.rtsRights.id from RtsRoleRights as rr where rr.rtsRole.id = "
				+ roleId + " " + ") " + "order by me.parentId, me.iorder";
		return hibernateBaseDAO.find(hql, null);

	}
	
	/**
	 * 查找某角色拥有的方法资源
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RtsFunctions> findHaveFunctionRights(Integer roleId) throws ServiceException {
		String hql = "select fun from RtsFunctions as fun where fun.id in ( "
				+ " select rr.rtsRights.id from RtsRoleRights as rr where rr.rtsRole.id = "
				+ roleId + " " + ") " + "order by fun.protectFunction asc";
		return hibernateBaseDAO.find(hql, null);
	}
	
	/**
	 * 查找所有
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RtsRole> findAllByState(Integer state) throws ServiceException {
		String hql = "from RtsRole where state = " + state + "order by id asc";
		return hibernateBaseDAO.find(hql, null);
	}
	
	/**
	 * 查找所有
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMapListByState(Integer state) throws ServiceException {
		String hql = "from RtsRole where state = " + state + "order by id asc";
		return (List<Map<String, Object>>) hibernateBaseDAO.getMapListByHql(hql, null).getList();
	}


}