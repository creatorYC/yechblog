package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.User;

public interface UserService extends BaseService<User> {

	/**
	 * �ж� email �Ƿ�ռ��
	 */
	public boolean isRegisted(String email);
	
	/**
	 * ��֤��¼��Ϣ
	 */
	public User validateLoginInfo(String email,String pswByMD5);

	/**
	 * �ϴ�ͷ��
	 * @param string
	 */
	public void updateUserImgPath(Integer id, String string);

	/**
	 * ���ݺ�������ѯ����
	 * @return
	 */
	public List<User> searchUserByName(String friendName);
}
