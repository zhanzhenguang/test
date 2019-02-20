package com.it.zzg.modules.flow.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.it.zzg.common.utils.R;
import com.it.zzg.common.utils.SpringContextUtils;
import com.it.zzg.modules.Constants;
import com.it.zzg.modules.flow.entity.ExecuteHisEntity;
import com.it.zzg.modules.flow.entity.NodeEntity;
import com.it.zzg.modules.flow.entity.NodeInstanceEntity;
import com.it.zzg.modules.flow.entity.WorkFlowDto;
import com.it.zzg.modules.flow.service.ExecuteHisService;
import com.it.zzg.modules.flow.service.ListService;
import com.it.zzg.modules.flow.service.NodeInstanceService;
import com.it.zzg.modules.flow.service.NodeService;
import com.it.zzg.modules.flow.service.WorkFlowService;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.utils.BaseUtils;
import com.it.zzg.modules.utils.IdWorker;

@Service
public class WorkFlowServiceImpl implements WorkFlowService{

	@Resource
	private ListService flowService;
	@Resource
	private NodeService nodeService;
	@Resource
	private NodeInstanceService nodeInstanceService;
	@Resource
	private ExecuteHisService executehisService;
	@Autowired
	private EhCacheManager ehCacheManager;
	/**
	 * 开启流程   flowCode(必须)，userId(必须)，formId(必须)，orgId(必须--如果当前流程是当前部门内使用的，只需要填入当前部门，如果是有同级部门，则需要填入上级部分id)
	 * @param workFlowDto
	 * @return
	 */
	@Transactional
	@Override
	public R startProcess(WorkFlowDto workFlowDto) {
		if(StringUtils.isEmpty(workFlowDto.getFormId())){
			return R.error("[formId]不能为空");
		}
		if(StringUtils.isEmpty(workFlowDto.getOrgId())){
			return R.error("[orgId]不能为空");
		}
		if(StringUtils.isEmpty(workFlowDto.getFlowCode())){
			return R.error("[flowCode]不能为空");
		}
		if(workFlowDto.getUserId()==null){
			return R.error("[userId]不能为空");
		}
		
//		Integer count = nodeInstanceService.queryObjectbyFormId(workFlowDto.getFormId());
//		if(count!=null&&count>0){
//			return R.error("[formId]已经存在，不能创建重复数据");
//		}
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		
		Map<String,Object> map = new HashMap<>();
		map.put("flowCode", workFlowDto.getFlowCode());
		List<NodeEntity> nodes =  nodeService.queryList(map);
		NodeInstanceEntity nodeInstance = null;
		String firstNode = "";
		String secondNode = "";
		IdWorker id = new IdWorker(0, 0);
		for(NodeEntity node : nodes){
			if(node.getTyp()==Constants.NODE_TYP.T_1){
				firstNode=node.getNodeCode();
				secondNode=node.getPosX();
			}
		}
		for(NodeEntity node : nodes){
			nodeInstance = new NodeInstanceEntity();
			nodeInstance.setId(id.nextStringId());
			nodeInstance.setFormId(workFlowDto.getFormId());
			nodeInstance.setOrgId(workFlowDto.getOrgId());
			nodeInstance.setExecuteRole(node.getExecuteRole());
			nodeInstance.setPosI(node.getPosI());
			nodeInstance.setPosX(node.getPosX());
			nodeInstance.setPosY(node.getPosY());
			nodeInstance.setPosZ(node.getPosZ());
			nodeInstance.setPosIName(node.getPosIName());
			nodeInstance.setPosXName(node.getPosXName());
			nodeInstance.setPosYName(node.getPosYName());
			nodeInstance.setPosZName(node.getPosZName());
			nodeInstance.setCreateUserId(workFlowDto.getUserId());
			nodeInstance.setCreateTime(new Date());
			nodeInstance.setFlowCode(node.getFlowCode());
			nodeInstance.setNodeCode(node.getNodeCode());
			nodeInstance.setNodeName(node.getNodeName());
			nodeInstance.setTyp(node.getTyp());
			if(node.getNodeCode().equals(secondNode)){
				nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_3);
			}else if(node.getNodeCode().equals(firstNode)){
				nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_1);
			}else{
				nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_2);
			}
			nodeInstance.setStatus(Constants.STATUS.S_1);
			nodeInstance.setCreateTime(new Date());
			nodeInstance.setCreateUserId(u.getUserId());
			nodeInstanceService.save(nodeInstance);
		}
		ExecuteHisEntity executeHis = new ExecuteHisEntity();
		executeHis.setId(id.nextStringId());
		if(StringUtils.isNotBlank(workFlowDto.getComment())){
			executeHis.setComment(workFlowDto.getComment());
		}else{
			executeHis.setComment("开始流程");
		}
		executeHis.setCreateTime(new Date());
		executeHis.setCreateUserId(workFlowDto.getUserId());
		executeHis.setFlowCode(workFlowDto.getFlowCode());
		executeHis.setFormId(workFlowDto.getFormId());
		executeHis.setNode(firstNode);
		executeHis.setStatus(Constants.STATUS.S_1);
		executehisService.save(executeHis);
		return R.ok();
		
	}

	/**
	 * 关闭流程   userId(必须)，formId(必须)
	 * @param workFlowDto
	 * @return
	 */
	@Transactional
	@Override
	public R endProcess(WorkFlowDto workFlowDto) {
		if(StringUtils.isEmpty(workFlowDto.getFormId())){
			return R.error("[formId]不能为空");
		}
		if(workFlowDto.getUserId()==null){
			return R.error("[userId]不能为空");
		}
		Map<String,Object> map = new HashMap<>();
		map.put("formId", workFlowDto.getFormId());
		map.put("userId", workFlowDto.getUserId());
		if(workFlowDto.getStatus()!=null){
			map.put("status",workFlowDto.getStatus());
		}else{
			map.put("status", Constants.STATUS.S_2);
		}
		/**
		 * 更新实例为完成  formId(必须)，userId(必须)
		 */
		
		nodeInstanceService.updateInstanceToFinish(map);
		
		IdWorker id = new IdWorker(0, 0);
		ExecuteHisEntity executeHis = new ExecuteHisEntity();
		executeHis.setId(id.nextStringId());
		if(StringUtils.isNotBlank(workFlowDto.getComment())){
			executeHis.setComment(workFlowDto.getComment());
		}else{
			executeHis.setComment("结束流程");
		}
		NodeInstanceEntity nodeInstance  = nodeInstanceService.getEndNode(workFlowDto.getFormId());
		executeHis.setCreateTime(new Date());
		executeHis.setCreateUserId(workFlowDto.getUserId());
		executeHis.setFlowCode(nodeInstance.getFlowCode());
		executeHis.setFormId(nodeInstance.getFormId());
		executeHis.setNode(nodeInstance.getNodeCode());
		executeHis.setStatus(Constants.STATUS.S_1);
		executehisService.save(executeHis);
		return R.ok();
	}

	/**
	 * 节点变更     userId(必须)，formId(必须),typ(必须,对应 Constants.NODE_SEQ),comment(必须)
	 * @param workFlowDto
	 * @return
	 */
	@Transactional
	@Override
	public R go(WorkFlowDto workFlowDto) {
		if(StringUtils.isEmpty(workFlowDto.getNodeSeq())){
			return R.error("[nodeSeq]不能为空");
		}
		if(StringUtils.isEmpty(workFlowDto.getFormId())){
			return R.error("[formId]不能为空");
		}
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);
		workFlowDto.setUserId(u.getUserId());
		workFlowDto.setExecuteRoleList(u.getFlowRoleIdList());
		if(workFlowDto.getUserId()==null){
			return R.error("[userId]不能为空");
		}
		if(workFlowDto.getExecuteRoleList()==null){
			return R.error("该用户角色不能为空");
		}
		/*switch (workFlowDto.getNodeSeq()) {
		case Constants.NODE_SEQ.FRONT://下一节点1
			r = front(workFlowDto);
			break;
		case Constants.NODE_SEQ.TOP://下一节点2
			r = top(workFlowDto);
			break;
		case Constants.NODE_SEQ.BOTTOM://下一节点3
			r = bottom(workFlowDto);
			break;
		case Constants.NODE_SEQ.BACK://上一节点
			r = back(workFlowDto);
			break;
		default:
			break;
		}*/
		Map<String,Object> map = new HashMap<>();
		map.put("formId", workFlowDto.getFormId());
		map.put("userId", workFlowDto.getUserId());
		map.put("qryNodeStatus", "all");
		map.put("pageType", "todo");
		map.put("executeRoleList", workFlowDto.getExecuteRoleList());
		List<NodeInstanceEntity> nodeInstances = nodeInstanceService.queryListByFormId(map);
		String[] beanAndMethon = null;
		ExecuteHisEntity executeHis = new ExecuteHisEntity();
		String nextNode =  null;
		//int i=5/0;
		/**
		 * 获取当前节点
		 */
		for(NodeInstanceEntity nodeInstance : nodeInstances){
			if(nodeInstance.getNodeStatus()==Constants.NODE_STATUS.S_3){
				nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_1);
				nodeInstanceService.update(nodeInstance);
				executeHis.setFlowCode(nodeInstance.getFlowCode());
				executeHis.setNode(nodeInstance.getNodeCode());
				if(workFlowDto.getNodeSeq().equals(Constants.NODE_SEQ.X)){
					nextNode = nodeInstance.getPosX();
					
					if(StringUtils.isNotEmpty(nodeInstance.getxBeanMethod())){
						beanAndMethon = nodeInstance.getxBeanMethod().split(",");
						if(beanAndMethon.length==2){
							workFlowDto.setBeanName(beanAndMethon[0]);
							workFlowDto.setMethonName(beanAndMethon[1]);
						}
					}
				}else if(workFlowDto.getNodeSeq().equals(Constants.NODE_SEQ.Y)){
					nextNode = nodeInstance.getPosY();
					
					if(StringUtils.isNotEmpty(nodeInstance.getxBeanMethod())){
						beanAndMethon = nodeInstance.getyBeanMethod().split(",");
						if(beanAndMethon.length==2){
							workFlowDto.setBeanName(beanAndMethon[0]);
							workFlowDto.setMethonName(beanAndMethon[1]);
						}
					}
				}else if(workFlowDto.getNodeSeq().equals(Constants.NODE_SEQ.Z)){
					nextNode = nodeInstance.getPosZ();
					
					if(StringUtils.isNotEmpty(nodeInstance.getzBeanMethod())){
						beanAndMethon = nodeInstance.getxBeanMethod().split(",");
						if(beanAndMethon.length==2){
							workFlowDto.setBeanName(beanAndMethon[0]);
							workFlowDto.setMethonName(beanAndMethon[1]);
						}
					}
				}else if(workFlowDto.getNodeSeq().equals(Constants.NODE_SEQ.I)){
					nextNode = nodeInstance.getPosI();
					
					if(StringUtils.isNotEmpty(nodeInstance.getiBeanMethod())){
						beanAndMethon = nodeInstance.getxBeanMethod().split(",");
						if(beanAndMethon.length==2){
							workFlowDto.setBeanName(beanAndMethon[0]);
							workFlowDto.setMethonName(beanAndMethon[1]);
						}
					}
				}else{
					throw new RuntimeException("没有执行更新下一条数据");
				}
				break;
			}
		}
		
		//更新下一节点为待处理
		/*if(nextNode.getTyp().equals(Constants.NODE_TYP.T_2)){//结束
			workFlowDto.setStatus(Constants.STATUS.S_2);
			this.endProcess(workFlowDto);
		}else if(nextNode.getTyp().equals(Constants.NODE_TYP.T_1)){//开始
			workFlowDto.setStatus(Constants.STATUS.S_0);
			this.endProcess(workFlowDto);
		}else {
			nextNode.setNodeStatus(Constants.NODE_STATUS.S_3);
			nodeInstanceService.update(nextNode);
		}*/
	
		for(NodeInstanceEntity nodeInstance : nodeInstances){
			
			if(nodeInstance.getNodeCode().equals(nextNode)){
				
				if(nodeInstance.getTyp().equals(Constants.NODE_TYP.T_2)){//结束
					nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_1);
					nodeInstanceService.update(nodeInstance);
					workFlowDto.setStatus(Constants.STATUS.S_2);
					this.endProcess(workFlowDto);
				}else if(nodeInstance.getTyp().equals(Constants.NODE_TYP.T_1)){//开始
					nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_1);
					nodeInstanceService.update(nodeInstance);
					workFlowDto.setStatus(Constants.STATUS.S_0);
					this.endProcess(workFlowDto);
				}else {
					nodeInstance.setNodeStatus(Constants.NODE_STATUS.S_3);
					nodeInstance.setCreateTime(new Date());
					nodeInstance.setCreateUserId(u.getUserId());
					nodeInstanceService.update(nodeInstance);
				}
				break;
			}
		}
		
		//更新旧记录状态为2
		executehisService.updateByFormId(workFlowDto.getFormId());
		//新增记录状态为1
		IdWorker id = new IdWorker(0, 0);
		executeHis.setId(id.nextStringId());
		if(StringUtils.isNotBlank(workFlowDto.getComment())){
			executeHis.setComment(workFlowDto.getComment());
		}else{
			executeHis.setComment("");
		}
		executeHis.setCreateTime(new Date());
		executeHis.setCreateUserId(workFlowDto.getUserId());
		executeHis.setFormId(workFlowDto.getFormId());
		executeHis.setStatus(Constants.STATUS.S_1);
		executehisService.save(executeHis);
		if(StringUtils.isNotEmpty(workFlowDto.getBeanName())&&StringUtils.isNotEmpty(workFlowDto.getMethonName())){
			try {
				Object serviceBean = SpringContextUtils.getBean(workFlowDto.getBeanName());
				Class<?> cz = serviceBean.getClass();
				if(cz!=null){
					//获取执行方法
					Method mh = ReflectionUtils.findMethod(cz, workFlowDto.getMethonName(),String.class);
					if(mh!=null){
						ReflectionUtils.invokeMethod(mh,serviceBean,workFlowDto.getFormId());
			        	return R.ok();
					}else{
						throw new RuntimeException("执行bean["+workFlowDto.getMethonName()+"]方法异常");
					}
				}else{
					throw new RuntimeException("获取不到Bean["+workFlowDto.getBeanName()+"]");
				}
			} catch (Exception e) {
				throw new RuntimeException("执行["+workFlowDto.getBeanName()+"]方法异常,err="+e.getMessage());
			}
		}else{
			return R.ok();
		}
	}

}
