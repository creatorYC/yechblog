package com.yech.yechblog.entity;


/**
 * �ղر�
 * @author Administrator
 *
 */
public class Collection {

	private Integer id;
	private Boolean deleted; //�Ƿ�ɾ����
	private String collectTime;	//�ղص�ʱ��
	
	private Blog blog;
	private User self; //˭�ղص�
	private User other; //�ղ���˭��
	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public User getSelf() {
		return self;
	}
	public void setSelf(User self) {
		this.self = self;
	}
	public User getOther() {
		return other;
	}
	public void setOther(User other) {
		this.other = other;
	}
}
