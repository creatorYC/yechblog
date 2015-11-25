package com.yech.yechblog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.UserService;
import com.yech.yechblog.util.ValidateUtil;

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
		String hql = "from User u where u.email = ?";
		List<User> list = this.batchFindEntityByHQL(hql, email);
		return ValidateUtil.isValidate(list);
	}

	/**
	 * ��֤��¼��Ϣ
	 */
	@Override
	public User validateLoginInfo(String email, String pswByMD5) {
		String hql = "FROM User u WHERE u.email = ? AND u.password = ? and u.status = 1";
		List<User> list = batchFindEntityByHQL(hql, email,pswByMD5);
		return ValidateUtil.isValidate(list)?list.get(0):null;
	}

	/**
	 * �ϴ�ͷ��
	 */
	@Override
	public void updateUserImgPath(Integer id, String path) {
		String hql = "UPDATE User u SET u.image = ? WHERE u.id = ?";
		this.batchUpdateEntityByHQL(hql,path,id);
	}

	/**
	 * ���ݺ�������ѯ����
	 * @return
	 */
	@Override
	public List<User> searchUserByName(String friendName) {
		String hql = "from User u where u.username like ?";
		List<User> friends = this.batchFindEntityByHQL(hql, "%"+friendName+"%");
		return friends;
	}

	/**
	 * ������������֤
	 */
	@Override
	public void setValidate(User model) {
		String hql = "update User u set u.status = 1,u.validateCode = ? "
				+ "where u.id = ?";
		this.batchUpdateEntityByHQL(hql, "",model.getId());
	}

	/**
	 * ͳ��ע���������
	 * @return
	 */
	@Override
	public int getRegistedUserNums() {
		String hql = "select count(*) from User u where u.status = 1";
		return  ((Long)(this.uniqueResult(hql))).intValue();
	}

	/**
	 * ����ע�������ѯ�û�(������������)
	 * @param email
	 */
	@Override
	public User queryUserByEmail(String email) {
		String hql = "from User u where u.email = ?";
		return this.batchFindEntityByHQL(hql, email).get(0);
	}
}
