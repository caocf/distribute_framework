package com.appleframework.oss.boss.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.dao.DepartmentDAO;
import com.appleframework.oss.boss.entity.Department;
import com.appleframework.oss.boss.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	private DepartmentDAO departmentDAO;
		
	/**
	 * 查找单个部门信息
	 */
	public Department get(Integer id) throws ServiceException {
		return departmentDAO.get(id);
	}
	
	/**
	 * 查找所有子菜單
	 */
	public List <Department> getChildrenDepartmentList(Integer parentId){
		List <Department> departmentList = departmentDAO.findByParentId( parentId );
		return departmentList;
	}
	
	/**
	 * 根据路径查询路径菜單
	 */
	public List <Department> getDepartmentListByPath(String path){
		List <Department> departmentList = departmentDAO.findByPath(path);
		return departmentList;
	}

	/**
	 * 查找所有根部门
	 */
	public List<Department> findRootDepartmentList() throws ServiceException {
		List<Department> departmentList = this.getChildrenDepartmentList(0);
		return departmentList;
	}
	
	/**
	 * 查找所有部门
	 */
	public List<Department> findAll() throws ServiceException {
		return departmentDAO.findAll();
	}
	
	
	/**
	 *從所有菜單中找子菜單
	 */
	public List<Department> findChildList(Department department, List<Department> allDepartmentList){
		List<Department> tmpList = new LinkedList<Department>();
		for( Department  childDepartment : allDepartmentList){
			if( childDepartment.getParentId().intValue() == department.getId().intValue() ){
				tmpList.add( childDepartment );
			}
		}
		return tmpList;
	}
	
	/**
	 * 添加department
	 * @param department
	 * @throws ServiceException
	 */
	public void add(Department department) throws ServiceException {
		try {
			List<Department> existDepartment = this.getChildrenDepartmentList(department.getParentId());
			department.setIorder(existDepartment.size() + 1);
			departmentDAO.save(department);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("DB Error");
		}
	}
	
	/**
	 * 更新department
	 * @param department
	 * @throws ServiceException
	 */
	public void update(Department department) throws ServiceException {
		try {
			String[] ignoreArray = { "id", "createTime" };
			Department persistent = get(department.getId());
			BeanUtils.copyProperties(department, persistent, ignoreArray);
			departmentDAO.update(persistent);
		} catch (Exception e) {
			throw new ServiceException("DB Error");
		}
	}
	
	/**
	 * 删除department
	 * @param department
	 * @throws ServiceException
	 */
	public void delete(Department department) throws ServiceException {
		List<Department> childList = this.getChildrenDepartmentList(department.getId());
		if (childList.size() > 0) {
			throw new ServiceException("有子部门，不能刪除");
		}
		int count = departmentDAO.countUserByDepartment(department.getId());
		if (count > 0) {
			throw new ServiceException("有用户使用该部门资源，不能刪除，请先去掉该用户的该资源");
		}
		try {
			departmentDAO.delete(department);
		} catch (Exception e) {
			throw new ServiceException("DB Error");
		}
	}

}
