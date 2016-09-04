package org.ext.dao;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ext.util.Pager;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class HibernateDaoImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements Idao<T, PK> {

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void delete(Class<T> entityClass, PK... pks) {
		for (PK pk : pks) {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(entityClass, pk));
		}
	}

	public void merge(T entity) {
		getHibernateTemplate().merge(entity);

	}

	public T findById(Class<T> entityClass, PK pk) {
		return getHibernateTemplate().get(entityClass, pk);
	}

	public List<T> findAll(String xql) {
		return getHibernateTemplate().find(xql);
	}

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Pager<T> findByPage(int pageNo, int pageSize, String xql) {
		return findByPage(pageNo, pageSize, null, xql);
	}

	public Pager<T> findByPage(int pageNo, int pageSize, Object key, String xql) {
		return findByPage(pageNo, pageSize, new Object[] { key }, xql);
	}

	public Pager<T> findByPage(int pageNo, int pageSize, Object[] keys,
			String xql) {
		Pager<T> pager = new Pager<T>();
		int totalNum = getTotalNum(xql, keys);
		List<T> pageList = new ArrayList<T>();
		Query query = getSession().createQuery(xql);
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				query.setParameter(i, keys[i]);
			}
		}
		// Hibernate分页
		query.setFirstResult(pageNo).setMaxResults(pageSize);
		pageList = query.list();
		pager.setPageList(pageList);
		pager.setTotalNum(totalNum);
		return pager;
	}

	private int getTotalNum(String xql, Object[] keys) {
		int totalNum = 0;
		int index = xql.indexOf("from");
		String from = xql.substring(index).trim();
		String hql = "select count(*) " + from;
		Query query = getSession().createQuery(hql);
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				query.setParameter(i, keys[i]);
			}
		}
		totalNum = ((Long) query.uniqueResult()).intValue();
		return totalNum;
	}
	
	public List<T> findAllByKeys(Class<T> entityClass, String xql, Object[] keys) {
		Query query = getSession().createQuery(xql);
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				query.setParameter(i, keys[i]);
			}
		}
		return query.list();
	} 
	
	public T findByKeys(Class<T> entityClass, String xql, Object[] keys) {
		boolean flag = checkByKeys(xql, keys);
		if (flag) {
			Query query = getSession().createQuery(xql);
			if (keys != null && keys.length > 0) {
				for (int i = 0; i < keys.length; i++) {
					query.setParameter(i, keys[i]);
				}
			}
			return (T) query.uniqueResult();
		} else {
			return null;
		}
	}
	
	
	private boolean checkByKeys(String xql, Object[] keys) {
		int index = xql.indexOf("from");
		String from = xql.substring(index).trim();
		String hql = "select count(*)" + from;
		Query query = getSession().createQuery(hql);
		if (keys != null && keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				query.setParameter(i, keys[i]);
			}
		}
		int flag = ((Long) query.uniqueResult()).intValue();
		return flag > 0;
	}

     
}
