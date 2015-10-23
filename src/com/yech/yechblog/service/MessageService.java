package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.User;

public interface MessageService{

	/**
	 * ��ȡ��ǰ�û����յ���δ������Ϣ
	 * @param user
	 */
	public List<Message> getMyUnReadMessages(User user);

	/**
	 * �� message ��� status �ֶθ�Ϊ 0����Ϊ�Ѷ�
	 * @param mid
	 */
	public void changeMessageStatus(Integer mid);

	/**
	 * ������Ϣ
	 * @param message
	 */
	public void saveMessage(Message message);

	/**
	 * ���� id �õ���Ϣ
	 * @param mid
	 */
	public Message getMessageById(Integer mid);

	/**
	 * ��ȡ�Ѿ������ľ���Ϣ
	 * @return
	 */
	public List<Message> getOldMessages(User model);

	/**
	 * ��ѯ��ǰ�û��Ķ�̬
	 * @return
	 */
	public List<Message> queryUserActivities(User user);

}
