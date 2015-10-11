package com.yech.yechblog.action;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.UserService;
import com.yech.yechblog.util.DataUtil;
import com.yech.yechblog.util.ValidateUtil;

@Controller
@Scope("prototype")
public class RegistAction extends BaseAction<User> {
	
	private static final long serialVersionUID = 3871358626902449552L;
	
	/**
	 * ע�� UserService
	 */
	@Resource
	private UserService userService;

	//ȥ��ע��ҳ��
	@SkipValidation //����У��
	public String toRegistPage(){
		return "registPage";
	}
	
	/**
	 * �����û�ע��
	 * @return
	 */
	public String doRegist(){
		//�������
		model.setPassword(DataUtil.md5(model.getPassword()));
		userService.saveEntity(model);
		return SUCCESS;
	}
	/**
	 * У��(��д ActionSupport ����ķ�������У��)
	 */
	public void validate(){
		//1.email �ǿ�
		String email = model.getEmail();
		if(!ValidateUtil.isValidate(email)){
			addFieldError("email", "email ����Ϊ��!");
		}
		if(!ValidateUtil.isValidate(model.getUsername())){
			addFieldError("username", "username ����Ϊ��!");
		}
		if(!ValidateUtil.isValidate(model.getPassword())){
			addFieldError("password", "password ����Ϊ��!");
		}
		if(hasErrors()){
			return;
		}
		//2.email��ռ��
		if(userService.isRegisted(model.getEmail())){
			addFieldError("email", "email �Ѿ�����!");
		}
	}
}
