package com.yech.yechblog.util;

import com.yech.yechblog.entity.User;

public class Global {

	public static User user = null;
	
	/**
	 * ҳ����תǰ��·��(���ڵ�¼����ת�ص�¼ǰ��ҳ��)
	 * ֮����Ҫд��ȫ�־�̬����������Ϊһ��ĵ�¼�Ǿ���LoginAction��toLoginPage���������¼
	 * ҳ��ģ���˿�����toLoginPage�����б����¼ǰ��ҳ��url�����ǻ���һ��������ǽ���д����ʱ��¼
	 * �����������û�û�е�¼��ֱ�ӽ�ҳ���ض��򵽵�¼ҳ��ģ���ʱû�о���toLoginPage������
	 * ����޷������¼ǰ��ҳ��
	 */
	public static String originUrl = "";
}
