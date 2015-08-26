package com.appleframework.oss.boss.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.dao.RtsMenuDAO;
import com.appleframework.oss.boss.entity.RtsMenu;
import com.appleframework.oss.boss.entity.RtsRoleRights;
import com.appleframework.oss.boss.service.RtsMenuService;

@Service("menuService")
public class RtsMenuServiceImpl implements RtsMenuService {

	@Resource
	private RtsMenuDAO rtsMenuDAO;
	
	/**
	 * 查找所有
	 */
	@Cacheable(value = "menuCache", key = "'RtsMenuServiceImpl.listMenus'")
	public List <RtsMenu> listMenus() throws ServiceException {
		List <RtsMenu> allMenuList = rtsMenuDAO.findByState(1);
		return allMenuList;
	}
	
	/**
	 * 查找单个菜单信息
	 */
	public RtsMenu get(Integer id) throws ServiceException {
		return rtsMenuDAO.get(id);
	}
	
	/**
	 * 查找所有子菜單
	 */
	@Cacheable(value = "menuCache", key = "'RtsMenuServiceImpl.listMenus.' + #parentId")
	public List <RtsMenu> listMenus(Integer parentId){
		List <RtsMenu> menuList = rtsMenuDAO.findByParentId( parentId );
		List <RtsMenu> rsMenuList = new LinkedList<RtsMenu>();
		for( RtsMenu menu :menuList ){
			if( menu.getState() == 1 ){
				rsMenuList.add( menu );
			}
		}
		return rsMenuList;
	}
	
	/**
	 * 根据路径查询路径菜單
	 */
	@Cacheable(value = "menuCache", key = "'RtsMenuServiceImpl.listMenus.' + #path")
	public List <RtsMenu> listMenus(String path){
		List <RtsMenu> menuList = rtsMenuDAO.findByPath(path);
		List <RtsMenu> rsMenuList = new LinkedList<RtsMenu>();
		for( RtsMenu menu :menuList ){
			if( menu.getState() == 1 ){
				rsMenuList.add( menu );
			}
		}
		return rsMenuList;
	}

	/**
	 * 查找所有根菜单
	 */
	@Cacheable(value = "menuCache", key = "'RtsMenuServiceImpl.findRootMenuList'")
	public List<RtsMenu> findRootMenuList() throws ServiceException {
		List<RtsMenu> menuList = this.listMenus(0);
		return menuList;
	}
	
	/**
	 * 查找所有菜单
	 */
	@Cacheable(value = "menuCache", key = "'RtsMenuServiceImpl.findAll'")
	public List<RtsMenu> findAll() throws ServiceException {
		return rtsMenuDAO.findAll();
	}
	
	/**
	 * 查找所有可用菜单
	 */
	@Cacheable(value = "menuCache", key = "'RtsMenuServiceImpl.findAllMenuRights'")
	public List<RtsMenu> findAllMenuRights() throws ServiceException {
		return rtsMenuDAO.findByState(1);
	}
	
	/**
	 *從所有菜單中找子菜單
	 */
	public List<RtsMenu> findChildList(RtsMenu menu, List<RtsMenu> allMenuList){
		List<RtsMenu> tmpList = new LinkedList<RtsMenu>();
		for( RtsMenu  childMenu : allMenuList){
			if( childMenu.getParentId().intValue() == menu.getId().intValue() ){
				tmpList.add( childMenu );
			}
		}
		return tmpList;
	}
	
	/**
	 * 添加menu
	 * @param menu
	 * @throws ServiceException
	 */
	@CacheEvict(value = "menuCache", allEntries = true)
	public void add(RtsMenu menu) throws ServiceException {
		try {
			List<RtsMenu> existMenu = this.listMenus(menu.getParentId());
			menu.setIorder(existMenu.size() + 1);
			menu.setState(1);
			if(null == menu.getOpenStyle()) {
				menu.setOpenStyle(0);
			}
			//menu.setResourceKey(ResourceKeyGenerator.gen(menu.getGrade() + menu.getUrlName() + menu.getUrl()));
			menu.setResouceDescription("菜单资源:[" + menu.getUrlName() + "]");
			rtsMenuDAO.save(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("DB Error");
		}
	}
	
	/**
	 * 更新menu
	 * @param menu
	 * @throws ServiceException
	 */
	@CacheEvict(value = "menuCache", allEntries = true)
	public void update(RtsMenu menu) throws ServiceException {
		try {
			if(null == menu.getOpenStyle()) {
				menu.setOpenStyle(0);
			}
			String[] ignoreArray = { "id", "createTime", "state" };
			RtsMenu persistent = get(menu.getId());
			// 設置新的key
			//menu.setResourceKey(ResourceKeyGenerator.gen(menu.getGrade() + menu.getUrlName() + menu.getUrl()));
			menu.setResouceDescription("菜单资源:[" + menu.getUrlName() + "]");
			BeanUtils.copyProperties(menu, persistent, ignoreArray);
			rtsMenuDAO.update(persistent);
			if(persistent.getParentId() == 0) {
				this.updateBaseUrl(persistent.getId(), persistent.getBaseUrl());
			}
		} catch (Exception e) {
			throw new ServiceException("DB Error");
		}
	}
	
	/**
	 * 删除menu
	 * @param menu
	 * @throws ServiceException
	 */
	@CacheEvict(value = "menuCache", allEntries = true)
	public void delete(RtsMenu menu) throws ServiceException {
		List<RtsMenu> childList = this.listMenus(menu.getId());
		if (childList.size() > 0) {
			throw new ServiceException("有子菜单，不能刪除");
		}
		Set<RtsRoleRights> roleSet = menu.getRtsRoleRightses();
		if (roleSet.size() > 0) {
			throw new ServiceException("有角色使用该菜单资源，不能刪除，请先去掉该角色的该资源");
		}
		try {
			rtsMenuDAO.delete(menu);
		} catch (Exception e) {
			throw new ServiceException("DB Error");
		}
	}

	@CacheEvict(value = "menuCache", allEntries = true)
	private void updateBaseUrl(Integer id, String baseUrl) {
		rtsMenuDAO.updateBaseUrlByPath(id, baseUrl);
	}
}
