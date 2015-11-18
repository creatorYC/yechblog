package com.yech.yechblog.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Answer;
import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.Reply;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.AnswerService;
import com.yech.yechblog.service.CommentService;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.ReplyService;
import com.yech.yechblog.service.UserService;

/**
 * ReplyAction
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply> implements UserAware {

	private static final long serialVersionUID = -6448012278102029221L;

	@Resource
	private ReplyService replyService;

	@Resource
	private CommentService commentService;

	@Resource
	private MessageService messageService;

	@Resource
	private AnswerService answerService;
	
	@Resource
	private UserService userService;
	// ע�뵱ǰ��¼���û�(���ظ�����)
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	// ����ҳ�洫������comment id
	private Integer cid;
	// ����ҳ�洫������answer id
	private Integer aid;

	//����ҳ�洫������ userId
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	/**
	 * ���һ�����ۻظ�
	 * 
	 * @return
	 */
	public String addReply() {

		// ��ӻظ�����ǰ����
		Comment comment2 = commentService.getCommentById(cid);
		Blog blog = comment2.getBlog();
		Comment comment = new Comment();
		comment.setId(cid);
		// ���ûظ�������֮��Ĺ�����ϵ
		model.setComment(comment);
		model.setSelf(user);
		if(userId == null){ //userIdΪ�գ���ʾ�ǻظ����˵Ļش�
			model.setOther(comment2.getUser());
		} else{
			//userId��Ϊ�գ���ʾ�ǻظ����˵�׷�ʣ���ʱmodel.setOther(User)�е�user
			//�Ͳ���comment2.getUser()�ˣ���Ҫ��Ϊ��ά��������ϵ����ȷ��
			User other = userService.getEntity(userId);
			model.setOther(other);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setReplyTime(format.format(new Date()));
		replyService.saveReply(model);

		// ��������Ϣ��ӵ� Message ��
		Message message = new Message();
		message.setContent(model.getContent());
		message.setSelf(user);
		if(userId == null){ //userIdΪ�գ���ʾ�ǻظ����˵Ļش�
			message.setOther(comment2.getUser());
		} else{
			//userId��Ϊ�գ���ʾ�ǻظ����˵�׷�ʣ���ʱmodel.setOther(User)�е�user
			//�Ͳ���comment2.getUser()�ˣ���Ҫ��Ϊ��ά��������ϵ����ȷ��
			User other = userService.getEntity(userId);
			message.setOther(other);
		}
		message.setStatus(true);// 1����δ��
		message.setReply(true);// 1�����ǻظ�����
		message.setBlog(blog);
		message.setCreateTime(format.format(new Date()));
		messageService.saveMessage(message);
		return "toMessageCenterPage";
	}

	/**
	 * ���һ������𰸵�׷��
	 * 
	 * @return
	 */
	public String addQuestionReply() {
		// ���׷�ʵ���ǰanswer
		Answer answer2 = answerService.getAnswerById(aid);
		Question question = answer2.getQuestion();
		Answer answer = new Answer();
		answer.setId(aid);
		// ����׷����answer֮��Ĺ�����ϵ
		model.setAnswer(answer);
		model.setSelf(user);
		if(userId == null){ //userIdΪ�գ���ʾ�ǻظ����˵Ļش�
			model.setOther(answer2.getUser());
		} else{
			//userId��Ϊ�գ���ʾ�ǻظ����˵�׷�ʣ���ʱmodel.setOther(User)�е�user
			//�Ͳ���comment2.getUser()�ˣ���Ҫ��Ϊ��ά��������ϵ����ȷ��
			User other = userService.getEntity(userId);
			model.setOther(other);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setReplyTime(format.format(new Date()));
		replyService.saveReply(model);

		// ��׷����Ϣ��ӵ� Message ��
		Message message = new Message();
		message.setContent(model.getContent());
		message.setSelf(user);
		if(userId == null){ //userIdΪ�գ���ʾ�ǻظ����˵Ļش�
			message.setOther(answer2.getUser());
		} else {
			//userId��Ϊ�գ���ʾ�ǻظ����˵�׷�ʣ���ʱmodel.setOther(User)�е�user
			//�Ͳ���comment2.getUser()�ˣ���Ҫ��Ϊ��ά��������ϵ����ȷ��
			User other = userService.getEntity(userId);
			message.setOther(other);
		}
		message.setStatus(true);// 1����δ��
		message.setAddAsk(true);// 1������׷��
		message.setQuestion(question);
		message.setCreateTime(format.format(new Date()));
		messageService.saveMessage(message);
		return "toMessageCenterPage";
	}
}
