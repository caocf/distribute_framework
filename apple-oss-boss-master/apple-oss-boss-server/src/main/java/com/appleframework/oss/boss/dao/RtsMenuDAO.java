package com.appleframework.oss.boss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.appleframework.oss.boss.entity.RtsMenu;

@Repository
public class RtsMenuDAO extends BaseDAO {

	private static final Log log = LogFactory.getLog(RtsMenuDAO.class);
	
	@SuppressWarnings("unchecked")
	public List<RtsMenu> findAll() {
		try {
			String hql = "from RtsMenu where state <> 2 order by parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public RtsMenu get(Integer id) {
		try {
			return (RtsMenu)hibernateBaseDAO.load(RtsMenu.class, id);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RtsMenu> findByState(Integer state) {
		try {
			String hql = "from RtsMenu where state = " + state + " order by parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsMenu> findByParentId(Integer parentId) {
		try {
			String hql = "from RtsMenu where parentId = " + parentId + " order by parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsMenu> findByPath(String path) {
		try {
			String hql = "from RtsMenu where id in (" + path + ") order by grade, parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	// 自动设置path、grade
	//@Override
	public Integer save(RtsMenu menu) {
		menu.setPath(menu.getUrlName());
		menu.setGrade(0);
		super.save(menu);
		fillMenu(menu);
		super.update(menu);
		return menu.getId();
	}
	
	// 自动设置path、grade
	public void update(RtsMenu menu) {
		fillMenu(menu);
		super.update(menu);
		List<RtsMenu> childrenMenuList = getChildrenMenuList(menu);
		if (childrenMenuList != null) {
			for (int i = 0; i < childrenMenuList.size(); i++) {
				RtsMenu childrenMenu = childrenMenuList.get(i);
				fillMenu(childrenMenu);
				super.update(childrenMenu);
			}
		}
	}
	
	private void fillMenu(RtsMenu menu) {
		RtsMenu parent = get(menu.getParentId());
		if (parent == null) {
			menu.setPath(menu.getId().toString());
		} else {
			menu.setPath(parent.getPath() + RtsMenu.PATH_SEPARATOR + menu.getId());
		}
		menu.setGrade(menu.getPath().split(RtsMenu.PATH_SEPARATOR).length - 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<RtsMenu> getChildrenMenuList(RtsMenu menu) {
		if (menu != null) {
			String hql = "from RtsMenu where path like :path order by grade asc, iorder asc";
			Map<String, String> param = new HashMap<String, String>();
			param.put("path", menu.getPath() + RtsMenu.PATH_SEPARATOR + "%");
			return hibernateBaseDAO.find(hql, param);
		} else {
			String hql = "from RtsMenu order by grade asc, iorder asc";
			return hibernateBaseDAO.find(hql, null);
		}
	}
	
	public void updateBaseUrlByPath(Integer id, String baseUrl) {
		if(null == baseUrl || baseUrl.length() == 0) {
			baseUrl = "";
		}
		String hql = "update RtsMenu set baseUrl = '" + baseUrl + "' where path like :path ";
		Map<String, String> param = new HashMap<String, String>();
		param.put("path", "%" + id + "%");
		hibernateBaseDAO.update(hql, param);
	}
}