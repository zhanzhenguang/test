package com.it.zzg.modules.flow.service;

import com.it.zzg.common.utils.R;
import com.it.zzg.modules.flow.entity.NodeInstanceEntity;
import com.it.zzg.modules.flow.entity.WorkFlowDto;

import java.util.List;
import java.util.Map;

/**
 * 流程节点实例表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
public interface NodeInstanceService {
	
	Integer queryObjectbyFormId(String formId);
	
	NodeInstanceEntity queryObjectByMap(Map<String, Object> map);
	
	List<NodeInstanceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NodeInstanceEntity nodeInstance);
	
	void update(NodeInstanceEntity nodeInstance);
	
	void delete(String formId);
	
	void deleteBatch(String[] formIds);
	/**
	 * 更新实例为完成  formId(必须)，userId(必须)
	 */
	void updateInstanceToFinish(Map<String, Object> map);

	R select(String formId);

	List<NodeInstanceEntity> queryListByFormId(Map<String, Object> map);

	NodeInstanceEntity getEndNode(String formId);

	void sendBack(WorkFlowDto workFlowDto);

	void delNodeInstance(String formId);
}
