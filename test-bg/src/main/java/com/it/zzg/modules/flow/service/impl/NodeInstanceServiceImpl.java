package com.it.zzg.modules.flow.service.impl;

import java.util.*;

import com.it.zzg.modules.flow.entity.ExecuteHisEntity;
import com.it.zzg.modules.flow.entity.WorkFlowDto;
import com.it.zzg.modules.flow.service.ExecuteHisService;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.utils.BaseUtils;
import com.it.zzg.modules.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.common.utils.R;
import com.it.zzg.modules.Constants;
import com.it.zzg.modules.flow.dao.NodeInstanceDao;
import com.it.zzg.modules.flow.entity.NodeInstanceEntity;
import com.it.zzg.modules.flow.service.NodeInstanceService;

import javax.annotation.Resource;


@Service("nodeInstanceService")
public class NodeInstanceServiceImpl implements NodeInstanceService {
	@Autowired
	private NodeInstanceDao nodeInstanceDao;
	@Resource
	private ExecuteHisService executehisService;
	@Autowired
	private NodeInstanceService nodeInstanceService;
	@Autowired
	private EhCacheManager ehCacheManager;
	
	@Override
	public Integer queryObjectbyFormId(String formId){
		return nodeInstanceDao.queryObjectbyFormId(formId);
	}
	@Override
	public NodeInstanceEntity queryObjectByMap(Map<String, Object> map) {
		return nodeInstanceDao.queryObjectByMap(map);
	}
	
	@Override
	public List<NodeInstanceEntity> queryList(Map<String, Object> map){
		return nodeInstanceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return nodeInstanceDao.queryTotal(map);
	}
	
	@Override
	public void save(NodeInstanceEntity nodeInstance){
		nodeInstanceDao.save(nodeInstance);
	}
	
	@Override
	public void update(NodeInstanceEntity nodeInstance){
		nodeInstanceDao.update(nodeInstance);
	}
	
	@Override
	public void delete(String formId){
		nodeInstanceDao.delete(formId);
	}
	
	@Override
	public void deleteBatch(String[] formIds){
		nodeInstanceDao.deleteBatch(formIds);
	}

	/**
	 * 更新实例为完成  formId(必须)，userId(必须)
	 */
	@Override
	public void updateInstanceToFinish(Map<String, Object> map) {
		nodeInstanceDao.updateInstanceToFinish(map);
	}
	@Override
	public R select(String formId) {
		Map<String,Object> map = new HashMap<>();
		map.put("formId", formId);
		List<NodeInstanceEntity> list =  nodeInstanceDao.queryListByFormId(map);
		List<String> steps = new ArrayList<>();
		Map<String,Object> data = null;
		NodeInstanceEntity node = null;
		int curStep = 1;
		for(int i=0;i<list.size();i++) {
			node = list.get(i);
			steps.add(node.getNodeName());
			if(node.getNodeStatus()!=null) {
				if(node.getNodeStatus().equals(Constants.NODE_STATUS.S_1)) {
					curStep += 1;
				}
			}
		}
		if(curStep==1)curStep=2;
		
		if(curStep>list.size()){
			curStep=list.size();
		}
		data = new HashMap<>();
		data.put("steps", steps);
		data.put("curStep", curStep);
		data.put("stepCounts", list.size());
		return R.ok(data);
	}
	@Override
	public List<NodeInstanceEntity> queryListByFormId(Map<String, Object> map) {
		return this.nodeInstanceDao.queryListByFormId(map);
	}
	@Override
	public NodeInstanceEntity getEndNode(String formId) {
		//return this.nodeInstanceDao.getEndNode(formId);
		Map<String,Object> map = new HashMap<>();
		map.put("formId", formId);
		map.put("typ", 2);
		return nodeInstanceDao.queryObjectbyCondition(map);
		
	}

	@Override
	public void sendBack(WorkFlowDto workFlowDto) {
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		workFlowDto.setUserId(u.getUserId());
		workFlowDto.setExecuteRoleList(u.getFlowRoleIdList());
		ExecuteHisEntity executeHis = new ExecuteHisEntity();
		//新增记录状态为1
		IdWorker id = new IdWorker(0, 0);
		executeHis.setId(id.nextStringId());
		if(StringUtils.isNotBlank(workFlowDto.getComment())){
			executeHis.setComment(workFlowDto.getComment());
		}else{
			executeHis.setComment("");
		}
		Map<String,Object> map = new HashMap<>();
		map.put("formId", workFlowDto.getFormId());
		map.put("userId", workFlowDto.getUserId());
		map.put("qryNodeStatus", "all");
		map.put("pageType", "todo");
		map.put("executeRoleList", workFlowDto.getExecuteRoleList());
		List<NodeInstanceEntity> nodeInstances = nodeInstanceService.queryListByFormId(map);
		for(NodeInstanceEntity nodeInstance : nodeInstances){
			if(nodeInstance.getNodeStatus()==Constants.NODE_STATUS.S_3){
				executeHis.setFlowCode(nodeInstance.getFlowCode());
				executeHis.setNode(nodeInstance.getNodeCode());
			}
		}
		executeHis.setCreateTime(new Date());
		executeHis.setCreateUserId(workFlowDto.getUserId());
		executeHis.setFormId(workFlowDto.getFormId());
		executeHis.setStatus(Constants.STATUS.S_1);
		executehisService.save(executeHis);


		Map<String,Object> map2 = new HashMap<>();
		map2.put("formId", workFlowDto.getFormId());
		map2.put("contId", workFlowDto.getContId());
		this.nodeInstanceDao.updateContStatus(map2);
		this.nodeInstanceDao.delNodeIns(map2);
	}

	@Override
	public void delNodeInstance(String formId) {
		Map<String,Object> map = new HashMap<>();
		map.put("formId", formId);
		this.nodeInstanceDao.delNodeInstance(map);
	}
}
