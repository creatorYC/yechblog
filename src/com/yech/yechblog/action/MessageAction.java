package com.yech.yechblog.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Answer;
import com.yech.yechblog.entity.Comment;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.Reply;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.AnswerService;
import com.yech.yechblog.service.BlogService;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.QuestionService;
import com.yech.yechblog.service.ReplyService;

/**
 * CommentAction
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class MessageAction extends BaseAction<Message> implements UserAware {

	private static final long serialVersionUID = -5672583535553188385L;

	@Resource
	private MessageService messageService;

	@Resource
	private BlogService blogService;

	@Resource
	private ReplyService replyService;
	
	@Resource
	private AnswerService answerService;
	
	@Resource
	private QuestionService questionService;
	//���е��������͵���Ϣ
	private List<Comment> comments;
	//���е�answer���͵���Ϣ
	private List<Answer> answers;
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	//���лظ����͵���Ϣ
	//���۵Ļظ�--- Map<����id,��Ӧ���۵Ļظ�>
	private Map<Integer, List<Reply>> allReplies = 
					new HashMap<Integer, List<Reply>>();

	private Map<Integer, List<Reply>> allQuestionReplies = 
					new HashMap<Integer, List<Reply>>();
	
	public Map<Integer, List<Reply>> getAllQuestionReplies() {
		return allQuestionReplies;
	}

	public void setAllQuestionReplies(Map<Integer, List<Reply>> allQuestionReplies) {
		this.allQuestionReplies = allQuestionReplies;
	}

	public Map<Integer, List<Reply>> getAllReplies() {
		return allReplies;
	}

	public void setAllReplies(Map<Integer, List<Reply>> allReplies) {
		this.allReplies = allReplies;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	// ����ҳ�洫������ message �� id
	private Integer mid;
	private Integer bid;//����id
	private Integer qid;//����id
	
	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	// ����ȥ��������LoginInteceptor ������
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	/**
	 * ȥ��������Ϣ����ϸҳ��
	 * 
	 * @return
	 */
	public String toDetailMessage() {
		// �� message ���е� status �ֶ���Ϊ 0 ����ʾ�Ѷ�
		messageService.changeMessageStatus(mid);
		model = messageService.getMessageById(mid);
		if(model.getAnswer() || model.getAddAsk()){ //�� answer ������Ϣ
			answers = answerService.queryAllAnswers(qid);
			for(Answer answer : answers){
				List<Reply> replies = replyService.queryAllQuestionReplies(answer.getId());
				allQuestionReplies.put(answer.getId(), replies);
			}
			model.setQuestion(questionService.getQuestionById(qid));
		} else { //����answer���͵���Ϣ
			comments = blogService.queryAllComments(bid);// ��ѯ������Ϣ����������ϸ��Ϣҳ����ʾ
			for(Comment comment : comments){ //��ѯÿ�����۶�Ӧ�Ļظ�
				List<Reply> replies = replyService.queryAllReplies(comment.getId());
				allReplies.put(comment.getId(), replies);
			}
			model.setBlog(blogService.getBlogById(bid));
		}
		return "toDetailMessagePage";
	}

}
