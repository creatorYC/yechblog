package com.yech.yechblog.entity;


/**
 * ������
 * @author Administrator
 *
 */
public class Comment {
	private Integer id;
	private String content;//����
	private User user;//������
	private String commentTime;//����ʱ��
	private String imageUrl;//���۵�ͼƬ��url
	//��������Ӧ��blog
	private Blog blog;
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
