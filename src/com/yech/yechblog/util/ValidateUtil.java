package com.yech.yechblog.util;

import java.util.Collection;

/**
 * У�鹤����
 * @author Administrator
 *
 */
public class ValidateUtil {
	
	/**
	 * �ж��ַ����Ƿ���Ч
	 * @param src
	 * @return
	 */
	public static boolean isValidate(String src){
		if(src == null || "".equals(src.trim())){
			return false;
		}
		return true;
	}
	
	/**
	 * �жϼ��ϵ���Ч��
	 * @param collection
	 * @return
	 */
	public static boolean isValidate(@SuppressWarnings("rawtypes") Collection collection){
		if(collection == null || collection.isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * �ж������Ƿ���Ч
	 * @param arr
	 * @return
	 */
	public static boolean isValidate(Object[] arr){
		if(arr == null || arr.length == 0){
			return false;
		}
		return true;
	}
}
