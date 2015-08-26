package com.appleframework.oss.boss.service.impl;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.oss.boss.dao.RtsRightsDAO;
import com.appleframework.oss.boss.entity.RtsRights;
import com.appleframework.oss.boss.service.AuthorizeService;

/**
 * @author xusm
 * 
 */
@Service("authorizeService")
public class AuthorizeServiceImpl implements AuthorizeService {

	@Resource
	private RtsRightsDAO rtsRightsDAO;

	// 标签里面调用
	public Set<RtsRights> getAuthResource(String username) {
		Set<RtsRights> rtsSet = new HashSet<RtsRights>();
		List<RtsRights> rtList = rtsRightsDAO.getMenuByUsername(username);
		for (RtsRights rt : rtList) {
			rtsSet.add(rt);
		}
		return rtsSet;
	}

	// 标签里面调用
	public Set<RtsRights> getAuthResource(Integer id) {
		Set<RtsRights> rtsSet = new HashSet<RtsRights>();
		List<RtsRights> rtList = rtsRightsDAO.getMenuById(id);
		for (RtsRights rt : rtList) {
			rtsSet.add(rt);
		}
		return rtsSet;
	}

}
