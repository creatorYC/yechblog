package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Answer;

public interface AnswerService {

	/**
	 * �����
	 */
	public void saveAnswer(Answer model);

	/**
	 * ��ѯ��ǰ��������лش�
	 */
	public List<Answer> queryAllAnswers(Integer qid);

	/**
	 * ����id��ѯanswer
	 */
	public Answer getAnswerById(Integer aid);

}
