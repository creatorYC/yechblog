package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Relation;
import com.yech.yechblog.entity.User;

public interface RelationService {

	//�����ϵ
	public void saveRelation(Relation model);

	/**
	 * ��ѯrelation���Ƿ��������ϵ
	 * @return
	 */
	public boolean queryRelation(User user, User other);

	/**
	 * ��ѯ��ǰ�û����еĹ�ϵ
	 * @return
	 */
	public List<Relation> queryAllRelations(User user);

}
