package com.yech.yechblog.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * ��ǩ��
 * @author Administrator
 *
 */
public class Tag {

	private Integer id;
	private String tagName;
	private String tagDesc;  //��ǩ������
	private String createTime; //����ʱ��
	private Set<Blog> blogs = new HashSet<Blog>(); //��ǩ������blog
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Set<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}
}
