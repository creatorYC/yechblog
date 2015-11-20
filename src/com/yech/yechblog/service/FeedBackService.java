package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.FeedBack;

public interface FeedBackService extends BaseService<FeedBack> {

	/**
	 * ���淴��
	 * @param feedBack
	 */
	public void saveFeedBack(FeedBack feedBack);

	/**
	 * ��ѯ���з���
	 * @return
	 */
	public List<FeedBack> findAllFeedBacks();

}
