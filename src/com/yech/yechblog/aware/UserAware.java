package com.yech.yechblog.aware;

import com.yech.yechblog.entity.User;


/**
 *	Ϊ��ҪUser��Ϣ���ഫ��User����
 */
public interface UserAware {
	public void setUser(User user);
}
