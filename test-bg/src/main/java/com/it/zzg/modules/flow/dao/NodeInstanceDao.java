package com.it.zzg.modules.flow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.it.zzg.modules.flow.entity.NodeInstanceEntity;
import com.it.zzg.modules.sys.dao.BaseDao;

/**
 * 流程节点实例表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@Mapper
public interface NodeInstanceDao extends BaseDao<NodeInstanceEntity> {

	/**
	 * 更新实例为完成  formId(必须)，userId(必须)
	 */
	void updateInstanceToFinish(Map<String, Object> map);

	NodeInstanceEntity queryObjectByMap(Map<String, Object> map);

	Integer queryObjectbyFormId(String id);

	List<NodeInstanceEntity> select(@Param("formId")String formId);

	List<NodeInstanceEntity> queryListByFormId(Map<String, Object> map);

	NodeInstanceEntity queryObjectbyCondition(Map<String, Object> map);
	//修改合同状态
	void updateContStatus(Map<String,Object> map);
	//删除审核记录(逻辑删除，设置status=0)
	void delNodeIns(Map<String,Object> map);
	//删除审核记录（物理删除）
	void delNodeInstance(Map<String,Object> map);

}
