package org.ext.dao;


import java.io.Serializable;
import java.util.List;

import org.ext.util.Pager;

public interface Idao<T, PK extends Serializable> {
	public void save(T entity);
	// 删除数据
	public void delete(Class<T> entityClass, PK... pks);

	// 修改数据
	public void merge(T entity);

	// 根据ID查询
	public T findById(Class<T> entityClass,PK pk);

	// 查询全部
	public List<T> findAll(String xql);
	
	//分页操作(不带条件�?带一个条�?多个条件)
	public Pager<T> findByPage(int pageNo,int pageSize,String xql);
	public Pager<T> findByPage(int pageNo,int pageSize,Object key,String xql);
	public Pager<T> findByPage(int pageNo,int pageSize,Object[] keys,String xql);
	
	public List<T> findAllByKeys(Class<T> entityClass, String xql, Object[] keys);
    
	public T findByKeys(Class<T> entityClass, String xql, Object[] keys);
}
