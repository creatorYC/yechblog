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
	 * 查找当前用户所有的收藏
	 * @return
	 */
	@Override
	public List<Collection> findMyCollections(User user) {
		String hql = "from Collection c where c.deleted = 0 and c.self.id = ?";
		List<Collection> collections = 
				collectionDao.batchFindEntityByHQL(hql, user.getId());
		for(Collection collection : collections){
			collection.getBlog().getTitle();
			collection.getBlog().getId();
		}
		return collections;
	}

	/**
	 * 查询是否有id为bid的collection
	 * @return
	 */
	@Override
	public boolean queryBlogInCollection(User user,Integer bid) {
		String hql = "from Collection c where c.blog.id = ? and c.self.id = ? and c.deleted = 0";
		List<Collection> list = collectionDao.batchFindEntityByHQL(hql, bid,user.getId());
		return ValidateUtil.isValidate(list);
	}
	
	/**
	 * 移除收藏的博客(物理删除)
	 */
	@Override
	public void removeBlogFromCollections(User user,Integer bid) {
		String hql = 
				"delete from Collection c where c.self.id = ? and c.id = ?";
		collectionDao.batchUpdateEntityByHQL(hql, user.getId(),bid);
	}

	
}
