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
	private Boolean comment=false; //����
	private Boolean love=false;//��
	private Boolean collect=false;//�ղ�
	private Boolean share=false; //����
	private Boolean reply=false;//�ظ�����
	
	public Boolean getReply() {
		return reply;
	}
	public void setReply(Boolean reply) {
		this.reply = reply;
	}
	public Boolean getComment() {
		return comment;
	}
	public void setComment(Boolean comment) {
		this.comment = comment;
	}
	
	public Boolean getLove() {
		return love;
	}
	public void setLove(Boolean love) {
		this.love = love;
	}
	public Boolean getCollect() {
		return collect;
	}
	public void setCollect(Boolean collect) {
		this.collect = collect;
	}
	public Boolean getShare() {
		return share;
	}
	public void setShare(Boolean share) {
		this.share = share;
	}
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
