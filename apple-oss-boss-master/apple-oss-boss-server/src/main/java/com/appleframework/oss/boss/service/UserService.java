package com.appleframework.oss.boss.service;

import com.appleframework.exception.ServiceException;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.oss.boss.entity.User;

public interface UserService {

	public Pagination getList(Pagination page, Search search);
	
	public void save(User user, String[] roleIds) throws ServiceException;
	
	/**
	 * 修改
	 * 
	 * @param role
	 * @return
	 */
	public void update(User user, String[] roleIds) throws ServiceException;
	
	public User get(Integer id);
	
	public int countByDepartment(Integer departmentId);
	
	/**
	 * 删除用户
	 * 
	 * @param role
	 * @throws ServiceException
	 */
	public void delete(Integer id) throws ServiceException;
	
	public boolean isUniqueByUsername(String oldUsername, String newUsername);
	
	public User getByUsername(String username);
	
	public void state(Integer id, int state) throws ServiceException;
	
	public void updatePassword(Integer id, String oldPassword, String newPassword) throws ServiceException;
	
	public void updateRole(Integer id) throws ServiceException;
}
