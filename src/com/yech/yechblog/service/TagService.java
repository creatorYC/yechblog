package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Tag;


public interface TagService{

	public void saveTag(Tag blogTag);

	/**
	 * ���ݲ���id ��ѯ�˲��������ı�ǩ
	 * @param bid
	 */
	public List<Tag> queryBlogTags(Integer bid);

}
