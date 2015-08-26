package com.appleframework.oss.boss.id;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.type.Type;


/**
 * @author xusm
 * 
 */
public class IDGenerator implements IdentifierGenerator, Configurable {
	
	private static final Log logger = LogFactory.getLog(IDGenerator.class);
	private long next;
	private String selectsql;
	private String wheresql;
	private Class<?> returnClass;
	private String column;
	private String table;
	
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		if (selectsql != null) {
			this.getNext(session.connection(), object);
		}
		return createId(next++, returnClass);
	}

	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		table = params.getProperty("table");
		if (table == null)
			table = params.getProperty(PersistentIdentifierGenerator.TABLE);
		column = params.getProperty("column");
		if (column == null)
			column = params.getProperty(PersistentIdentifierGenerator.PK);
		String schema = params.getProperty(PersistentIdentifierGenerator.SCHEMA);
		selectsql = "select max(" + column + ") from " + (schema == null ? table : schema + "." + table);
		returnClass = type.getReturnedClass();
	}
	
	private void getNext(Connection conn, Object object) throws HibernateException {
		try {
			//RtsMenu menu = (RtsMenu) object;
			int sta = 1000;
			wheresql = selectsql + " where " + column + " >=" + sta;
			PreparedStatement st = conn.prepareStatement(wheresql);
			ResultSet rs = st.executeQuery();
			try {
				if (rs.next()) {
					next = rs.getInt(1) + 1;
					if (rs.wasNull())
						next = sta;
				} else {
					next = sta;
				}
				wheresql = null;
				logger.debug("first free langid: " + next);
			} catch (SQLException e) {
				logger.error(e);
				throw new HibernateException(e);
			} finally {
				rs.close();
			}
		} catch (Exception e) {
			logger.error(e);
			throw new HibernateException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e);
				throw new HibernateException(e);
			}
		}

	}

	/**
	 * 根据配置文件定义的数据类型进行类型转换
	 * 
	 * @param value
	 * @param clazz
	 * @return
	 * @throws IdentifierGenerationException
	 */
	private Serializable createId(long value, Class<?> clazz) throws IdentifierGenerationException {
		if (clazz == Long.class) {
			return new Long(value);
		} else if (clazz == Integer.class) {
			return new Integer((int) value);
		} else if (clazz == Short.class) {
			return new Short((short) value);
		} else {
			throw new IdentifierGenerationException("this id generator generates long, integer, short");
		}
	}
}
