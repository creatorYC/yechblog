package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.User;

public interface UserService extends BaseService<User> {

	/**
	 * 判断 email 是否被占用
	 */
	public boolean isRegisted(String email);
	
	/**
	 * 验证登录信息
	 */
	public User validateLoginInfo(String email,String pswByMD5);

	/**
	 * 上传头像
	 * @param string
	 */
	public void updateUserImgPath(Integer id, String string);

	/**
	 * 根据好友名查询好友
	 * @return
	 */
	public List<User> searchUserByName(String friendName);

	/**
	 * 经过了邮箱验证
	 * @param model
	 */
	public void setValidate(User model);

	/**
	 * 统计注册的总人数
	 * @return
	 */
	public int getRegistedUserNums();

	/**
	 * 根据注册邮箱查询用户(用于重置密码)
	 * @param email
	 */
	public User queryUserByEmail(String email);
}
