package com.yech.yechblog.entity;

import java.sql.Timestamp;

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
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
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
