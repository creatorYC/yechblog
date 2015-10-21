package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource(name="blogDao")
	private BaseDao<Blog> blogDao;

	@Resource(name="commentDao")
	private BaseDao<Comment> commentDao;
	/**
	 * �������в���
	 */
	@Override
	public List<Blog> findMyBlogs(User user) {
		String hql = "from Blog b where b.user.id = ?";
		List<Blog> blogs = blogDao.batchFindEntityByHQL(hql,user.getId());
		//����blog������user������������
		for(Blog blog : blogs){
			blog.getUser().getUsername();
		}
		return blogs;
	}

	/**
	 * �½�/�༭����
	 * @return
	 */
	@Override
	public void saveOrUpdateBlog(Blog blog) {
		blogDao.saveOrUpdateEntity(blog);
	}

	/**
	 * ��ѯ�����û�����
	 * @return
	 */
	@Override
	public List<Blog> findAllBlogs() {
		String hql = "from Blog";
		List<Blog> blogs = blogDao.batchFindEntityByHQL(hql);
		//����blog������user������������
		for(Blog blog : blogs){
			blog.getUser().getUsername();
		}
		return blogs;
	}

	/**
	 * �Ķ�ȫ��
	 */
	@Override
	public Blog readDetail(Integer bid) {
		
		Blog blog =  blogDao.getEntity(bid);
		blog.getComments().size();
		//��ȡblog������user������������
		blog.getUser().getUsername();
		return blog;
	}

	/**
	 * Ϊ��ǰ�����������
	 * @param bid
	 */
	@Override
	public void addComment(Comment comment) {
		commentDao.saveOrUpdateEntity(comment);
	}

	/**
	 * ��ѯ��ǰ������������
	 */
	@Override
	public List<Comment> queryAllComments(Integer bid) {
		String hql = "from Comment c where c.blog.id = ?";
		List<Comment> comments = commentDao.batchFindEntityByHQL(hql, bid);
		for(Comment comment : comments){
			comment.getUser().getUsername();
		}
		return comments;
	}

	/**
	 * ͳ�Ʋ�������
	 */
	@Override
	public int getBlogCount() {
		String hql = "select count(*) from Blog";
		return  ((Long)(blogDao.uniqueResult(hql))).intValue();
	}

	/**
	 * ��ѯָ��ҳ��bolg����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> queryPage(int currentPageIndex, int countPerPage) {
		String hql = "SELECT * FROM blogs LIMIT ?,?";
		List<Blog> blogs = (List<Blog>) blogDao.listResult(Blog.class,hql, (currentPageIndex-1) * countPerPage,
							countPerPage);
		for(Blog blog : blogs){
			blog.getUser().getUsername();
			blog.getTags().size();
		}
		return blogs;
	}
	
	/**
	 * �ڵ�ǰ�û������в�ѯָ��ҳ��bolg����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> queryMyPage(User user,int currentPageIndex, int countPerPage) {
		String hql = "SELECT * FROM blogs WHERE userid = ? LIMIT ?,?";
		List<Blog> blogs = (List<Blog>) blogDao.listResult(Blog.class,hql,user.getId(), (currentPageIndex-1) * countPerPage,
				currentPageIndex * countPerPage);
		for(Blog blog : blogs){
			blog.getUser().getUsername();
		}
		return blogs;
	}

	/**
	 * ͳ�Ƶ�ǰ�û���������
	 * @return
	 */
	@Override
	public int getMyBlogCount(User user) {
		String hql = "select count(*) from Blog b where b.user.id = ?";
		return  ((Long)(blogDao.uniqueResult(hql,user.getId()))).intValue();
	}

	/**
	 * ���� id �������
	 * @return
	 */
	@Override
	public Blog getBlogById(Integer bid) {
		Blog blog = blogDao.getEntity(bid);
		blog.getUser().getUsername();
		return blog;
	}

	/**
	 * ���ݴ����tagName ���Һ��д˱�ǩ�Ĳ���
	 * @return
	 */
	@Override
	public List<Blog> queryBlogsByTagName(String tagName) {
		String hql = "select t.blogs from Tag t where t.tagName = ?";
		List<Blog> blogs = blogDao.batchFindEntityByHQL(hql, tagName);
		for(Blog blog : blogs){
			blog.getComments().size();
			blog.getTags().size();
		}
		return blogs;
	}

}
