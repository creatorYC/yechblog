package com.yech.yechblog.entity;

import java.util.Date;

/**
 * ������
 * @author Administrator
 *
 */
public class Blog{

	// ID
	private Integer id;

	// ��������
	private String content;

	// ���±���
	private String title;

	// ���·���
	private Integer category;

	// ����ʱ��
	private Date createTime;
	// ���� Blog �� User ֮�� ���һ ������ϵ
	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	// �Ƿ���ɾ��,Ĭ��0��δɾ��,1��ʾɾ��
	private Integer deleted;

	// �Ķ�����
	private Integer readCount;

}
