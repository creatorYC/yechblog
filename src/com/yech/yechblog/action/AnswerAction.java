package com.yech.yechblog.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Answer;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.AnswerService;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.QuestionService;

@Controller
@Scope("prototype")
public class AnswerAction extends BaseAction<Answer> implements UserAware {

	private static final long serialVersionUID = 6669153036581044844L;

	@Resource
	private QuestionService questionService;

	@Resource
	private AnswerService answerService;
	
	@Resource
	private MessageService messageService;
	// ���� User ����
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	private Integer qid;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	/**
	 * Ϊ��question��ӻش�
	 */
	public String addAnswer() {
		// ���ûش�������Ĺ�����ϵ
		model.setUser(user);
		Question question2 = questionService.getQuestionById(qid);//Ϊ�˵õ��������������
		Question question = new Question();
		question.setId(qid);
		model.setQuestion(question);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setAnswerTime(format.format(new Date()));
		question.getAnswers().add(model);
		answerService.saveAnswer(model);

		// ��������Ϣ��ӵ� Message ��
		Message message = new Message();
		message.setContent(model.getContent());
		message.setSelf(user);
		message.setOther(question2.getUser());
		message.setAnswer(true);//��̬�����ǻش�
		message.setStatus(true);// 1����δ��
		message.setQuestion(question2);
		message.setCreateTime(format.format(new Date()));
		messageService.saveMessage(message);
		return "toDetailReadPage";
	}
}
