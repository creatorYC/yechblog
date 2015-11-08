package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.User;

public interface QuestionService {

	/**
	 * ͳ��question����
	 */
	public int getQuestionCount();

	/**
	 * ��ѯָ��ҳ��question����
	 * @return
	 */
	public List<Question> queryPage(int currentPageIndex, int countPerPage);

	/**
	 * ��������
	 */
	public void saveOrUpdateQuestion(Question model);

	/**
	 * �鿴��������
	 */
	public Question readDetail(Integer qid);

	/**
	 * ��ѯ������ͬ�������������
	 */
	public int getSimilarQuestionCount(String categoryName);

	/**
	 * �ھ�����ͬ����������в�ѯָ��ҳ��question����
	 */
	public List<Question> querySimilarQuestionPage(String categoryName,
			int currentPageIndex, int countPerPage);

	/**
	 * ��������id��ȡquestion
	 */
	public Question getQuestionById(Integer qid);

	/**
	 * ��ѯ��ǰ�û����������б�
	 * @return
	 */
	public List<Question> queryAllMyQuestions(User user);
	
}
