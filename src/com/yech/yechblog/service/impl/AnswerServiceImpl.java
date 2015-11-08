package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Answer;
import com.yech.yechblog.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Resource(name="answerDao")
	private BaseDao<Answer> answerDao;
	/**
	 * �����
	 */
	@Override
	public void saveAnswer(Answer model) {
		answerDao.saveEntity(model);
	}
	
	/**
	 * ��ѯ��ǰ��������лش�
	 */
	@Override
	public List<Answer> queryAllAnswers(Integer qid) {
		String hql = "from Answer a where a.question.id = ?";
		List<Answer> answers = answerDao.batchFindEntityByHQL(hql, qid);
		for(Answer answer : answers){
			answer.getUser().getUsername();
		}
		return answers;
	}

	/**
	 * ����id��ѯanswer
	 */
	@Override
	public Answer getAnswerById(Integer aid) {
		Answer answer = answerDao.getEntity(aid);
		return answer;
	}

}
