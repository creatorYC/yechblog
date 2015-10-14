package com.yech.yechblog.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;

/**
 * CommentAction
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class CommentAction extends BaseAction<Comment> implements UserAware{

	private static final long serialVersionUID = 1462140807513502797L;

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
	// ����ҳ���bid(blog id)
	private Integer bid;

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}
	
	/**
	 * Ϊ��ǰ�����������
	 * @return
	 */
	public String addComment(){
		model.setUser(user);
		Blog blog = new Blog();
		blog.setId(bid);
		model.setBlog(blog);
		blog.getComments().add(model);
		blogService.addComment(model);
		return "toDetailReadPage";
	}
	
}
