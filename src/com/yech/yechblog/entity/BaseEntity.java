package com.yech.yechblog.entity;

import java.io.Serializable;
/**
 * ʵ����Ļ���
 * @author Administrator
 *
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -1426086199605140928L;

	public abstract Integer getId();

	public abstract void setId(Integer id);

}