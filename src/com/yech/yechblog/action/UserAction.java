package com.yech.yechblog.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.UserService;
import com.yech.yechblog.util.ValidateUtil;

/**
 * UserAction
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> implements ServletContextAware
{

	private static final long serialVersionUID = 3575939345060413099L;

	private File userImg; // �ϴ����ļ�
	private String userImgFileName; // �ļ����������� FileName ��β

	private ServletContext servletContext; // ���� ServletContext ����

	@Resource
	private UserService userService;

	@Resource
	private MessageService messageService;

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

	/**
	 * �༭�û���Ϣ
	 * 
	 * @return
	 */
	public String editInfo() {
		return "";
	}

	public File getUserImg() {
		return userImg;
	}

	public void setUserImg(File userImg) {
		this.userImg = userImg;
	}

	public String getUserImgFileName() {
		return userImgFileName;
	}

	public void setUserImgFileName(String userImgFileName) {
		this.userImgFileName = userImgFileName;
	}

	/**
	 * �ϴ�ͷ��
	 * 
	 * @return
	 */
	public String addImage() {
		System.out.println("imageFileName = " + userImgFileName);
		if (ValidateUtil.isValidate(userImgFileName)) { // �ļ����Ƿ���Ч
			// 1.ʵ���ϴ�
			// �õ� uplode �ļ����ڷ������ϵ���ʵ·��
			String dir = servletContext.getRealPath("/upload");
			// �ļ���չ��
			String ext = userImgFileName.substring(userImgFileName
					.lastIndexOf("."));
			// ����ʱ����Ϊ�ļ���(��ֹ����)
			long l = System.nanoTime();
			// ���ļ�·��
			File newFile = new File(dir, l + ext);
			System.out.println(",,," + newFile);
			// �ļ�����Ϊ
			// userImg.renameTo(newFile);
			try {
				FileUtils.copyFile(userImg, newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 2.����·��
			userService.updateUserImgPath(model.getId(), "/upload/" + l + ext);
			model.setImage("/upload/" + l + ext);
		}
		return "personalPage";
	}

	// �µ���Ϣ
	private List<Message> newMessages;
	// �Ѷ����ľ���Ϣ
	private List<Message> oldMessages;

	public List<Message> getOldMessages() {
		return oldMessages;
	}

	public void setOldMessages(List<Message> oldMessages) {
		this.oldMessages = oldMessages;
	}

	public List<Message> getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(List<Message> newMessages) {
		this.newMessages = newMessages;
	}

	/**
	 * ȥ����Ϣ����ҳ��
	 * 
	 * @return
	 */
	public String toMessageCenter() {
		newMessages = model.getMessages();
		oldMessages = messageService.getOldMessages(model); // ��ȡ�Ѿ������ľ���Ϣ
		if (newMessages.size() > 0) {
			for (Message message : newMessages) {
				message.getBlog().getId();
			}
		}
		if (oldMessages.size() > 0) {
			for (Message message : oldMessages) {
				System.out.println("om=" + message.getId());
				message.getBlog().getId();
			}
		}
		return "toMessageCenterPage";
	}
}