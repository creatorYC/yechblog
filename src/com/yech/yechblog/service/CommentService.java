package com.yech.yechblog.service;

import com.yech.yechblog.entity.Comment;

public interface CommentService {

	/**
	 * ���� id ��ѯcomment
	 * @return
	 */
	public Comment getCommentById(Integer cid);

}
