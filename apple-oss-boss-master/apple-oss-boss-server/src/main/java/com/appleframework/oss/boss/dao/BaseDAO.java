package com.appleframework.oss.boss.dao;

import javax.annotation.Resource;

import com.appleframework.orm.hibernate.client.HibernateBaseDAO;

public class BaseDAO {
	
	@Resource
	protected  HibernateBaseDAO hibernateBaseDAO;
	
	public Integer save(Object object) {
		return (Integer)hibernateBaseDAO.save(object);
	}
	
	public void update(Object object) {
		hibernateBaseDAO.update(object);
	}
	
	public void saveOrUpdate(Object object) {
		hibernateBaseDAO.saveOrUpdate(object);
	}
	
	public void delete(Object object) {
		hibernateBaseDAO.delete(object);
	}

}
