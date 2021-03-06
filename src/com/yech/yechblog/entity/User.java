package com.yech.yechblog.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户类
 * @author Administrator
 *
 */
public class User{
	private Integer id;
	private String email;
	private String username;
	private String password;
	private String nickName; //昵称
	private String notes;//个人备注
    private String image;//图片URL
    
    //个人基本信息
    private String career; //职业
    private String birth; //生日
    private Boolean gender;//性别(男1女0)
    private String field; //领域
    private String country;//国家
    private String province;
    private String city;
    
    public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	private Boolean status;//是否验证
    private String validateCode;//验证的验证码，保证只能激活链接只能验证一次
    
    public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	private List<Message> messages = new ArrayList<Message>();
    
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	//注册时间不可改
	private Timestamp registerDate = new Timestamp(System.currentTimeMillis());
	//最近一次登录时间
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
