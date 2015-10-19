package com.yech.yechblog.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yech.yechblog.entity.IdentifyCode;

/**
 * ��֤��
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class IdentifyCodeAction extends ActionSupport {

	private static final long serialVersionUID = -4532811499638836430L;

	private ByteArrayInputStream inputStream;

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String execute() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();

		// �����������Ҫ�����ͼƬ
		response.setHeader("Pragma", "no-cache");

		response.setHeader("Cache-Control", "no-cache");

		response.setDateHeader("Expires", 0);

		IdentifyCode code = new IdentifyCode();
		this.setInputStream(code.getIdentifyCodeImage());
		// ����֤�����SESSION
		ActionContext.getContext().getSession()
				.put("code", code.getCheckCode());
		return SUCCESS;
	}

}
