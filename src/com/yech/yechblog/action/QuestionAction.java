package com.yech.yechblog.action;

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

	// 注入 user
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
	// 显示在当前页的question
	private List<Question> allQuestionList;
	//具有相同分类的question
	private List<Question> similarQuestionList;
	//当前问题的回答
	private List<Answer> allAnswers;
	// 当前博客对应评论的回复--- Map<评论id,对应评论的回复>
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
	 * 去到提问题页面
	 * 
	 * @return
	 */
	public String toAskQuestionPage() {
		return "toAskQuestionPage";
	}

	/**
	 * 新建问题
	 * 
	 * @return
	 */
	public String newQuestion() {
		model.setUser(user);
		model.setDeleted(false);// 设置未删除(逻辑删除)
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		model.setCreateTime(format.format(new Date()));

		questionService.saveOrUpdateQuestion(model);
		return "QuestionAction";
	}

	// 接收页面参数，默认第一页
	private String pageIndex;
	// 当前页数
	private int currentPageIndex;
	// 总页数
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
	 * 分页显示问题列表
	 * 
	 * @return
	 */
	public String pagination() {
		int countPerPage = 15;// 每页显示15条
		if (pageIndex == null) {
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		// 查询question的总数
		int questionCount = questionService.getQuestionCount();
		allQuestionList = questionService.queryPage(currentPageIndex,
				countPerPage);
		// 总页数
		pageCount = (questionCount % countPerPage == 0 ? questionCount
				/ countPerPage : (questionCount / countPerPage + 1));
		return "allQuestionList";
	}

	// 接收页面传过来的问题id
	private Integer qid;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	/**
	 * 查看问题详情
	 * 
	 * @return
	 */
	public String readDetail() {
		model = questionService.readDetail(qid);
		model.setReadCount(model.getReadCount() + 1);
		allAnswers = answerService.queryAllAnswers(qid);
		questionService.saveOrUpdateQuestion(model);// 更新问题的浏览次数
		//查询当前答案的追问列表
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
	 * 有相同分类的问题列表分页显示
	 * @return
	 */
	public String similarQuestionsPagination(){
		int countPerPage = 15;// 每页显示15条
		if (pageIndex == null) {
			pageIndex = "1";
		}
		currentPageIndex = Integer.parseInt(pageIndex);
		//查询具有相同分类的问题总数
		int questionCount = questionService.getSimilarQuestionCount(categoryName);
		// 显示在当前页的博客
		similarQuestionList = questionService.querySimilarQuestionPage(categoryName,
				currentPageIndex, countPerPage);
		// 总页数
		pageCount = (questionCount % countPerPage == 0 ? questionCount / countPerPage
				: (questionCount / countPerPage + 1));
		return "similarQuestions";
	}
	
	/**
	 * 编辑问题
	 * @return
	 */
	public String editQuestion(){
		return "";
	}
	
	/**
	 * 删除问题
	 * @return
	 */
	public String deleteQuestion(){
		return "";
	}

}
