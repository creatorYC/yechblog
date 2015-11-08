package com.yech.yechblog.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * ����(�ʴ�����)
 * 
 * @author Administrator
 *
 */
public class Question {

	private Integer id;
	private String title;
	private String content;
	private String createTime;
	private Integer readCount;// �����������
	private Boolean deleted;// �Ƿ�ɾ��
	private String category;// ������������

	// ���⵽�𰸵�һ�Զ�ӳ��
	private Set<Answer> answers = new HashSet<Answer>();

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	private User user;// ������

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
