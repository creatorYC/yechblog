package com.yech.yechblog.dao;

import java.util.List;

/**
 * BaseDao
 * @author Administrator
 */
public interface BaseDao<T> {

	// д����
	public void saveEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void batchUpdateEntityByHQL(String hql, Object... objects);
	// ִ��ԭ���� sql ���
	public void executeSQL(String sql, Object... objects);
	// ������
	public T loadEntity(Integer id);
	public T getEntity(Integer id);
	public List<T> batchFindEntityByHQL(String hql, Object... objects);
	//ִ��ԭ����sql��ѯ(clazz ָ���Ƿ��װ��ʵ��)
	@SuppressWarnings("rawtypes")
	public List executeSQLQuery(Class clazz,String sql,Object ...objects);
}
