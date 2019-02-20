package com.it.zzg.modules.flow.service;

import javax.servlet.http.HttpServletRequest;

import com.it.zzg.common.utils.R;
import com.it.zzg.modules.flow.entity.WorkFlowDto;

public interface WorkFlowService {

	
	/**
	 * 开启流程   flowCode(必须)，userId(必须)，formId(必须)，orgId(必须--如果当前流程是当前部门内使用的，只需要填入当前部门，如果是有同级部门，则需要填入上级部分id)
	 * @param worFlowDto
	 * @return
	 */
	R startProcess(WorkFlowDto worFlowDto);
	
	/**
	 * 关闭流程   userId(必须)，formId(必须)
	 * @param worFlowDto
	 * @return
	 */
	R endProcess(WorkFlowDto worFlowDto);
	
	/**
	 * 节点变更     userId(必须)，formId(必须),typ(必须,对应 Constants.NODE_SEQ),comment(必须)
	 * @param worFlowDto
	 * @return
	 */
	R go(WorkFlowDto worFlowDto);

	
}
