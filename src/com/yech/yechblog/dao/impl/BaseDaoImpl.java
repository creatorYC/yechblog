package com.yech.yechblog.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import com.yech.yechblog.dao.BaseDao;

/**
 * ����� Dao ʵ���࣬���ڼ̳�
 * @author Administrator
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory sessionFactory;
	
	/*
	 * ע: class UserDao extends BaseDaoIml<User>{}
	 * 		���²�������Ϊ�˵õ� BaseDaoIml<User> �е� User
	 */
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		//�õ����ͻ�����
		ParameterizedType type =
				(ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	@Override
	public void saveEntity(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public void updateEntity(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public void deleteEntity(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}

	/**
	 * ���� HQL ��������������
	 */
	@Override
	public void batchUpdateEntityByHQL(String hql, Object... objects) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
	}

	//ִ��ԭ���� sql ���
	@Override
	public void executeSQL(String sql, Object... objects) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T loadEntity(Integer id) {
		return (T) sessionFactory.getCurrentSession().load(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getEntity(Integer id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> batchFindEntityByHQL(String hql, Object... objects) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeSQLQuery(Class clazz, String sql, Object... objects) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		//���ʵ����
		if(clazz != null){
			query.addEntity(clazz);
		}
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}
}
