package com.it.zzg.modules.flow.service;

import com.it.zzg.modules.flow.entity.ListEntity;

import java.util.List;
import java.util.Map;

/**
 * 流程表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
public interface ListService {
	
	ListEntity queryObject(String flowCode);
	
	List<ListEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ListEntity list);
	
	void update(ListEntity list);
	
	void delete(String flowCode);
	
	void deleteBatch(String[] flowCodes);
}
