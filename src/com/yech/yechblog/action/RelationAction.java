package com.yech.yechblog.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.Relation;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.RelationService;
import com.yech.yechblog.service.UserService;
/**
 * RelationAction
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class RelationAction extends BaseAction<Relation> implements UserAware{

	private static final long serialVersionUID = 9162299802568974005L;

	@Resource
	private UserService userService;
	
	@Resource
	private RelationService relationService;
	
	@Resource
	private MessageService messageService;
	private User user;

	@Override
	public void setUser(User user) {
		this.user = user;
	}
	//�����ķ�ʽ����
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}
	
	//����ҳ�洫������userId������Ҫ��ע����
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * ��ӹ�ע
	 * ���� ajax ����
	 * ����ע�����Ϣ�����ķ�ʽ���أ��Ա� ajax �������(struts2 �� ajax���÷�)
	 * ��ע�ɹ����� 1�����򷵻�0
	 * @return
	 */
	public String addFocus(){
		try {
			User other = userService.getEntity(userId);
			//�Ѿ���ע����
			if(!relationService.queryRelation(user,other)){
				model.setSelf(user); 
				model.setOther(other);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				model.setCreateTime(format.format(new Date()));
				relationService.saveRelation(model);
				
				// ���ղ���Ϣ��ӵ� Message ��
				Message message = new Message();
				message.setContent("��ע��");
				message.setSelf(user);
				message.setOther(other);
				//��Ϣ�����ǹ�ע
				message.setStatus(true);// 1����δ��
				message.setCreateTime(format.format(new Date()));
				messageService.saveMessage(message);
				inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
			} else {
				inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
			}
		} catch (Exception e) {
			try {
				inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return "ajax-success";
	}

	
}
