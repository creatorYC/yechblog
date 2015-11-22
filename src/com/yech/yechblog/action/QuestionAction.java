package com.yech.yechblog.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Answer;
import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.Reply;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.AnswerService;
import com.yech.yechblog.service.QuestionService;
import com.yech.yechblog.service.ReplyService;

@Controller
@Scope("prototype")
public class QuestionAction extends BaseAction<Question> implements UserAware {

	private static final long serialVersionUID = -8313968563365971788L;

	// ע�� user
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Resource
	private QuestionService questionService;

	@Resource
	private AnswerService answerService;
	
	@Resource
	private ReplyService replyService;
	// ��ʾ�ڵ�ǰҳ��question
	private List<Question> allQuestionList;
	//������ͬ�����question
	private List<Question> similarQuestionList;
	//��ǰ����Ļش�
	private List<Answer> allAnswers;
	//��ǰ�û��������б�
	private List<Question> myQuestions;
	
	public List<Question> getMyQuestions() {
		return myQuestions;
	}

	public void setMyQuestions(List<Question> myQuestions) {
		this.myQuestions = myQuestions;
	}

	// ��ǰ���Ͷ�Ӧ���۵Ļظ�--- Map<����id,��Ӧ���۵Ļظ�>
	private Map<Integer, List<Reply>> allQuestionReplies = 
					new HashMap<Integer, List<Reply>>();
	
	public Map<Integer, List<Reply>> getAllQuestionReplies() {
		return allQuestionReplies;
	}

	public void setAllQuestionReplies(Map<Integer, List<Reply>> allQuestionReplies) {
		this.allQuestionReplies = allQuestionReplies;
	}

	public List<Answer> getAllAnswers() {
		return allAnswers;
	}

	public void setAllAnswers(List<Answer> allAnswers) {
		this.allAnswers = allAnswers;
	}

	public List<Question> getSimilarQuestionList() {
		return similarQuestionList;
	}

	public void setSimilarQuestionList(List<Question> similarQuestionList) {
		this.similarQuestionList = similarQuestionList;
	}

	public List<Question> getAllQuestionList() {
		return allQuestionList;
	}

	public void setAllQuestionList(List<Question> allQuestionList) {
		this.allQuestionList = allQuestionList;
	}

	/**
	 * ȥ��������ҳ��
	 * 
	 * @return
	 */
	public String toAskQuestionPage() {
		return "toAskQuestionPage";
	}

	/**
	 * �½�����
	 * 
	 * @return
	 */
	public String newQuestion() {
		model.setUser(user);
		model.setDeleted(false);// ����δɾ��(�߼�ɾ��)
		model.setReadCount(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setCreateTime(format.format(new Date()));

		questionService.saveOrUpdateQuestion(model);
		return "QuestionAction";
	}

	// ����ҳ�������Ĭ�ϵ�һҳ
	private String pageIndex;
	// ��ǰҳ��
	private int currentPageIndex;
	// ��ҳ��
	private int pageCount;

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * ��ҳ��ʾ�����б�
	 * 
	 * @return
	 */
	public String pagination() {
		int countPerPage = 15;// ÿҳ��ʾ15��
		if (pageIndex == null) {
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		// ��ѯquestion������
		int questionCount = questionService.getQuestionCount();
		allQuestionList = questionService.queryPage(currentPageIndex,
				countPerPage);
		// ��ҳ��
		pageCount = (questionCount % countPerPage == 0 ? questionCount
				/ countPerPage : (questionCount / countPerPage + 1));
		if(questionCount == 0){
			pageCount = 1;	//Ϊ����ҳ���ϲ���ʾ����1ҳ/��0ҳ������Ч��
		}
		return "allQuestionList";
	}

	// ����ҳ�洫����������id
	private Integer qid;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	/**
	 * �鿴��������
	 * 
	 * @return
	 */
	public String readDetail() {
		model = questionService.readDetail(qid);
		model.setReadCount(model.getReadCount() + 1);
		allAnswers = answerService.queryAllAnswers(qid);
		questionService.saveOrUpdateQuestion(model);// ����������������
		//��ѯ��ǰ�𰸵�׷���б�
		for(Answer answer : allAnswers){
			List<Reply> replies = 
					replyService.queryAllQuestionReplies(answer.getId());
			allQuestionReplies.put(answer.getId(), replies);
		}
		return "detailReadPage";
	}
	
	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * ����ͬ����������б��ҳ��ʾ
	 * @return
	 */
	public String similarQuestionsPagination(){
		int countPerPage = 15;// ÿҳ��ʾ15��
		if (pageIndex == null) {
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		//��ѯ������ͬ�������������
		int questionCount = questionService.getSimilarQuestionCount(categoryName);
		// ��ʾ�ڵ�ǰҳ�Ĳ���
		similarQuestionList = questionService.querySimilarQuestionPage(categoryName,
				currentPageIndex, countPerPage);
		// ��ҳ��
		pageCount = (questionCount % countPerPage == 0 ? questionCount / countPerPage
				: (questionCount / countPerPage + 1));
		if(questionCount == 0){
			pageCount = 1;	//Ϊ����ҳ���ϲ���ʾ����1ҳ/��0ҳ������Ч��
		}
		return "similarQuestions";
	}
	
	/**
	 * �༭����
	 * @return
	 */
	public String editQuestion(){
		model = questionService.getQuestionById(qid);
		return "editQuestionPage";
	}
	
	/**
	 * ��������
	 * @return
	 */
	public String updateQuestion(){
		model.setId(qid);
		model.setUser(user);// ���ֹ�����ϵ
		model.setDeleted(false);
		model.setReadCount(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setCreateTime(format.format(new Date()));
		questionService.updateQuestion(model);
		return "redirectToPersonalPage";
	}
	/*
	 *ʹ�� ajax �ķ�ʽɾ������������ʽ����ɾ�������Ϣ
	 */
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * ɾ������
	 * @return
	 */
	public String deleteQuestion(){
		try {
			questionService.deleteQuestion(qid);
			inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return "ajax-success";
	}
	
	/**
	 * ��ѯ��ǰ�û�ӵ�е�����
	 * @return
	 */
	public String myPagination(){
		int countPerPage = 15;// ÿҳ��ʾ15��
		if (pageIndex == null) {
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		// ��ѯquestion������
		int questionCount = questionService.getMyQuestionCount(user.getId());
		myQuestions = questionService.queryMyPage(user.getId(),currentPageIndex,
				countPerPage);
		// ��ҳ��
		pageCount = (questionCount % countPerPage == 0 ? questionCount
				/ countPerPage : (questionCount / countPerPage + 1));
		if(questionCount == 0){
			pageCount = 1;	//Ϊ����ҳ���ϲ���ʾ����1ҳ/��0ҳ������Ч��
		}
		return "myQuestionList";
	}

}
