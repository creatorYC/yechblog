package com.yech.yechblog.entity;


/**
 * Message : ��Ϣ�࣬���ڶ�̬����
 * @author Administrator
 *
 */
public class Message {

	private Integer id;
	private String content;
	private User self;	//��Ϣ����˭
	private User other; //��Ϣ����˭
	private Boolean status; //״̬ 0�Ѷ� 1δ��
	private Blog blog;//��Ϣ�ǹ�����ƪ����
	private String createTime;
	
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
