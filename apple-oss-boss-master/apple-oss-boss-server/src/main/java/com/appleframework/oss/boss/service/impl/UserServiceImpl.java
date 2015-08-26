package com.appleframework.oss.boss.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appleframework.exception.ServiceException;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.oss.boss.dao.UserDAO;
import com.appleframework.oss.boss.entity.Department;
import com.appleframework.oss.boss.entity.RtsRole;
import com.appleframework.oss.boss.entity.RtsUserRole;
import com.appleframework.oss.boss.entity.User;
import com.appleframework.oss.boss.service.DepartmentService;
import com.appleframework.oss.boss.service.RtsRoleService;
import com.appleframework.oss.boss.service.RtsUserRoleService;
import com.appleframework.oss.boss.service.UserService;
import com.appleframework.oss.boss.util.DES;
import com.appleframework.oss.boss.util.StringUtil;

/**
 * @author xusm
 * 
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private RtsRoleService rtsRoleService;
	
	@Resource
	private RtsUserRoleService rtsUserRoleService;
	
	@Resource
	private DepartmentService departmentService;

	
	@Resource
	private UserDAO userDAO;

	/**
	 * 分页获取信息.
	 * 
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public Pagination getList(Pagination page, Search search) {
		Long pageNo = page.getPageNo();
		Long pageSize = page.getPageSize();
		return userDAO.findPage(pageNo.intValue(), pageSize.intValue(), search);
	}
	
	public User get(Integer id) {
		return userDAO.get(id);
	}
	
	/**
	 * 新添加
	 * 
	 * @param role
	 * @return
	 */
	public void save(User user, String[] roleIds) throws ServiceException {		
		// 添加前查询是否已经存在该用户
		user.setState(1);
		if (userDAO.isExistByUsername(user.getUsername())) {
			throw new ServiceException("用户名已经存在");
		}
		
		Department department = departmentService.get(user.getDepartment().getId());
		user.setDepartPath(department.getPath());

		//生成密码
		//String salt = ResourceKeyGenerator.getRandomNum(4);
		//String pwd = Md5Encrypt.md5(user.getPassword());
		//pwd = Md5Encrypt.md5(pwd + salt);
		//user.setSalt(salt);
		String pwd = DES.encrypt(user.getPassword());
		user.setPassword(pwd);
		if(null != roleIds && roleIds.length > 0)
			user.setRoles(StringUtil.convert(roleIds));
		else
			user.setRoles("");
		userDAO.save(user);
		if (null != roleIds && roleIds.length > 0) {
			for (String roleId : roleIds) {
				RtsRole rtsRole = rtsRoleService.get(new Integer(roleId));
				RtsUserRole rtsUserRole = new RtsUserRole();
				rtsUserRole.setUser(user);
				rtsUserRole.setRtsRole(rtsRole);
				rtsUserRoleService.save(rtsUserRole);
			}
		}
	}
	
	/**
	 * 删除用户
	 * 
	 * @param role
	 * @throws ServiceException
	 */
	public void delete(Integer id) throws ServiceException {
		User user = get(id);
		if(user.getIsadmin() == 1) {
			throw new ServiceException("是管理员用户，不能删除");
		}
		user.setState(2);
		userDAO.update(user);
	}
	
	/**
	 * 修改
	 * 
	 * @param role
	 * @return
	 */
	public void update(User user, String[] roleIds) throws ServiceException {
		
		//新更新角色相关信息
		String[] ignoreArray = { "id", "createTime", "password", "joinip", "salt" };
		User persistent = get(user.getId());

		// 添加前查询是否已经存在该用户
		if (!this.isUniqueByUsername(persistent.getUsername(), user.getUsername())) {
			throw new ServiceException("用户名已经存在");
		}
		
		Department department = departmentService.get(user.getDepartment().getId());
		user.setDepartPath(department.getPath());
		BeanUtils.copyProperties(user, persistent, ignoreArray);
		
		//密码判断
		if(!StringUtils.isEmpty(user.getPassword())) {
			//String salt = ResourceKeyGenerator.getRandomNum(4);
			//String pwd = Md5Encrypt.md5(user.getPassword());
			//pwd = Md5Encrypt.md5(pwd + salt);
			//persistent.setSalt(salt);
			String pwd = DES.encrypt(user.getPassword());
			persistent.setPassword(pwd);
		}
		if(null != roleIds && roleIds.length > 0)
			persistent.setRoles(StringUtil.convert(roleIds));
		else
			persistent.setRoles("");
		userDAO.update(persistent);
		
		//Set<RtsUserRole> rtsUserRoles = persistent.getRtsUserRoles();
		List<RtsUserRole> rtsUserRoleList = rtsUserRoleService.getByUserId(persistent.getId());
		List<String> nowRoleIdList = new ArrayList<String>();
		if(null != roleIds) {
			for (int i = 0; i < roleIds.length; i++) {
				nowRoleIdList.add(roleIds[i]);
			}
		}
		
		for (RtsUserRole rtsUserRole : rtsUserRoleList) {
			String rId = rtsUserRole.getRtsRole().getId().toString();
			if(nowRoleIdList.contains(rId)) {
				nowRoleIdList.remove(rId);
			}
			else {
				rtsUserRoleService.delete(rtsUserRole);
			}
		}
		
		if (nowRoleIdList.size() > 0) {
			for (String roleId : nowRoleIdList) {
				RtsRole rtsRole = rtsRoleService.get(new Integer(roleId));
				RtsUserRole rtsUserRole = new RtsUserRole();
				rtsUserRole.setUser(user);
				rtsUserRole.setRtsRole(rtsRole);
				rtsUserRoleService.save(rtsUserRole);
			}
		}
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByUsername(String oldUsername, String newUsername) {
		if (StringUtils.equalsIgnoreCase(oldUsername, newUsername)) {
			return true;
		} else {
			if (userDAO.isExistByUsername(newUsername)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Transactional(readOnly = true)
	public int countByDepartment(Integer departmentId) {
		return userDAO.countByDepartment(departmentId);
	}
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}
	
	/**
	 * 修改用户状态
	 * 
	 * @param role
	 * @throws ServiceException
	 */
	public void state(Integer id, int state) throws ServiceException {
		User user = get(id);
		user.setState(state);
		userDAO.update(user);
	}
	
	/**
	 * 修改密码
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updatePassword(Integer id, String oldPassword, String newPassword) throws ServiceException {
		User user = get(id);
		String desOldPassword = DES.encrypt(oldPassword);
		if(user.getPassword().equals(desOldPassword)) {
			String pwd = DES.encrypt(newPassword);
			user.setPassword(pwd);
			userDAO.update(user);
		}
		else {
			throw new ServiceException("旧密码错误，请重新输入");
		}
	}
	
	/**
	 * 矫正用户角色信息
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void updateRole(Integer id) throws ServiceException {
		User user = get(id);
		List<RtsUserRole> rtsUserRoleList = rtsUserRoleService.getByUserId(id);
		String roles = "";
		for (int i = 0; i < rtsUserRoleList.size(); i++) {
			RtsUserRole userRole = rtsUserRoleList.get(i);
			roles += userRole.getRtsRole().getId();
			if(i != rtsUserRoleList.size() -1) {
				roles += ",";
			}
		}
		user.setRoles(roles);
		userDAO.update(user);
	}

}
