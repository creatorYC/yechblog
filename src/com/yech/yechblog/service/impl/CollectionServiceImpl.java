package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Collection;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.CollectionService;
import com.yech.yechblog.util.ValidateUtil;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

	@Resource(name="collectionDao")
	private BaseDao<Collection> collectionDao;
	
	@Override
	public void saveCollection(Collection collection) {
		collectionDao.saveEntity(collection);
	}

	/**
	 * ���ҵ�ǰ�û����е��ղ�
	 * @return
	 */
	@Override
	public List<Collection> findMyCollections(User user) {
		String hql = "from Collection c where c.deleted = 0 "
				+ "and c.self.id = ? order by c.collectTime desc";
		List<Collection> collections = 
				collectionDao.batchFindEntityByHQL(hql, user.getId());
		for(Collection collection : collections){
			collection.getBlog().getTitle();
			collection.getBlog().getId();
		}
		return collections;
	}

	/**
	 * ��ѯ�Ƿ���idΪbid��collection
	 * @return
	 */
	@Override
	public boolean queryBlogInCollection(User user,Integer bid) {
		String hql = "from Collection c where c.blog.id = ? and c.self.id = ? and c.deleted = 0";
		List<Collection> list = collectionDao.batchFindEntityByHQL(hql, bid,user.getId());
		return ValidateUtil.isValidate(list);
	}
	
	/**
	 * �Ƴ��ղصĲ���(����ɾ��)
	 */
	@Override
	public void removeBlogFromCollections(User user,Integer bid) {
		String hql = 
				"delete from Collection c where c.self.id = ? and c.id = ?";
		collectionDao.batchUpdateEntityByHQL(hql, user.getId(),bid);
	}

	/**
	 * ͳ���ղصĲ�������
	 */
	@Override
	public int getMyCollectionCount(User user) {
		String hql = "select count(c.id) from Collection c where c.self.id = ? and c.deleted = 0";
		return  ((Long)(collectionDao.uniqueResult(hql,user.getId()))).intValue();
	}

	/**
	 * ��ѯָ��ҳ�ղصĲ�������
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Collection> queryMyPage(User user, int currentPageIndex,
			int countPerPage) {
		String hql = "SELECT * FROM collection where self = ? and deleted = 0 order by collect_time desc LIMIT ?,?";
		List<Collection> collections = (List<Collection>) collectionDao.listResult(Collection.class,hql,user.getId(),
				(currentPageIndex-1) * countPerPage,countPerPage);
		for(Collection collection : collections){
			collection.getBlog().getTitle();
			collection.getBlog().getId();
			collection.getCollectTime();
		}
		return collections;
	}
}
