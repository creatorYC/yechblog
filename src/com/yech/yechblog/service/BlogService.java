package com.yech.yechblog.service;

import java.util.List;

import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.User;

/**
 * 
 * @author Administrator
 *
 */
public interface BlogService{

	/**
	 * �������в���
	 * @param user 
	 */
	public List<Blog> batchFindEntityByHQL(User user);

	/**
	 * �½�/�༭����
	 * @return
	 */
	public void saveOrUpdateBlog(Blog model);

}
