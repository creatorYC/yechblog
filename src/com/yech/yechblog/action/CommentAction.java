package com.yech.yechblog.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;
import com.yech.yechblog.service.MessageService;

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
	
	@Resource
	private MessageService messageService;
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
		//���� blog �� comment �Ĺ�����ϵ
		model.setUser(user);
		
		Blog blog2 = blogService.getBlogById(bid);//Ϊ�˵õ��˲��͵�����
		Blog blog = new Blog();
		blog.setId(bid);
		model.setBlog(blog);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		model.setCommentTime(format.format(new Date()));
		blog.getComments().add(model);
		blogService.addComment(model);
		
		//��������Ϣ��ӵ� Message ��
		Message message = new Message();
		message.setContent(model.getContent());
		message.setSelf(user);
		message.setOther(blog2.getUser());
		message.setStatus(true);//1����δ��
		
		message.setCreateTime(format.format(new Date()));
		messageService.saveMessage(message);
		return "toDetailReadPage";
	}
	
}
