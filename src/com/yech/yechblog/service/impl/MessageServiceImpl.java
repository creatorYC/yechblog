package com.yech.yechblog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.service.MessageService;

@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message>
										implements  MessageService{

	/**
	 * ��д�÷�����Ŀ����Ϊ�˸��ǳ����з�����ע�⣬ָ��ע��ָ���� Dao ����,����Spring
	 * �޷�ȷ��ע���ĸ� Dao( ��Ϊ�ж������������ Dao(���BaseDao��ʵ����))
	 */
	@Resource(name="messageDao")
	public void setBaseDao(BaseDao<Message> baseDao) {
		super.setBaseDao(baseDao);
	}
}
