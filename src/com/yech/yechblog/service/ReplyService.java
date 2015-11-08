package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Reply;

public interface ReplyService {

	/**
	 * ����reply
	 */
	public void saveReply(Reply model);

	/**
	 * ���ݵ�ǰ����id��ѯ�����۵����лظ�
	 * @return
	 */
	public List<Reply> queryAllReplies(Integer id);

	/**
	 * ���ݵ�ǰanswer id��ѯ�˴𰸵�����׷��
	 * @return
	 */
	public List<Reply> queryAllQuestionReplies(Integer id);

}
