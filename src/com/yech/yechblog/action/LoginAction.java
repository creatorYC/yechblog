package com.yech.yechblog.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.UserService;
import com.yech.yechblog.util.DataUtil;

/**
 * ��¼Action
 * 
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{

	private static final long serialVersionUID = 6769670387253184703L;

	//�Ƿ��ס����
	private boolean remember;
	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	// ���� session �� map
	private Map<String, Object> sessionMap;
	/**
	 * ע�� UserService
	 */
	@Resource
	private UserService userService;
	
	/**
	 * �����¼����
	 * @return
	 */
	@SkipValidation
	public String toLoginPage(){
		return "loginPage";
	}
	
	/**
	 * ���е�¼����
	 * @return
	 */
	public String doLogin(){
		return "success";
	}
	
	public void validate(){
		User user = 
				userService.validateLoginInfo(model.getEmail(),DataUtil.md5(model.getPassword()));
		if(user == null){
			addActionError("email �� password ����!");
		} else {
			if(isRemember()){ //��ס������
			}
			sessionMap.put("user", user);//�� user ��Ϣ�ŵ�session��
		}
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

}
