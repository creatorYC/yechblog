package com.yech.yechblog.util;

import java.security.MessageDigest;

/**
 * ���ݹ�����
 * @author Administrator
 *
 */
public class DataUtil {
	
	/**
	 * ʹ�� MD5 �㷨���м���
	 * @param src
	 * @return
	 */
	public static String md5(String src){
		try {
			StringBuffer buffer = new StringBuffer();
			char[] chars = 
				{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
			byte[] bytes = src.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] targ = md.digest(bytes);//targ Ϊ16���ֽڳ���
			
			for(byte b : targ){
				//һ���ֽ�8λ������4λȡ��4λ
				buffer.append(chars[(b>>4) & 0x0F]);
				//ȡ��4λ
				buffer.append(chars[b & 0x0F]);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
