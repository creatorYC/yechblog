package com.yech.yechblog.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.Tag;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;
import com.yech.yechblog.service.TagService;
import com.yech.yechblog.util.StringUtil;

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

	@Resource
	private TagService tagService;
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
	//������ͬ��ǩ�Ĳ����б�
	private List<Blog> similarBlogList;
	
	public List<Blog> getSimilarBlogList() {
		return similarBlogList;
	}

	public void setSimilarBlogList(List<Blog> similarBlogList) {
		this.similarBlogList = similarBlogList;
	}

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

	//����ҳ�洫�����Ĳ��͵ı�ǩ
	private String myTags;

	public String getMyTags() {
		return myTags;
	}

	public void setMyTags(String myTags) {
		this.myTags = myTags;
	}

	/**
	 * �½�����
	 */
	public String newBlog() {
		// ȥ��CKEditor�Զ����ı�����ӵ�<p></p>��ǩ
		model.setContent(model.getContent().replace("<p>", "")
				.replace("</p>", ""));
		model.setUser(user);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		model.setCreateTime(format.format(new Date()));
		String []tagArr = StringUtil.str2Arr(myTags, ","); //�Զ��ŷָ��ַ���
		Tag blogTag = null;
		if(tagArr != null && tagArr.length > 0){
			for(String str : tagArr){
				//�����ǩ
				blogTag = new Tag();
				blogTag.setTagName(str);
				blogTag.setCreateTime(format.format(new Date()));
				blogTag.setTagDesc("*"+str+"*");
				blogTag.getBlogs().add(model);
				model.getTags().add(blogTag);
				blogService.saveOrUpdateBlog(model);
				tagService.saveTag(blogTag);
			}
		}else{
			blogService.saveOrUpdateBlog(model);
		}
		return "BlogAction";
	}

//	/**
//	 * ��ѯ���������ı�ǩ
//	 * @return
//	 */
//	public List<Tag> queryBlogTags(){
//		return tagService.queryBlogTags(bid);
//	}
//	
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
	
	public String tagName;
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * ���ݴ����tagName ���Һ��д˱�ǩ�Ĳ���
	 * @return
	 */
	public String queryBlogsByTagName(){
		allBlogList = blogService.queryBlogsByTagName(tagName);
		return "similarBlogs";
	}
	
	/**
	 * ����ͬ��ǩ�Ĳ����б��ҳ��ʾ
	 * @return
	 */
	public String similarBlogsPagination(){
		int countPerPage = 5;//ÿҳ��ʾ5��
		if(pageIndex == null)
		{
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		int blogCount = blogService.getSimilarBlogCount(tagName);//��ѯ������ͬ��ǩ�Ĳ�������
		//��ʾ�ڵ�ǰҳ�Ĳ���
		similarBlogList = blogService.querySimilarBlogPage(tagName,currentPageIndex, countPerPage);
		//��ҳ��
		pageCount = 
				(blogCount%countPerPage==0?blogCount/countPerPage:(blogCount/countPerPage + 1));
		return "similarBlogs";
	}
}
