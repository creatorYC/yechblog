package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.Reply;
import com.yech.yechblog.entity.Tag;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource(name="blogDao")
	private BaseDao<Blog> blogDao;

	@Resource(name="commentDao")
	private BaseDao<Comment> commentDao;
	
	@Resource(name="messageDao")
	private BaseDao<Message> messageDao;
	
	@Resource(name="replyDao")
	private BaseDao<Reply> replyDao;
	/**
	 * �������в���
	 */
	@Override
	public List<Blog> findMyBlogs(User user) {
		String hql = "from Blog b where b.user.id = ? and b.deleted = 0 "
				+ "order by b.createTime desc";
		List<Blog> blogs = blogDao.batchFindEntityByHQL(hql,user.getId());
		//����blog������user������������
		for(Blog blog : blogs){
			blog.getComments().size();
			blog.getUser().getUsername();
			blog.getId();
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
		String hql = "from Blog b where b.deleted = 0";
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
		String hql = "select count(*) from Blog b where b.deleted = 0";
		return  ((Long)(blogDao.uniqueResult(hql))).intValue();
	}

	/**
	 * ��ѯָ��ҳ��bolg����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> queryPage(int currentPageIndex, int countPerPage) {
		String hql = "SELECT * FROM blogs where deleted = 0 order by create_time desc LIMIT ?,?";
		List<Blog> blogs = (List<Blog>) blogDao.listResult(Blog.class,hql,
				(currentPageIndex-1) * countPerPage,countPerPage);
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
		String hql = "SELECT * FROM blogs WHERE userid = ? and deleted = 0 order by create_time desc LIMIT ?,?";
		List<Blog> blogs = (List<Blog>) blogDao.listResult(Blog.class,hql,user.getId(), 
				(currentPageIndex-1) * countPerPage,countPerPage);
		for(Blog blog : blogs){
			blog.getUser().getUsername();
			blog.getTags().size();
		}
		return blogs;
	}

	/**
	 * ͳ�Ƶ�ǰ�û���������
	 * @return
	 */
	@Override
	public int getMyBlogCount(User user) {
		String hql = "select count(*) from Blog b where b.user.id = ? and b.deleted = 0";
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
		for(Tag tag : blog.getTags()){
			tag.getTagName();
		}
		return blog;
	}

	/**
	 * ���ݴ����tagName ���Һ��д˱�ǩ�Ĳ���
	 * @return
	 */
	@Override
	public List<Blog> queryBlogsByTagName(String tagName) {
		String hql = "select t.blogs from Tag t where t.tagName = ? order by t.createTime desc";
		List<Blog> blogs = blogDao.batchFindEntityByHQL(hql, tagName);
		for(Blog blog : blogs){
			//����������
			blog.getUser().getUsername();
			blog.getComments().size();
			blog.getTags().size();
		}
		return blogs;
	}

	/**
	 * ��ѯ���� tagName ��ǩ�Ĳ������������ڷ�ҳ
	 * @return
	 */
	@Override
	public int getSimilarBlogCount(String tagName) {
		String hql = "select count(t.tagName) from Tag t where t.tagName = ?";
		return  ((Long)(blogDao.uniqueResult(hql,tagName))).intValue();
	}

	/**
	 * �ھ�����ͬ��ǩ�Ĳ����в�ѯָ��ҳ��bolg����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> querySimilarBlogPage(String tagName,
			int currentPageIndex, int countPerPage) {
		String sql = "select b.*,t.* from tags_blogs as tb "
				+ "left join tags as t on t.id = tb.t_id "
				+ "left join blogs as b on b.id = tb.b_id "
				+ "where t.tag_name = ? and b.deleted = 0 "
				+ "order by t.create_time desc limit ?,?";
		List<Blog> blogs = (List<Blog>) blogDao.listResult(Blog.class,sql,tagName, 
				(currentPageIndex-1) * countPerPage,countPerPage);
		for(Blog blog : blogs){
			blog.getUser().getUsername();
			blog.getComments().size();
			blog.getTags().size();
		}
		return blogs;
	}

	/**
	 * ����blog
	 */
	@Override
	public void updateBlog(Blog model) {
		blogDao.updateEntity(model);
	}

	/**
	 * ɾ��blog(�߼�ɾ��)
	 * ͬʱɾ�����ۡ��ظ�����ǩ����Ϣ
	 */
	@Override
	public void deleteBlog(Integer bid) {
		//ɾ����ǩ
//		String hql = "delete from tags_blogs where tb.b_id = ?";
//		blogDao.executeSQL(hql, bid);
		
		//ɾ����Ϣ
		String hql = "delete from Message m where m.blog.id = ?";
		messageDao.batchUpdateEntityByHQL(hql, bid);
		
		//ɾ���ظ�
		hql = "delete from Reply r where r.comment.id in (select c.id "
				+ "from Comment c where c.blog.id = ?)";
		replyDao.batchUpdateEntityByHQL(hql, bid);
		
		//ɾ������
		hql = "delete from Comment c where c.blog.id = ?";
		commentDao.batchUpdateEntityByHQL(hql, bid);
	
		//ɾ������
		hql = "update Blog b set b.deleted = 1 where b.id = ?";
		blogDao.batchUpdateEntityByHQL(hql, bid);
	}

	/**
	 * ������������ƥ��Ĳ���
	 * @param searchCondition
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> searchBlogByCondition(int currentPageIndex,int countPerPage,String searchCondition) {
		String hql = "SELECT * FROM blogs b where b.deleted = 0 and "
				+ "b.title like ? order by b.create_time desc LIMIT ?,?";
		List<Blog> blogs = 
				blogDao.listResult(Blog.class, hql,"%"+searchCondition+"%",
						(currentPageIndex-1) * countPerPage,countPerPage);
		for(Blog blog : blogs){
			blog.getUser().getUsername();
			blog.getComments().size();
			blog.getTags().size();
		}
		return blogs;
	}

	/**
	 * ��ѯƥ��Ĳ�������
	 * @return
	 */
	@Override
	public int getMatchedBlogCount(String searchCondition) {
		String hql = "select count(*) from Blog b "
				+ "where b.title like ? and b.deleted = 0";
		return  ((Long)(blogDao.uniqueResult(hql,"%"+searchCondition+"%"))).intValue();
	}

	/**
	 * �����û�id��ѯ���Ĳ���
	 * @return
	 */
	@Override
	public List<Blog> queryHisBlogs(Integer userId) {
		String hql = "from Blog b where b.user.id = ? and b.deleted = 0";
		List<Blog> blogs = blogDao.batchFindEntityByHQL(hql, userId);
		for(Blog blog : blogs){
			blog.getId();
			blog.getUser().getUsername();
			blog.getComments().size();
		}
		return blogs;
	}

	/**
	 * �ı䲩�͵�����Ȩ��(�Ƿ������)
	 * @param bid
	 */
	@Override
	public void changeBlogAllowState(Integer bid,Boolean state) {
		String hql = "update Blog b set b.allowComment = ? where b.id = ?";
		blogDao.batchUpdateEntityByHQL(hql, state,bid);
	}

}
