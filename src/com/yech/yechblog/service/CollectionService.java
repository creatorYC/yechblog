package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Collection;
import com.yech.yechblog.entity.User;

public interface CollectionService {

	public void saveCollection(Collection collection);

	/**
	 * ���ҵ�ǰ�û����е��ղ�
	 * @return
	 */
	public List<Collection> findMyCollections(User user);

	/**
	 * ��ѯ�Ƿ���idΪbid��collection
	 * @return
	 */
	public boolean queryBlogInCollection(User user,Integer bid);

	public void removeBlogFromCollections(User user,Integer bid);

	/**
	 * ͳ���ղصĲ�������
	 */
	public int getMyCollectionCount(User user);

	/**
	 * ��ѯָ��ҳ�ղصĲ�������
	 */
	public List<Collection> queryMyPage(User user, int currentPageIndex,
			int countPerPage);

}
