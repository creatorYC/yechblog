package com.yech.yechblog.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yech.yechblog.entity.Blog;
import com.yech.yechblog.entity.FeedBack;
import com.yech.yechblog.entity.Question;
import com.yech.yechblog.entity.Tag;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.BlogService;
import com.yech.yechblog.service.FeedBackService;
import com.yech.yechblog.service.QuestionService;
import com.yech.yechblog.service.UserService;
import com.yech.yechblog.util.AddressUtil;
import com.yech.yechblog.util.DataUtil;
import com.yech.yechblog.util.Global;

/**
 * ��¼Action
 * 
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware,
		ApplicationAware, ServletRequestAware,ServletResponseAware
{

	private static final long serialVersionUID = 6769670387253184703L;

	// �Ƿ��ס����
	private String remember;

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	// ���� session �� map
	private Map<String, Object> sessionMap;
	// ���� application ��map
	private Map<String, Object> application;

	// �û���ַ(����ip�õ��ĵ�ַ) Map<"ip","��ַ(����ʡ����)">
	private Map<String, String> userAddr = new HashMap<String, String>();
	// �����û���Ϣ(Map<"�û���",Map<"ip","��ַ">>)
	private static Map<String, Map<String, String>> userInfo = new HashMap<String, Map<String, String>>();
	private HttpServletRequest request;
	private HttpServletResponse response;
	// ��ĳ��ǩ�Ĳ������� Map<"��ǩ��",��������>
	private Map<String, Integer> blogNumsWithTag = new HashMap<String, Integer>();
	// ����ĳ������������� Map<"������",��������>
	private Map<String, Integer> questionNumsWithTag = new HashMap<String, Integer>();
	// �����б�
	private List<FeedBack> feedBackList;
	
	public List<FeedBack> getFeedBackList() {
		return feedBackList;
	}

	public void setFeedBackList(List<FeedBack> feedBackList) {
		this.feedBackList = feedBackList;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	@Override
	public void setApplication(Map<String, Object> arg0) {
		this.application = arg0;
	}

	/**
	 * ע�� UserService
	 */
	@Resource
	private UserService userService;

	@Resource
	private BlogService blogService;

	@Resource
	private QuestionService questionService;

	@Resource
	private FeedBackService feedBackService;
//	// �����¼ǰ��ҳ�����ڵ�¼������
	private static String originUrl;

	public String getOriginUrl() {
		return originUrl;
	}

	// public void setOriginUrl(String originUrl) {
	// this.originUrl = originUrl;
	// }
	/**
	 * �����¼����
	 * 
	 * @return
	 */
	@SkipValidation
	public String toLoginPage() {
		// ֻ�о�̬�������ڵ�¼��ص�֮ǰ��ҳ�棬��Ȼ originUrl ��Ϊnull
		originUrl = request.getHeader("referer");
		originUrl = originUrl.substring(originUrl.lastIndexOf("/")+1);
		return "loginPage";
	}

	/**
	 * ���е�¼����
	 * 
	 * @return
	 */
	public String doLogin() {
		if(Global.originUrl != ""){ //��Ϊ""˵�������˵�¼�������������ض��򵽵�¼ҳ���
			originUrl = Global.originUrl;
			Global.originUrl="";//�����ÿ�
		}
		// �õ�����ͷ�е� referer �ֶΣ����ڵ�¼����ת��֮ǰ��ҳ��
		return "keepOriginUrl";
	}

	/**
	 * ȥ�����ݷ���ҳ��
	 * 
	 * @return
	 */
	@SkipValidation
	public String toDataAnalysePage() {
		getBlogNumsWithTag();
		getQuestionNumsWithCategory();
		feedBackList = feedBackService.findAllFeedBacks();
		int registedUserNums = userService.getRegistedUserNums();
		application.put("registedUserNums", registedUserNums);
		application.put("blogNumsWithTag", blogNumsWithTag);
		application.put("questionNumsWithTag", questionNumsWithTag);
		if (application.get("count") == null
				|| (Integer) (application.get("count")) == 0) {
			application.put("count", 0);
		}
		return "dataAnalysePage";
	}

	/**
	 * �õ�����ĳ����ǩ�Ĳ��͵�����(���ڽ������ݷ���)
	 * @return
	 */
	@SkipValidation
	public void getBlogNumsWithTag() {
		List<Blog> blogs = blogService.findAllBlogs();// �õ�����blog
		// �õ�����ĳ����ǩ�Ĳ��͵�����(���ڽ������ݷ���)
		for (int i = 0; i < blogs.size(); i++) {
			// �õ��˲��͵ı�ǩ
			List<Tag> tags = new ArrayList<Tag>(blogs.get(i).getTags());
			for (int j = 0; j < tags.size(); j++) {
				// map���Ѿ��д˴α�������tag,ֱ�ӽ����д�tag�Ĳ�����+1
				if (blogNumsWithTag.keySet().contains(tags.get(j).getTagName())) {
					Integer num = blogNumsWithTag.get(tags.get(j).getTagName());
					blogNumsWithTag.put(tags.get(j).getTagName(), ++num);
				} else { // map���治���˴α�������tag,���ú��д˱�ǩ�Ĳ�����Ϊ1
					blogNumsWithTag.put(tags.get(j).getTagName(), 1);
				}
			}
		}
		JSONObject object = new JSONObject(blogNumsWithTag);
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(object);
			System.out.println("key= "+object);
		} catch (Exception e) {
		}
	}

	/**
	 * �õ�����ĳ��������������(���ڽ������ݷ���)
	 */
	@SkipValidation
	public void getQuestionNumsWithCategory() {
		List<Question> questions = questionService.queryAllQuestions();
		for (int i = 0; i < questions.size(); i++) {
			// map���Ѿ��д˴α����õ���question�ķ���,ֱ�ӽ����д˷����������+1
			if (questionNumsWithTag.keySet().contains(
					questions.get(i).getCategory())) {
				Integer num = questionNumsWithTag.get(questions.get(i)
						.getCategory());
				questionNumsWithTag.put(questions.get(i).getCategory(), ++num);
			} else {
				// map���治���˴α�������question�ķ���,���ú��д˴˷����������Ϊ1
				questionNumsWithTag.put(questions.get(i).getCategory(), 1);
			}
		}
		JSONObject object = new JSONObject(questionNumsWithTag);
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(object);
			System.out.println("key= "+object);
		} catch (Exception e) {
		}
	}

	/**
	 * �˳���¼
	 */
	@SkipValidation
	public String logout() {
		// 1����������-1����ȡ������������������>0����-1
		Integer count = (Integer) application.get("count");
		User user = (User) sessionMap.get("user");
		if (count != null && count > 0) {
			count--;
			application.put("count", count);
			// �˳���¼ʱ�Ƴ����û�
			userInfo.remove(user.getUsername());
		}
		//��Cookie�ÿ�
		Cookie emailCookie = new Cookie("email", "");
		Cookie pswCookie = new Cookie("password", "");
		response.addCookie(emailCookie);
		response.addCookie(pswCookie);
		sessionMap.clear();
		// 2��sessionʧЧ��ǿתΪSessionMap������invalidate����
		((SessionMap<String, Object>) sessionMap).invalidate();
		Global.user = null;
		// ֻ�о�̬�������ڵ�¼��ص�֮ǰ��ҳ�棬��Ȼ originUrl ��Ϊnull
		originUrl = request.getHeader("referer");
		originUrl = originUrl.substring(originUrl.lastIndexOf("/")+1);
		return "keepOriginUrl";
	}

	public void validate() {
		User user = userService.validateLoginInfo(model.getEmail(),
				DataUtil.md5(model.getPassword()));
		if (user == null) {
			addActionError("email �� password ����!�����˺�δ����!");
		} else {
			// "on"����checkbox��ѡ�У���ס������,����Ϣ���浽 cookie ��,����Ϊ null
			if ("on".equals(getRemember())) { 
				Cookie emailCookie = new Cookie("email", model.getEmail()); //����email
				Cookie pswCookie = 
						new Cookie("password", DataUtil.md5(model.getPassword()));//��������
				emailCookie.setMaxAge(10 * 24 * 60 * 60);//��Ч��Ϊ10��
				pswCookie.setMaxAge(10 * 24 * 60 * 60);
				response.addCookie(emailCookie);
				response.addCookie(pswCookie);
			}
			// 1����ȡ��ǰ��������������application�л�ȡ
			Integer count = (Integer) application.get("count");
			if (count == null) {
				count = 0;
			}
			// 2��ʹ��ǰ����������+1
			count++;
			String remoteAddr = request.getRemoteAddr() == null ? "" : request
					.getRemoteAddr();
			try {
				// ��map�м���ip��Ӧ�ĵ�ַ
				userAddr.clear();// �����
				userAddr.put(remoteAddr,
						AddressUtil.getAddresses(remoteAddr, "utf-8"));
				// ��ӵ�userInfo��map��
				userInfo.put(user.getUsername(), userAddr);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			application.put("userInfo", userInfo);
			application.put("count", count);
			sessionMap.put("user", user);// �� user ��Ϣ�ŵ�session��
			Global.user = user;
		}
	}

}
