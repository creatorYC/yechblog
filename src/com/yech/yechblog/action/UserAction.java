package com.yech.yechblog.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.FeedBack;
import com.yech.yechblog.entity.Message;
import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.Relation;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;
import com.yech.yechblog.service.FeedBackService;
import com.yech.yechblog.service.MessageService;
import com.yech.yechblog.service.QuestionService;
import com.yech.yechblog.service.RelationService;
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
public class UserAction extends BaseAction<User> 
					implements ServletContextAware,ServletResponseAware,ServletRequestAware
{

	private static final long serialVersionUID = 3575939345060413099L;

	private File userImg; // �ϴ����ļ�
	private String userImgFileName; // �ļ����������� FileName ��β

	private ServletContext servletContext; // ���� ServletContext ����

	@Resource
	private UserService userService;

	@Resource
	private MessageService messageService;

	@Resource
	private BlogService blogService;
	
	@Resource
	private RelationService relationService;
	
	@Resource
	private QuestionService questionService;
	
	@Resource
	private FeedBackService feedBackService;
	//���Ĳ����б�
	private List<Blog> hisBlogs;
	//����ע����
	private List<Relation> hisRelations;
	//��ע������
	private List<Relation> focusHims;
	//���������б�
	private List<Question> hisQuestions;
	
	public List<Question> getHisQuestions() {
		return hisQuestions;
	}

	public void setHisQuestions(List<Question> hisQuestions) {
		this.hisQuestions = hisQuestions;
	}

	public List<Relation> getFocusHims() {
		return focusHims;
	}

	public void setFocusHims(List<Relation> focusHims) {
		this.focusHims = focusHims;
	}

	public List<Relation> getHisRelations() {
		return hisRelations;
	}

	public void setHisRelations(List<Relation> hisRelations) {
		this.hisRelations = hisRelations;
	}

	public List<Blog> getHisBlogs() {
		return hisBlogs;
	}

	public void setHisBlogs(List<Blog> hisBlogs) {
		this.hisBlogs = hisBlogs;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

	private HttpServletResponse response;
	private HttpServletRequest request;
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	
	/**
	 * �༭�û���Ϣ
	 */
	public String editInfo() {
		model.setCountry("�й�");
		model.setId(model.getId());
		userService.updateEntity(model);
		return "personalPage";
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
	 * ȥ�������ڡ�ҳ��
	 * @return
	 */
	public String toAboutPage(){
		return "toAboutPage";
	}

	/**
	 * �ϴ�ͷ��
	 * @return
	 */
	public String addImage() {
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
			// �ļ����Ϊ
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
				//���ǹ�ע��answer��׷�ʵ���Ϣ(��Ϊ��Щ��Ϣ����û�ж�Ӧ�Ĳ���)
				if(!message.getFocus() && !message.getAnswer() && !message.getAddAsk()){ 
					message.getBlog().getId();
					//���message����Ӧ��blog
					Blog blog = blogService.getBlogById(message.getBlog().getId());
					message.setBlog(blog);
				}else if(!message.getAnswer() && !message.getAddAsk()){ //����answer��׷�����͵���Ϣ(���ǹ�ע���͵���Ϣ)
					//�� message ���е� status �ֶ���Ϊ 0 ����ʾ�Ѷ�
					messageService.changeMessageStatus(message.getId());
				} else { // ��answer���͵���Ϣ
					Question question = 
							questionService.getQuestionById(message.getQuestion().getId());
					message.setQuestion(question);
				}
			}
		}
		if (oldMessages.size() > 0) {
			for (Message message : oldMessages) {
				if(!message.getFocus() && !message.getAnswer() && !message.getAddAsk()){
					//���message����Ӧ��blog
					Blog blog = blogService.getBlogById(message.getBlog().getId());
					message.setBlog(blog);
				} else if(message.getAnswer() || message.getAddAsk()){
					Question question = 
							questionService.getQuestionById(message.getQuestion().getId());
					message.setQuestion(question);
				}
			}
		}
		return "toMessageCenterPage";
	}
	
	//����ҳ�洫�������û�id������id����������ҳ
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	//Ҫ��user����ҳ
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * ȥ���˵���ҳ
	 * @return
	 */
	public String toOtherHomePage(){
		user = userService.getEntity(userId);
		if(model.getId() != userId){	//��������ҳ���ǵ�ǰ��¼���û�����ҳ
			hisBlogs = blogService.queryHisBlogs(userId);
			hisRelations = relationService.queryAllRelations(user);
			focusHims = relationService.queryAllMyFocus(user);
			hisQuestions = questionService.queryHisQuestions(userId);
			return "toOtherHomePage";
		} else{	//Ҫ������ҳ�ǵ�ǰ��¼�û�����ҳ(�����Լ�����ҳ)
			//�ض��򵽸�����ҳ
			return "redirectToPersonalPage";
		}
	}
	
	//����ҳ�洫������ֵ
	private String friendName;
	//�����б�
	private List<User> friendsList;
	
	public List<User> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<User> friendsList) {
		this.friendsList = friendsList;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	/**
	 * �������ѣ���������ĺ�������
	 * @return
	 */
	public void searchFriends(){
		friendsList = userService.searchUserByName(friendName);
		JSONArray array = new JSONArray(friendsList);
		try {
			response.getWriter().print(array);
			System.out.println("array= "+array);
		} catch (Exception e) {
		}
//		return "ajax-success";
	}
	
	public String originUrl;
	public String getOriginUrl() {
		return originUrl;
	}
	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}
	
	//���շ���ҳ��ķ�������
	private String fbContent;
	
	public String getFbContent() {
		return fbContent;
	}

	public void setFbContent(String fbContent) {
		this.fbContent = fbContent;
	}

	/**
	 * �û�����
	 */
	public String feedBack(){
		model = userService.getEntity(userId);
		originUrl = request.getHeader("referer");
		// http://localhost:8080/yechblog/BlogAction_pagination.action
		originUrl = 
				originUrl.substring(originUrl.lastIndexOf("/")+1);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		FeedBack feedBack = new FeedBack();
		feedBack.setContent(fbContent);
		feedBack.setState(false); //��ʼΪΪ����״̬
		feedBack.setUser(model);
		feedBack.setFeedBackTime(format.format(new Date()));
		feedBackService.saveFeedBack(feedBack); //����feedBack
		return "keepOriginUrl";
	}

}
