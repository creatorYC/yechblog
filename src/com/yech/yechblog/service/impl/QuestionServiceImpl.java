package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.QuestionService;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Resource(name="questionDao")
	private BaseDao<Question> questionDao;
	/**
	 * ͳ��question����
	 */
	@Override
	public int getQuestionCount() {
		String hql = "select count(*) from Question q where q.deleted = 0";
		return  ((Long)(questionDao.uniqueResult(hql))).intValue();
	}
	
	/**
	 * ��ѯָ��ҳ��question����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> queryPage(int currentPageIndex, int countPerPage) {
		String hql = "SELECT * FROM questions where deleted = 0 order by create_time desc LIMIT ?,?";
		List<Question> questions = questionDao.listResult(Question.class, hql, 
											(currentPageIndex-1) * countPerPage,countPerPage);
		for(Question question : questions){
			question.getUser().getUsername();
			question.getUser().getId();
			question.getAnswers().size();//��ѯ�𰸵ĸ���
		}
		return questions;
	}

	/**
	 * ��������
	 */
	@Override
	public void saveOrUpdateQuestion(Question model) {
		questionDao.saveOrUpdateEntity(model);
	}

	/**
	 * �鿴��������
	 */
	@Override
	public Question readDetail(Integer qid) {
		Question question = questionDao.getEntity(qid);
		question.getUser().getUsername();
		return question;
	}

	/**
	 * ��ѯ������ͬ�������������
	 */
	@Override
	public int getSimilarQuestionCount(String categoryName) {
		String hql = "select count(t.tagName) from Tag t where t.tagName = ?";
		return  ((Long)(questionDao.uniqueResult(hql,categoryName))).intValue();
	}

	/**
	 * �ھ�����ͬ����������в�ѯָ��ҳ��question����
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> querySimilarQuestionPage(String categoryName,
			int currentPageIndex, int countPerPage) {
		String sql = "select * from questions q where q.category = ? "
				+ "and q.deleted = 0 order by q.create_time desc limit ?,?";
		List<Question> questions = 
				questionDao.listResult(Question.class, sql, categoryName,
						(currentPageIndex-1) * countPerPage,countPerPage);
		for(Question question : questions){
			question.getUser().getUsername();
			question.getAnswers().size();//��ѯ��ǰ����Ļش����
		}
		return questions;
	}

	/**
	 * ��������id��ȡquestion
	 */
	@Override
	public Question getQuestionById(Integer qid) {
		Question question = questionDao.getEntity(qid);
		question.getUser().getUsername();
		return question;
	}

	/**
	 * ��ѯ��ǰ�û����������б�
	 * @return
	 */
	@Override
	public List<Question> queryAllMyQuestions(User user) {
		String hql = "from Question q where q.user.id = ? "
				+ "and q.deleted = 0 order by q.createTime desc";
		List<Question> questions = 
				questionDao.batchFindEntityByHQL(hql, user.getId());
		for(Question question : questions){
			question.getAnswers().size();
			question.getUser().getUsername();
		}
		return questions;
	}

	/**
	 * ɾ������
	 */
	@Override
	public void deleteQuestion(Integer qid) {
		String hql = "update Question q set q.deleted = 1 where q.id = ?";
		questionDao.batchUpdateEntityByHQL(hql, qid);
	}

	/**
	 * ��������
	 */
	@Override
	public void updateQuestion(Question question) {
		questionDao.updateEntity(question);
	}

	/**
	 * ����id��ѯ���û�����������
	 */
	@Override
	public List<Question> queryHisQuestions(Integer userId) {
		String hql = "from Question q where q.user.id = ? and q.deleted = 0 "
				+ "order by q.createTime desc";
		List<Question> questions = questionDao.batchFindEntityByHQL(hql, userId);
		for(Question question : questions){
			question.getAnswers().size();
			question.getUser().getUsername();
		}
		return questions;
	}

	/**
	 * ��ѯ��������
	 * @return
	 */
	@Override
	public List<Question> queryAllQuestions() {
		String hql = "from Question q where q.deleted = 0";
		List<Question> questions = questionDao.batchFindEntityByHQL(hql);
		questions.size();
		return questions;
	}

	/**
	 * ��ѯ��ǰ�û�����������
	 */
	@Override
	public int getMyQuestionCount(Integer userId) {
		String hql = "select count(*) from Question q where q.deleted = 0 "
					+ "and q.user.id = ?";
		return  ((Long)(questionDao.uniqueResult(hql,userId))).intValue();
	}

	/**
	 * �ڵ�ǰ�û������в�ѯָ��ҳ��question����
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> queryMyPage(Integer userId, int currentPageIndex,
			int countPerPage) {
		String hql = "SELECT * FROM questions WHERE userid = ? and deleted = 0 "
				+ "order by create_time desc LIMIT ?,?";
		List<Question> questions = questionDao.listResult(Question.class, hql, userId,
				(currentPageIndex-1) * countPerPage,countPerPage);
		for(Question question : questions){
			question.getAnswers().size();
			question.getUser().getUsername();
		}
		return questions;
	}

}
