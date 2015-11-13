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

	/**
	 * ͳ�Ʋ�������
	 * @return
	 */
	public int getBlogCount();
	
	/**
	 * �����в����в�ѯָ��ҳ��bolg����
	 * @return
	 */
	public List<Blog> queryPage(int currentPageIndex,int countPerPage);

	/**
	 * �ڵ�ǰ�û������в�ѯָ��ҳ��bolg����
	 * @return
	 */
	public List<Blog> queryMyPage(User user,int currentPageIndex, int countPerPage);
	/**
	 * ͳ�Ƶ�ǰ�û���������
	 * @return
	 */
	public int getMyBlogCount(User user);

	/**
	 * ���� id �������
	 * @return
	 */
	public Blog getBlogById(Integer bid);

	/**
	 * ���ݴ����tagName ���Һ��д˱�ǩ�Ĳ���
	 * @return
	 */
	public List<Blog> queryBlogsByTagName(String tagName);

	/**
	 * ��ѯ���� tagName ��ǩ�Ĳ������������ڷ�ҳ
	 * @return
	 */
	public int getSimilarBlogCount(String tagName);

	/**
	 * �ھ�����ͬ��ǩ�Ĳ����в�ѯָ��ҳ��bolg����
	 * @return
	 */
	public List<Blog> querySimilarBlogPage(String tagName,
			int currentPageIndex, int countPerPage);

	/**
	 * ����blog
	 * @param model
	 */
	public void updateBlog(Blog model);

	/**
	 * ɾ��blog(�߼�ɾ��)
	 */
	public void deleteBlog(Integer bid);

	/**
	 * ������������ƥ��Ĳ���
	 * @param searchCondition
	 * @return 
	 */
	public List<Blog> searchBlogByCondition(int currentPageIndex,int countPerPage,String searchCondition);

	/**
	 * ��ѯƥ��Ĳ�������
	 * @param searchCondition 
	 * @return
	 */
	public int getMatchedBlogCount(String searchCondition);

	/**
	 * �����û�id��ѯ���Ĳ���
	 * @return
	 */
	public List<Blog> queryHisBlogs(Integer userId);

	/**
	 * �ı䲩�͵�����Ȩ��(�Ƿ������)
	 * @param bid
	 */
	public void changeBlogAllowState(Integer bid,Boolean state);

}
