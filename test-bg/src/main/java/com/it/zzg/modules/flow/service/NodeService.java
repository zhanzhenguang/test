package com.it.zzg.modules.flow.service;

import com.it.zzg.modules.flow.entity.NodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 流程节点表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
public interface NodeService {
	
	NodeEntity queryObjectByOne(String flowCode,String nodeCode);
	
	List<NodeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NodeEntity node);
	
	void update(NodeEntity node);
	
	void delete(String nodeCode);
	
	void deleteBatch(String[] nodeCodes);
}
