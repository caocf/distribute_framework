package com.appleframework.oss.boss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.appleframework.exception.ServiceException;
import com.appleframework.orm.hibernate.types.Finder;
import com.appleframework.oss.boss.entity.Department;

@Repository
public class DepartmentDAO extends BaseDAO {

	private static final Log log = LogFactory.getLog(DepartmentDAO.class);
	
	@SuppressWarnings("unchecked")
	public List<Department> findAll() {
		try {
			String hql = "from Department where 1=1 order by parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public Department get(Integer id) {
		try {
			return (Department)hibernateBaseDAO.load(Department.class, id);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> findByParentId(Integer parentId) {
		try {
			String hql = "from Department where parentId = " + parentId + " order by parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> findByPath(String path) {
		try {
			String hql = "from Department where id in (" + path + ") order by grade, parentId, iorder asc ";
			return hibernateBaseDAO.find(hql, null);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	// 自动设置path、grade
	//@Override
	public Integer save(Department department) {
		department.setPath(department.getName());
		department.setGrade(0);
		super.save(department);
		fillDepartment(department);
		super.update(department);
		return department.getId();
	}
	
	// 自动设置path、grade
	public void update(Department department) {
		fillDepartment(department);
		super.update(department);
		List<Department> childrenDepartmentList = getChildrenDepartmentList(department);
		if (childrenDepartmentList != null) {
			for (int i = 0; i < childrenDepartmentList.size(); i++) {
				Department childrenDepartment = childrenDepartmentList.get(i);
				fillDepartment(childrenDepartment);
				super.update(childrenDepartment);
			}
		}
	}
	
	private void fillDepartment(Department department) {
		Department parent = get(department.getParentId());
		if (parent == null) {
			department.setPath(department.getId().toString());
		} else {
			department.setPath(parent.getPath() + Department.PATH_SEPARATOR + department.getId());
		}
		department.setGrade(department.getPath().split(Department.PATH_SEPARATOR).length - 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> getChildrenDepartmentList(Department department) {
		if (department != null) {
			String hql = "from Department where path like :path order by grade asc, iorder asc";
			Map<String, String> param = new HashMap<String, String>();
			param.put("path", department.getPath() + Department.PATH_SEPARATOR + "%");
			return hibernateBaseDAO.find(hql, param);
		} else {
			String hql = "from Department order by grade asc, iorder asc";
			return hibernateBaseDAO.find(hql, null);
		}
	}
	
	/**
	 * 查找所有
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMapListByAll() throws ServiceException {
		String hql = "from Department order by id, iorder asc";
		return (List<Map<String, Object>>) hibernateBaseDAO.getMapListByHql(hql, null).getList();
	}
	
	public int countUserByDepartment(Integer departmentId) {
		String hql = " from User where departPath like '%" + departmentId + "%'";
		return hibernateBaseDAO.countQueryResult(Finder.create(hql), null);
	}
	
}