package com.yech.yechblog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> 
						implements UserService {

	/**
	 * ��д�÷�����Ŀ����Ϊ�˸��ǳ����з�����ע�⣬ָ��ע��ָ���� Dao ����,����Spring
	 * �޷�ȷ��ע���ĸ� Dao( ��Ϊ�ж������������ Dao(���BaseDao��ʵ����))
	 */
	@Resource(name="userDao")
	public void setBaseDao(BaseDao<User> baseDao) {
		super.setBaseDao(baseDao);
	}
	
	/**
	 * ��֤�����Ƿ�ռ��
	 */
	@Override
	public boolean isRegisted(String email) {
		return false;
	}

	/**
	 * ��֤��¼��Ϣ
	 */
	@Override
	public User validateLoginInfo(String email, String pswByMD5) {
		return null;
	}

}
