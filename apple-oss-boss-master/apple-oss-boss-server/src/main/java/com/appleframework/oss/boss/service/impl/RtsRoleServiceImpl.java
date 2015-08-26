package com.appleframework.oss.boss.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appleframework.exception.ServiceException;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.oss.boss.dao.RtsRoleDAO;
import com.appleframework.oss.boss.entity.Department;
import com.appleframework.oss.boss.entity.RtsFunctions;
import com.appleframework.oss.boss.entity.RtsMenu;
import com.appleframework.oss.boss.entity.RtsRights;
import com.appleframework.oss.boss.entity.RtsRole;
import com.appleframework.oss.boss.entity.RtsRoleRights;
import com.appleframework.oss.boss.service.DepartmentService;
import com.appleframework.oss.boss.service.RtsMenuService;
import com.appleframework.oss.boss.service.RtsRightsService;
import com.appleframework.oss.boss.service.RtsRoleRightsService;
import com.appleframework.oss.boss.service.RtsRoleService;


/**
 * @author xusm
 * 
 */

@Service("rtsRoleService")
public class RtsRoleServiceImpl implements RtsRoleService {

	private static final Log logger = LogFactory.getLog(RtsRoleServiceImpl.class);

	@Resource
	private RtsRightsService rtsRightsService;
	
	@Resource
	private RtsMenuService rtsMenuService;
	
	@Resource
	private RtsRoleRightsService rtsRoleRightsService;
	
	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private RtsRoleDAO rtsRoleDAO;

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
		return rtsRoleDAO.findPage(pageNo.intValue(), pageSize.intValue(), search);
	}

	/**
	 * 查找所有
	 * 
	 * @return
	 */
	public List<RtsRole> findAll() throws ServiceException {
		return rtsRoleDAO.findAllByState(1);
	}

	/**
	 * 新添加
	 * 
	 * @param role
	 * @return
	 */
	public void add(RtsRole role, String[] rightsIds) throws ServiceException {
		role.setState(1);
		try {
			// 添加前查询是否已经存在该角色
			if(rtsRoleDAO.isExistByName(role.getRoleName())) {
				throw new ServiceException("角色名称已经存在");
			}
			Department department = departmentService.get(role.getDepartment().getId());
			role.setDepartPath(department.getPath());

			rtsRoleDAO.save(role);
			if (null != rightsIds && rightsIds.length > 0) {

				for (String rtsId : rightsIds) {
					System.out.println("---------->>>rtsId=" + rtsId);
					if(rtsId.equals("0")) {
						continue;
					}
					RtsRoleRights roleRights = new RtsRoleRights();
					RtsRights rights = rtsRightsService.get(new Integer(rtsId));
					roleRights.setRtsRole(role);
					roleRights.setRtsRights(rights);
					rtsRoleRightsService.save(roleRights);
					try {
						addMenuParent(role, rights);
					} catch (Exception e) {
						throw new ServiceException("系统错误，请稍后再试");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("DB ERROR");
		}
	}
	
	public void addMenuParent(RtsRole role, RtsRights rights) {
	try {
		rights = rtsRightsService.get(rights.getId());
		if (rights instanceof RtsMenu) {
			RtsMenu menu = (RtsMenu) rights;
			RtsMenu parentMenu = rtsMenuService.get(menu.getParentId());
			//System.out.print("parent" + parentMenu.getUrlName());			
			if(rtsRoleRightsService.isExit(role.getId(), parentMenu.getId())) {
				if(menu.getParentId() != 0) {
					addMenuParent(role, parentMenu);
				}
			}
			else {
				RtsRoleRights newroleRights = new RtsRoleRights();

				newroleRights.setRtsRole(role);
				newroleRights.setRtsRights(parentMenu);
				rtsRoleRightsService.save(newroleRights);
				
				if(menu.getParentId() != 0) {
					addMenuParent(role, parentMenu);
				}
			}
		} else {
		}
	} catch (Exception e) {
		logger.error(e);
	}
}

	/**
	 * 根据id查找role
	 * 
	 * @param roleId
	 * @return
	 */
	public RtsRole get(Integer id) throws ServiceException {
		return rtsRoleDAO.get(id);
	}

	/**
	 * 更新ROLE
	 * 
	 * @param role
	 * @throws ServiceException
	 */
	@Override
	public void update(RtsRole role, String[] rigthsIds) throws ServiceException {
        try{
			Department department = departmentService.get(role.getDepartment().getId());
			role.setDepartPath(department.getPath());
        	
        	//新更新角色相关信息
			String[] ignoreArray = { "id", "createTime", "state" };
			RtsRole persistent = get(role.getId());
			BeanUtils.copyProperties(role, persistent, ignoreArray);
			rtsRoleDAO.update(persistent);

			
			//更新角色相关的权限信息
			Set<RtsRoleRights> roleRights = role.getRtsRoleRightses();
			List<RtsRoleRights> roleRightsList = rtsRoleRightsService.getListByRoleId(persistent.getId());
			roleRights.addAll(roleRightsList);

			List<RtsRoleRights> delList = new LinkedList<RtsRoleRights>();

			List<String> rightsIdList = null;
			if (null != rigthsIds && rigthsIds.length > 0)
				rightsIdList = Arrays.asList(rigthsIds);
			else
				rightsIdList = new ArrayList<String>();
			List<String> reserveList = new LinkedList<String>();
			// 找出需要刪除的 和 新增加的
			for (RtsRoleRights roleRight : roleRights) {
				String rightsId = "" + roleRight.getRtsRights().getId();

				if (!rightsIdList.contains(rightsId)) {
					delList.add(roleRight);
				} else {
					reserveList.add(rightsId);
				}

			}
            //刪除
			for( RtsRoleRights roleRight : delList ){
				rtsRoleRightsService.delete(roleRight);
				//如果是菜單把上級菜單先刪除，由後面來添加
				delMenuParent(roleRights,roleRight.getRtsRights() );
			}
			//新增加
			for (String rightsId : rightsIdList) {
				if (rightsId == null || rightsId.length() < 1) {
					continue;
				}
				if (reserveList.contains(rightsId)) {
					RtsRights rights = rtsRightsService.get(new Integer(rightsId));
					try {
						addMenuParent(persistent, rights);
					} catch (Exception e) {

					}
					continue;
				}
				RtsRoleRights rlRt = new RtsRoleRights();
				RtsRights rights = rtsRightsService.get(new Integer(rightsId));
				rlRt.setRtsRole(persistent);
				rlRt.setRtsRights(rights);
				rtsRoleRightsService.save(rlRt);
				// 新增加的，需要添加父菜單
				try {
					addMenuParent(persistent, rights);
				} catch (Exception e) {

				}
			}
			
			//恢復刪除的top層菜單			
			for (String rightId : reserveList) {
				RtsRights rights = rtsRightsService.get(new Integer(rightId));
				addMenuParent(role, rights);
			}
		
			
		}catch( Exception e){
			e.printStackTrace();
			throw new ServiceException("DB ERROR");
		}
	}

	private void delMenuParent(Set<RtsRoleRights> roleRights, RtsRights rights) {
		try {
			rights = rtsMenuService.get(rights.getId());
			if (rights instanceof RtsMenu) {
				RtsMenu menu = (RtsMenu) rights;
				for (RtsRoleRights roleRight : roleRights) {
					String rightsId = "" + roleRight.getRtsRights().getId();
					if (rightsId.equalsIgnoreCase(menu.getParentId().toString())) {
						rtsRoleRightsService.delete(roleRight);
						System.out.println("---------del a parent menu role right");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除ROLE
	 * 
	 * @param role
	 * @throws ServiceException
	 */
	public void delete(Integer id) throws ServiceException {
		try {
			RtsRole role = get(id);
			if (role.getRtsUserRoles().size() > 0) {
				throw new ServiceException("有账号在使用此角色，不能删除");
			}

			Set<RtsRoleRights> roleRights = role.getRtsRoleRightses();
			for (RtsRoleRights roleRight : roleRights) {
				rtsRoleRightsService.delete(roleRight);
			}
			rtsRoleDAO.delete(role);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 查找某角色拥有的菜单资源
	 * 
	 * @return
	 */
	public List<RtsMenu> findHaveMenuRights(Integer roleId) throws ServiceException {
		return rtsRoleDAO.findHaveMenuRights(roleId);
	}

	/**
	 * 查找某角色拥有的方法资源
	 * 
	 * @return
	 */
	public List<RtsFunctions> findHaveFunctionRights(Integer roleId) throws ServiceException {
		return rtsRoleDAO.findHaveFunctionRights(roleId);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByName(String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(oldName, newName)) {
			return true;
		} else {
			if (rtsRoleDAO.isExistByName(newName)) {
				return false;
			} else {
				return true;
			}
		}
	}

}
