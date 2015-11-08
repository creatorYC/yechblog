package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Reply;
import com.yech.yechblog.service.ReplyService;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

	@Resource(name="replyDao")
	private BaseDao<Reply> replyDao;
	
	/**
	 * ����reply
	 */
	@Override
	public void saveReply(Reply reply) {
		replyDao.saveOrUpdateEntity(reply);
	}

	/**
	 * ���ݵ�ǰ����id��ѯ�����۵����лظ�
	 * @return
	 */
	@Override
	public List<Reply> queryAllReplies(Integer cid) {
		String hql = "from Reply r where r.comment.id = ?";
		List<Reply> replies = replyDao.batchFindEntityByHQL(hql, cid);
		for(Reply reply : replies){
			reply.getSelf().getUsername();
			reply.getOther().getUsername();
		}
		return replies;
	}

	/**
	 * ���ݵ�ǰanswer id��ѯ�˴𰸵�����׷��
	 * @return
	 */
	@Override
	public List<Reply> queryAllQuestionReplies(Integer aid) {
		String hql = "from Reply r where r.answer.id = ?";
		List<Reply> replies = replyDao.batchFindEntityByHQL(hql, aid);
		for(Reply reply : replies){
			reply.getSelf().getUsername();
			reply.getOther().getUsername();
		}
		return replies;
	}

}
