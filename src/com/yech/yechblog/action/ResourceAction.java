package com.yech.yechblog.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import com.yech.yechblog.aware.UserAware;
import com.yech.yechblog.entity.Resource;
import com.yech.yechblog.entity.User;
import com.yech.yechblog.service.ResourceService;
import com.yech.yechblog.util.ValidateUtil;

@Controller
@Scope("prototype")
public class ResourceAction extends BaseAction<Resource> implements UserAware,
		ServletContextAware,ServletRequestAware
{

	private static final long serialVersionUID = 2916660768257015198L;

	@javax.annotation.Resource
	private ResourceService resourceService;

	private User user;
	private ServletContext servletContext;
	private HttpServletRequest request;
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	// �����¼ǰ��ҳ�����ڵ�¼������
	private static String originUrl;

	public String getOriginUrl() {
		return originUrl;
	}
	
	//��Դ�б�
	private List<Resource> resList;
	
	public List<Resource> getResList() {
		return resList;
	}

	public void setResList(List<Resource> resList) {
		this.resList = resList;
	}

	/**
	 * ȥ���ϴ���Դҳ��
	 * 
	 * @return
	 */
	public String toUploadPage() {
		// ֻ�о�̬�������ڵ�¼��ص�֮ǰ��ҳ�棬��Ȼ originUrl ��Ϊnull
		originUrl = request.getHeader("referer");
		originUrl = originUrl.substring(originUrl.lastIndexOf("/")+1);
		return "toUploadPage";
	}

	private File userRes; // �û��ϴ�����Դ
	private String userResFileName; // �ļ����������� FileName ��β

	public File getUserRes() {
		return userRes;
	}

	public void setUserRes(File userRes) {
		this.userRes = userRes;
	}

	public String getUserResFileName() {
		return userResFileName;
	}

	public void setUserResFileName(String userResFileName) {
		this.userResFileName = userResFileName;
	}

	/**
	 * �ϴ���Դ
	 * @return
	 */
	public String uploadResource() {
		if (ValidateUtil.isValidate(userResFileName)) { // �ļ����Ƿ���Ч
			// �õ� uplode �ļ����ڷ������ϵ���ʵ·��
			String dir = servletContext.getRealPath("/upload");
			// �Ե�ǰ�û����û���+id����Ϊ�ļ���������ÿ���û��ϴ�����Դ�����ڵ������ļ�����
			String userResPath = dir + File.separator + user.getUsername() + user.getId()
					+ File.separator;
			File userFolder = new File(userResPath);
			if (!userFolder.exists()) { // ����ļ��в����ھʹ����ļ���
				userFolder.mkdir();
			} // ����ļ��д��ھ��ϴ��ļ�
				// �ļ���չ��
			String ext = userResFileName.substring(userResFileName.lastIndexOf("."));
			// ����ʱ����Ϊ�ļ���(��ֹ����)
			long l = System.nanoTime();
			// ���ļ�·��
			File newFile = new File(userFolder, l + ext);
			try {
				FileUtils.copyFile(userRes, newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			model.setResName(userResFileName);
			model.setDownloadCount(0);//��ʼ���ش���Ϊ0
			model.setResSuffix(ext);//������Դ��׺��
			model.setResUploadTime(format.format(new Date()));
			model.setResPath("/upload/" + user.getUsername() + user.getId() + "/" + l + ext);
			model.setUploadUser(user);
			resourceService.saveResource(model);
		}
		return "keepOriginUrl";
	}
	
	/**
	 * ȥ��Դ����ҳ��
	 * @return
	 */
	public String toResPage(){
		resList = resourceService.queryAllResources();
		return "toResPage";
	}
	public Integer rid;//����ҳ�洫������resource��id
	private String contentType;//���صĽ������
	private long contentLength;//���ص��ļ�����
	//����Ӧͷָ����Ӧ���ļ��������ͣ�һ��ȡֵΪattachment;filename=""
	private String contentDisposition;
	private InputStream inputStream;
	
	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getContentType() {
		return contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * ������Դ
	 * @return
	 */
	public String downloadResource(){
		model = resourceService.getResourceById(rid);
		String filename = servletContext.getRealPath(model.getResPath());
		String downName;
		try {
			//Ϊ�ļ������룬��ֹ��������
			downName = URLEncoder.encode(model.getResName(), "utf-8");
			contentType = "application/octet-stream;charset=utf-8";
			contentDisposition = "attachment;filename="+downName;
			inputStream = new FileInputStream(filename);
			if(inputStream != null){ //���������
				model.setDownloadCount(model.getDownloadCount()+1);//������+1
				resourceService.updateResource(model);//�������ݿ��д���Դ
				contentLength = inputStream.available();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
