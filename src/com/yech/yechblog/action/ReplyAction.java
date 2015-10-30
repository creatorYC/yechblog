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
import com.yech.yechblog.entity.Reply;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.CommentService;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.ReplyService;
/**
 * ReplyAction
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply> implements UserAware{

	private static final long serialVersionUID = -6448012278102029221L;

	@Resource
	private ReplyService replyService;
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private MessageService messageService;
	//ע�뵱ǰ��¼���û�(���ظ�����)
	private User user;
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	//����ҳ�洫������comment id
	private Integer cid;
	
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	/**
	 * ���һ���ظ�
	 * @return
	 */
	public String addReply(){
		
		//��ӻظ�����ǰ����
		Comment comment2 = commentService.getCommentById(cid);
		Blog blog = comment2.getBlog();
		Comment comment = new Comment();
		comment.setId(cid);
		//���ûظ�������֮��Ĺ�����ϵ
		model.setComment(comment);
		model.setSelf(user);
		model.setOther(comment2.getUser());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setReplyTime(format.format(new Date()));
		replyService.saveReply(model);
		
		//��������Ϣ��ӵ� Message ��
		Message message = new Message();
		message.setContent(model.getContent());
		message.setSelf(user);
		message.setOther(comment2.getUser());
		message.setStatus(true);//1����δ��
		message.setReply(true);//1�����ǻظ�����
		message.setBlog(blog);
		message.setCreateTime(format.format(new Date()));
		messageService.saveMessage(message);
		return "toMessageCenterPage";
	}
}
