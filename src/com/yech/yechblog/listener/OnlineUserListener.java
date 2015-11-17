package com.yech.yechblog.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ������������
 * @author Administrator
 */
public class OnlineUserListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		System.out.println("sessionCreated---sessionId= "+sessionEvent.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		System.out.println("sessionDestroyed---sessionId= "+sessionEvent.getSession().getId());
	}

}
