package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements  MessageService{

	/**
	 * ��д�÷�����Ŀ����Ϊ�˸��ǳ����з�����ע�⣬ָ��ע��ָ���� Dao ����,����Spring
	 * �޷�ȷ��ע���ĸ� Dao( ��Ϊ�ж������������ Dao(���BaseDao��ʵ����))
	 */
	@Resource(name="messageDao")
	private BaseDao<Message> messageDao;

	/**
	 * ������Ϣ
	 */
	@Override
	public void saveMessage(Message message) {
		messageDao.saveEntity(message);
	}
	
	/**
	 * ��ȡ��ǰ�û����յ���δ������Ϣ
	 * @param user
	 */
	@Override
	public List<Message> getMyUnReadMessages(User user) {
		String hql = "from Message m where m.other.id = ? and m.self.id != ? and m.status = ?";
		List<Message> messages = 
				messageDao.batchFindEntityByHQL(hql, user.getId(), user.getId(),true);
		for(Message message : messages){
			message.getOther().getUsername();
			message.getSelf().getUsername();
		}
		return messages;
	}

	/**
	 * �� message ��� status �ֶθ�Ϊ 0����Ϊ�Ѷ�
	 * @param mid
	 */
	@Override
	public void changeMessageStatus(Integer mid) {
		String hql = "update Message m set m.status = ? where m.id = ?";
		messageDao.batchUpdateEntityByHQL(hql,false,mid);
	}

	/**
	 * ���� id �õ���Ϣ
	 * @param mid
	 */
	@Override
	public Message getMessageById(Integer mid) {
		Message message =  messageDao.getEntity(mid);
		message.getBlog().getId();
		return message;
	}

	/**
	 * ��ȡ�Ѿ������ľ���Ϣ
	 */
	@Override
	public List<Message> getOldMessages(User user) {
		String hql = "from Message m where m.other.id = ? and m.self.id != ? and m.status = ?";
		List<Message> messages = 
				messageDao.batchFindEntityByHQL(hql, user.getId(), user.getId(),false);
		for(Message message : messages){
			message.getOther().getUsername();
			message.getSelf().getUsername();
		}
		return messages;
	}

}
