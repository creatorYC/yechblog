package com.yech.yechblog.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Blog;
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

	// �����б�
	private List<Blog> blogList;

	public List<Blog> getBlogList() {
		return blogList;
	}

	public void setBlogList(List<Blog> blogList) {
		this.blogList = blogList;
	}

	// ����ҳָ��
	private String inputPage;

	public String getInputPage() {
		return inputPage;
	}

	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
	}

	/**
	 * ��ѯ��ǰ�û����в���
	 */
	public String queryAllBlogs() {
		blogList = blogService.batchFindEntityByHQL(user);
		return "allBlogsList";
	}

	/**
	 * ȥ��д���ͽ���
	 * @return
	 */
	public String toWriteBlogPage(){
		return "toWriteBlogPage";
	}
	/**
	 * �½�����
	 */
	public String newBlog() {
		model.setUser(user);
		blogService.saveOrUpdateBlog(model);
		return "writeBlog";
	}
}
