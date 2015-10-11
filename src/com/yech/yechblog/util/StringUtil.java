package com.yech.yechblog.util;

/**
 * �ַ���������
 * @author Administrator
 *
 */
public class StringUtil {
	
	/**
	 * �ַ�����ֳ�string����
	 * @param str
	 * @return
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValidate(str)){
			return str.split(tag);
		}
		return null;
	}

	/**
	 * �ж��� values �������Ƿ���value�ַ���
	 * @param values
	 * @param value
	 * @return
	 */
	public static boolean contains(String[] values, String value) {
		if(ValidateUtil.isValidate(values)){
			for(String s : values){
				if(s.equals(value)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ������תΪ�ַ�����ʹ�� , �ָ�
	 * @param value
	 * @return
	 */
	public static String arr2Str(Object[] value) {
		String tmp = "";
		if(ValidateUtil.isValidate(value)){
			for(Object s : value){
				tmp = tmp + s + ",";
			}
			return tmp.substring(0,tmp.length()-1);
		}
		return tmp;
	}
}