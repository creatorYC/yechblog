package com.yech.yechblog.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.UserService;
import com.yech.yechblog.util.DataUtil;
import com.yech.yechblog.util.SendMailUtil;
import com.yech.yechblog.util.ValidateUtil;

@Controller
@Scope("prototype")
public class RegistAction extends BaseAction<User> implements ServletRequestAware{
	
	private static final long serialVersionUID = 3871358626902449552L;
	
	/**
	 * ע�� UserService
	 */
	@Resource
	private UserService userService;

	private HttpServletRequest request;
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

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
		model.setImage("/image/personImg.jpg");//����Ĭ��ͼƬ
		model.setStatus(false);//����û����֤
		model.setValidateCode(DataUtil.md5("VaLiDaTeCoDe"));
		userService.saveEntity(model);
		sendMessage();//������֤�ʼ�
		return "BlogAction";//�ض��������ҳ�����б�
	}
	
	private String email;
	private String vcode;
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	/**
	 * ����������֤
	 * @return
	 */
	public String doMessageValidate(){
		model = userService.getEntity(userId);
		//��֤�����˺ţ��޸��˺�״̬  
		if(vcode.equals(model.getValidateCode())){
			model.setStatus(true);
			model.setValidateCode("");
			userService.setValidate(model);//����statusΪ1��validateΪ""
			request.setAttribute("msg", "<h3>����ɹ�������Ϊ����ת��yechblog��ҳ</h3>");
		} else {
			request.setAttribute("msg", "<h3>�Ѿ��������,��Ҫ�ظ�����!</h3>");
			System.out.println("�Ѿ��������,��Ҫ�ظ�����!");
		}
		return "temp";
	}
	/**
	 * ������֤�ʼ�
	 * @return
	 */
	public void sendMessage(){
		if(model != null && !model.getStatus()){ //û����֤
			String email = model.getEmail().trim();
			String code = DataUtil.md5("VaLiDaTeCoDe");
			StringBuffer content = new StringBuffer("�װ���"+model.getUsername()+" :����!<br>");
			 content.append("&nbsp;&nbsp;&nbsp;&nbsp;��л��ע�� yechblog����ֻҪ�����������ӣ����������˺ţ���������� yechblog �������")
			 .append("<br><br>")
//			 .append("<a style='font-size:16px;' "
//					 	+ "href='http://localhost:8080/yechblog/RegistAction_doMessageValidate?")
//			 .append("email=" + email + "&vcode=" + code+"&userId="+model.getId() +"'>")
			 .append("<span style='font-size:16px;'>")
			 .append("http://114.215.92.22:8080/yechblog/RegistAction_doMessageValidate?email="+ email + "&vcode=" + code+"&userId="+model.getId())
			 .append("<span>")
			 .append("<br><br><br><br>")
			 .append("&nbsp;&nbsp;������ֻ�ܼ���һ�Σ��뾡�켤��!");
			 SendMailUtil.send(email, content.toString());//��ʼ�����ʼ�
		}
	}
	//����ҳ�洫��������֤��
	private String identifyCode;
	
	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	/**
	 * У��(��д ActionSupport ����ķ�������У��)
	 */
	public void validateDoRegist(){
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
		if(!identifyCode.toLowerCase().equals(((String)(ActionContext.getContext().getSession().get("code"))).toLowerCase())){
			addFieldError("identifyCode", "��֤�벻��!");
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
