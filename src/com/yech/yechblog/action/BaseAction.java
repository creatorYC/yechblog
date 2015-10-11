package com.yech.yechblog.action;

import java.lang.reflect.ParameterizedType;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
/**
 * ���� Action�����ڼ̳�
 * @author Administrator
 */
public abstract class BaseAction<T> extends ActionSupport 
							implements ModelDriven<T>,Preparable {

	private static final long serialVersionUID = 1L;
	
	public T model;
	
	/**
	 * ������ ���÷����ʼ�� public ���͵� model���������оͿ���ʡȥ getModel()������
	 */
	@SuppressWarnings("unchecked")
	public BaseAction() {
		ParameterizedType type = 
				(ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
		try {
			model = (T) clazz.newInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void prepare() throws Exception {
		
	}
	
	@Override
	public T getModel(){
		return model;
	}
}
