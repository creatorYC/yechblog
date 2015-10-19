package com.yech.yechblog.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.yech.yechblog.action.BaseAction;
import com.yech.yechblog.action.IdentifyCodeAction;
import com.yech.yechblog.action.LoginAction;
import com.yech.yechblog.action.RegistAction;
import com.yech.yechblog.action.UserAction;
import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.User;

/**
 * ��¼������
 * @author Administrator
 *
 */
public class LoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -113465086676835611L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		
		//������֤���Action,ֱ�ӷ���
		if(arg0.getAction() instanceof IdentifyCodeAction){
			return arg0.invoke();
		}
		
		BaseAction action = (BaseAction) arg0.getAction();
		if(action instanceof LoginAction
				|| action instanceof RegistAction ){
			//������ע��͵�¼action��ֱ�ӷ���
			return arg0.invoke();
		}
		//ȡ�� session �е� user ��Ϣ
		User user = (User) arg0.getInvocationContext().getSession().get("user");
		if(user == null){
			//ȥ��¼
			return "login";
		} else {
			//��� action ʵ���� UserAware �ӿ�,��Ϊ�� action ע�� user
			if(action instanceof UserAware){
				((UserAware)action).setUser(user);
				return arg0.invoke();
			} else if(action instanceof UserAction){
				action.model = user;
				return arg0.invoke();
			}
		}
		return null;
	}

}
