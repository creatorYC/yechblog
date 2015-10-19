package com.yech.yechblog.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * ��֤�빤����
 * 
 * @author Administrator
 *
 */
public class IdentifyCode {

	public static int WIDTH = 130;
	public static int HEIGHT = 30;

	private String checkCode="";// ��֤��

	ByteArrayInputStream input=null;
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * ��ȡ ��֤�� ͼƬ
	 * 
	 * @return
	 */
	public ByteArrayInputStream  getIdentifyCodeImage() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 1.���ñ���ɫ
		setBackground(g);
		// 2.���ñ߿�
		setBorder(g);
		// 3.��������
		drawRandomLine(g);
		// 4.д�����
		drawRandomNumber((Graphics2D) g);
		
		//ͼ����Ч  
        g.dispose();  
         
        ByteArrayOutputStream output = new ByteArrayOutputStream();  
        try{  
            ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);  
            ImageIO.write(image, "JPEG", imageOut);  
            imageOut.close();  
            input = new ByteArrayInputStream(output.toByteArray());  
        }catch(Exception e){  
            System.out.println("��֤��ͼƬ�������ִ���"+e.toString());  
        }  
		return input;
	}

	private void drawRandomNumber(Graphics2D g) {

		g.setColor(Color.RED);
		g.setFont(new Font("����", Font.BOLD, 25));
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		int x = 5;
		for (int i = 0; i < 4; i++) {
			int degree = new Random().nextInt() % 30;// �õ�-30��30�������
			String ch = base.charAt(new Random().nextInt(base.length())) + "";
			// ��¼��������֤��
			checkCode += ch;
			g.rotate(degree * Math.PI / 180, x, 20);// ��ת�Ļ���
			g.drawString(ch, x, 20);
			g.rotate(-degree * Math.PI / 180, x, 20);// ��ת��Ϊ֮ǰ�Ƕ�
			x += 30;
		}
	}

	private void drawRandomLine(Graphics g) {
		g.setColor(Color.GREEN);
		for (int i = 0; i < 20; i++) {
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);

			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	private void setBorder(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
	}

	private void setBackground(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
}
