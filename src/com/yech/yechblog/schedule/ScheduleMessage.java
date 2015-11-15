package com.yech.yechblog.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.util.Global;

@Service
public class ScheduleMessage {

	@Resource
	private MessageService messageService;

	/**
	 * ��ʱ����ÿ��5���ӻ�ȡһ�����ݿ� message ���е���Ϣ
	 */
	public void scheduleTask() {
		System.out.println("����ʼ��...");
		User user = Global.user;
		if (user != null) {
			List<Message> messages = messageService.getMyUnReadMessages(user);
			user.setMessages(messages);
		}
	}
}
