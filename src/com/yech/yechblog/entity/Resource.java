package com.yech.yechblog.entity;


/**
 * �û��ϴ�����Դ��
 * @author Administrator
 */
public class Resource{
	private Integer id;
	private String resName;//��Դ����
	private String resDesc;//��Դ����
	private String resType;//��Դ����
	private Integer resScore;//��Դ��
	private String resSuffix;//��Դ��׺��
	private Integer downloadCount;//��������
	private String resPath;//��Դ·��
	private String resUploadTime;//��Դ�ϴ�ʱ��
	private User uploadUser;//��Դ���ϴ���
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResDesc() {
		return resDesc;
	}
	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public Integer getResScore() {
		return resScore;
	}
	public void setResScore(Integer resScore) {
		this.resScore = resScore;
	}
	public String getResSuffix() {
		return resSuffix;
	}
	public void setResSuffix(String resSuffix) {
		this.resSuffix = resSuffix;
	}
	public Integer getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getResPath() {
		return resPath;
	}
	public void setResPath(String resPath) {
		this.resPath = resPath;
	}
	public String getResUploadTime() {
		return resUploadTime;
	}
	public void setResUploadTime(String resUploadTime) {
		this.resUploadTime = resUploadTime;
	}
	public User getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(User uploadUser) {
		this.uploadUser = uploadUser;
	}
}
