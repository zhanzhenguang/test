package com.it.zzg.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.zzg.modules.sys.entity.AppEntity;

/**
 * APP管理
 * 
 * @author admin
 * @email admin
 * @date 2018-07-06 09:41:23
 */
public interface AppService {
	
	AppEntity queryObject(Long id);
	
	List<AppEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppEntity app);
	
	void update(AppEntity app);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<Long> queryAppIdList(Long userId);

	void saveOrUpdate(Long userId, List<Long> appIdList);

	HashMap<String, Object> queryByAppId(HashMap<String, Object> pd);
}
