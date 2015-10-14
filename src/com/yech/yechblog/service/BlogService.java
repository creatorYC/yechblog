package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.User;

/**
 * 
 * @author Administrator
 *
 */
public interface BlogService{

	/**
	 * ���ҵ�ǰ�û����в���
	 * @param user 
	 */
	public List<Blog> findMyBlogs(User user);

	/**
	 * �½�/�༭����
	 * @return
	 */
	public void saveOrUpdateBlog(Blog model);

	/**
	 * ��ѯ�����û�����
	 * @return
	 */
	public List<Blog> findAllBlogs();

	/**
	 * �Ķ�ȫ��
	 * @param model
	 */
	public Blog readDetail(Integer bid);

	/**
	 * Ϊ��ǰ�����������
	 * @param model
	 */
	public void addComment(Comment model);

	/**
	 * ��ѯ��ǰ������������
	 */
	public List<Comment> queryAllComments(Integer bid);

}
