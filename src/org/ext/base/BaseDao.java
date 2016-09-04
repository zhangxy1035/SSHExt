package org.ext.base;



import java.io.Serializable;
import java.util.List;

import org.ext.util.Pager;

public interface BaseDao<T, PK extends Serializable> {
	// 添加
	public void save(T entity);

	// 删除数据
	public void delete(PK... pks);

	// 修改数据
	public void merge(T entity);

	// 根据ID查询
	public T findById(PK pk);

	// 查询全部
	public List<T> findAll(String xql);

	public Pager<T> findByPage(int pageNo, int pageSize, String xql);

	public Pager<T> findByPage(int pageNo, int pageSize, Object key, String xql);

	public Pager<T> findByPage(int pageNo, int pageSize, Object[] keys,
			String xql);
	
	public List<T> findAllByKeys(String xql, Object[] keys);
	
	public T findByKeys(String xql, Object[] keys);
}
