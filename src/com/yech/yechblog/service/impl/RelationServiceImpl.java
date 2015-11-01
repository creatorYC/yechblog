package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Relation;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.RelationService;
import com.yech.yechblog.util.ValidateUtil;

@Service("relationService")
public class RelationServiceImpl implements RelationService {

	@Resource(name="relationDao")
	private BaseDao<Relation> relationDao;
	
	//�����ϵ
	@Override
	public void saveRelation(Relation model) {
		relationDao.saveEntity(model);
	}

	/**
	 * �ж�relation���Ƿ��������ϵ
	 */
	@Override
	public boolean queryRelation(User user, User other) {
		String hql = "from Relation r where r.self.id = ? and r.other.id = ?";
		List<Relation> list = 
				relationDao.batchFindEntityByHQL(hql, user.getId(),other.getId());
		return ValidateUtil.isValidate(list);
	}

	/**
	 * ��ѯ��ǰ�û����еĹ�ϵ
	 * @return
	 */
	@Override
	public List<Relation> queryAllRelations(User user) {
		String hql = "from Relation r where r.self.id = ?";
		List<Relation> relations = 
				relationDao.batchFindEntityByHQL(hql, user.getId());
		for(Relation relation : relations){
			relation.getOther().getUsername();
			relation.getOther().getImage();
		}
		return relations;
	}

}
