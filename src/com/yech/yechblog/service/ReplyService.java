package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Reply;

public interface ReplyService {

	/**
	 * 保存reply
	 */
	public void saveReply(Reply model);

	/**
	 * 根据当前评论id查询此评论的所有回复
	 * @return
	 */
	public List<Reply> queryAllReplies(Integer id);

	/**
	 * 根据当前answer id查询此答案的所有追问
	 * @return
	 */
	public List<Reply> queryAllQuestionReplies(Integer id);

}
