package com.yech.yechblog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yech.yechblog.dao.BaseDao;
import com.yech.yechblog.entity.Resource;
import com.yech.yechblog.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@javax.annotation.Resource(name="resourceDao")
	private BaseDao<Resource> resourceDao;
	
	/**
	 * ����Resource
	 */
	@Override
	public void saveResource(Resource model) {
		resourceDao.saveEntity(model);
	}
	
	/**
	 * ��ѯ������Դ
	 * @return
	 */
	@Override
	public List<Resource> queryAllResources() {
		String hql = "from Resource";
		List<Resource> resources = resourceDao.batchFindEntityByHQL(hql);
		for(Resource resource : resources){
			resource.getUploadUser().getUsername();
		}
		return resources;
	}

	/**
	 * ������Դid������Դ
	 */
	@Override
	public Resource getResourceById(Integer rid) {
		return resourceDao.getEntity(rid);
	}

	/**
	 * ������Դ
	 */
	@Override
	public void updateResource(Resource model) {
		resourceDao.updateEntity(model);
	}

}
