package com.yech.yechblog.entity;

import java.sql.Timestamp;

/**
 * �û���
 * @author Administrator
 *
 */
public class User{
	private Integer id;
	private String email;
	private String username;
	private String password;
	private String nickName; //�ǳ�
	private String notes;//���˱�ע
    private String image;//ͼƬURL
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	//ע��ʱ�䲻�ɸ�
	private Timestamp registerDate;
	//���һ�ε�¼ʱ��
	 private Timestamp recentLoginTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Timestamp getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}
	public Timestamp getRecentLoginTime() {
		return recentLoginTime;
	}
	public void setRecentLoginTime(Timestamp recentLoginTime) {
		this.recentLoginTime = recentLoginTime;
	}
}
