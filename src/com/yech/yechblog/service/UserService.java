package com.yech.yechblog.service;

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
}
