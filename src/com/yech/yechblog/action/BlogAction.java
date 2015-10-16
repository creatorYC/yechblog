package com.yech.yechblog.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;

/**
 * BlogAction
 * 
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class BlogAction extends BaseAction<Blog> implements UserAware {

	private static final long serialVersionUID = 5190914411419980760L;
	/**
	 * ע�� SurveyService
	 */
	@Resource
	private BlogService blogService;

	// ���� User ����
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	// ��ǰ�û��Ĳ����б�
	private List<Blog> myBlogList;

	//
	// ���в����б�
	private List<Blog> allBlogList;

	// ��ǰ���͵������б�
	private List<Comment> allComments;

	public List<Comment> getAllComments() {
		return allComments;
	}

	public void setAllComments(List<Comment> allComments) {
		this.allComments = allComments;
	}

	public List<Blog> getAllBlogList() {
		return allBlogList;
	}

	public void setAllBlogList(List<Blog> allBlogList) {
		this.allBlogList = allBlogList;
	}

	public List<Blog> getMyBlogList() {
		return myBlogList;
	}

	public void setMyBlogList(List<Blog> myBlogList) {
		this.myBlogList = myBlogList;
	}

	/**
	 * ��ѯ�����û��Ĳ��ͣ���������ҳ��ʾ
	 * 
	 * @return
	 */
	public String queryAllBlogs() {
		allBlogList = blogService.findAllBlogs();
		return "allBlogList";
	}

	/**
	 * ��ѯ��ǰ�û����в���
	 */
	public String queryMyBlogs() {
		myBlogList = blogService.findMyBlogs(user);
		return "myBlogList";
	}

	/**
	 * ȥ��д���ͽ���
	 * 
	 * @return
	 */
	public String toWriteBlogPage() {
		return "toWriteBlogPage";
	}

	/**
	 * �½�����
	 */
	public String newBlog() {
		// ȥ��CKEditor�Զ����ı�����ӵ�<p></p>��ǩ
		model.setContent(model.getContent().replace("<p>", "")
				.replace("</p>", ""));
		model.setUser(user);
		blogService.saveOrUpdateBlog(model);
		return "BlogAction";
	}

	// ����ҳ���bid(blog id)
	private Integer bid;

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	/**
	 * �Ķ�ȫ��
	 */
	public String readDetail() {
		model = blogService.readDetail(bid);
		allComments = blogService.queryAllComments(bid);
		return "detailReadPage";
	}
	
	//����ҳ�������Ĭ�ϵ�һҳ
	private String pageIndex;

	//��ǰҳ��
	private int currentPageIndex;
	//��ҳ��
	private int pageCount;
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	/**
	 * ���в����б��ҳ��ʾ
	 * @return
	 */
	public String pagination(){
		int countPerPage = 5;//ÿҳ��ʾ5��
		if(pageIndex == null)
		{
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		int blogCount = blogService.getBlogCount();//��ѯ��������
		//��ʾ�ڵ�ǰҳ�Ĳ���
		allBlogList = blogService.queryPage(currentPageIndex, countPerPage);
		//��ҳ��
		pageCount = 
				(blogCount%countPerPage==0?blogCount/countPerPage:(blogCount/countPerPage + 1));
		return "allBlogList";
	}
	/**
	 * ��ǰ�û������б��ҳ��ʾ
	 * @return
	 */
	public String myPagination(){
		int countPerPage = 5;//ÿҳ��ʾ5��
		if(pageIndex == null)
		{
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		int blogCount = blogService.getMyBlogCount(user);//��ѯ��������
		System.out.println("user="+user.getImage());
		//��ʾ�ڵ�ǰҳ�Ĳ���
		myBlogList = blogService.queryMyPage(user,currentPageIndex, countPerPage);
		//��ҳ��
		pageCount = 
				(blogCount%countPerPage==0?blogCount/countPerPage:(blogCount/countPerPage + 1));
		return "myBlogList";
	}
	
	/**
	 * ȥ������ҳ
	 * @return
	 */
	public String toPersonalPage(){
		return "personalPage";
	}
}
