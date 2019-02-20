package com.it.zzg.modules.flow.service;

import com.it.zzg.modules.flow.entity.ExecuteHisEntity;

import java.util.List;
import java.util.Map;

/**
 * 流程执行历史
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
public interface ExecuteHisService {
	
	ExecuteHisEntity queryObject(String node);
	
	List<ExecuteHisEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExecuteHisEntity executeHis);
	
	void update(ExecuteHisEntity executeHis);
	
	void delete(String node);
	
	void deleteBatch(String[] nodes);

	void updateByFormId(String formId);
}
