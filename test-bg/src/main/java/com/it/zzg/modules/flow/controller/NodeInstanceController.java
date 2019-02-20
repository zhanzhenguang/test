package com.it.zzg.modules.flow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.common.utils.PageUtils;
import com.it.zzg.common.utils.Query;
import com.it.zzg.common.utils.R;
import com.it.zzg.modules.flow.entity.NodeInstanceEntity;
import com.it.zzg.modules.flow.entity.WorkFlowDto;
import com.it.zzg.modules.flow.service.NodeInstanceService;
import com.it.zzg.modules.flow.service.WorkFlowService;
import com.it.zzg.modules.sys.controller.AbstractController;
import com.it.zzg.modules.sys.entity.SysOrgEntity;
import com.it.zzg.modules.sys.entity.SysUserEntity;
import com.it.zzg.modules.utils.BaseUtils;

/**
 * 流程节点实例表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:24
 */
@RestController
@RequestMapping("/flow/nodeinstance")
public class NodeInstanceController extends AbstractController {
	@Autowired
	private NodeInstanceService nodeInstanceService;
	@Autowired
	private EhCacheManager ehCacheManager;
	@Autowired
	private WorkFlowService workFlowService;

	/**
	 * 列表
	 */
	@RequestMapping("/list/{pageType}")
	public R list(@RequestParam Map<String, Object> params,@PathVariable("pageType") String pageType) {
		SysUserEntity u = BaseUtils.getUser(ehCacheManager);

		if (u.getFlowRoleIdList() != null && u.getFlowRoleIdList().size() > 0 && u.getUserId() != null) {
			params.put("userId", u.getUserId());
			SysOrgEntity sysOrgEntity = u.getSysOrgEntity();
			params.put("orgId", sysOrgEntity.getId());
			if(u.getOrgIdList()!=null) {
				params.put("orgIdList", u.getOrgIdList());
			}
			
			params.put("pageType", pageType);
			params.put("executeRoleList", u.getFlowRoleIdList());
			// 查询列表数据
			Query query = new Query(params);

			List<NodeInstanceEntity> nodeInstanceList = nodeInstanceService.queryList(query);
			int total = nodeInstanceService.queryTotal(query);

			PageUtils pageUtil = new PageUtils(nodeInstanceList, total, query.getLimit(), query.getPage());

			return R.ok().put("page", pageUtil);
		} else {
			return R.ok("流程角色和用户id不能为空");
		}
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") String id,String pageType) {

		if (StringUtils.isNotEmpty(id)) {
			SysUserEntity u = BaseUtils.getUser(ehCacheManager);

			if (u.getFlowRoleIdList() != null && u.getFlowRoleIdList().size() > 0 && u.getUserId() != null) {
				Map<String,Object> params = new HashMap<>();
				params.put("id",id);
				params.put("userId", u.getUserId());
				if(!pageType.equals("todo")) {
					params.put("qryNodeStatus", "all");
				}
				params.put("orgId", u.getSysOrgEntity().getId());
				params.put("executeRoleList", u.getFlowRoleIdList());
				NodeInstanceEntity nodeInstance = nodeInstanceService.queryObjectByMap(params);;
				return R.ok().put("nodeInstance", nodeInstance);
			} else {
				return R.error("流程角色和用户id不能为空");
			}
		} else {
			return R.error("[formId]不能为空");
		}

	}

	/**
	 * 跳转节点
	 */
	@RequestMapping("/go")
	public R go(@RequestBody WorkFlowDto worFlowDto) {
		try {
			return workFlowService.go(worFlowDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}

	@RequestMapping("/sendBack")
	public R sendBack(@RequestBody WorkFlowDto worFlowDto) {
		this.nodeInstanceService.sendBack(worFlowDto);
		return R.ok();
	}

	@RequestMapping("/delNodeInstance/{formId}")
	public R delNodeInstance(@PathVariable("formId") String formId) {
		this.nodeInstanceService.delNodeInstance(formId);
		return R.ok();
	}

	/**
	 * 开启流程
	 */
	@RequestMapping("/start")
	public R start(@RequestBody WorkFlowDto worFlowDto) {

		try {
			SysUserEntity u = BaseUtils.getUser(ehCacheManager);
			worFlowDto.setUserId(u.getUserId());
			SysOrgEntity sysOrgEntity = u.getSysOrgEntity();
			worFlowDto.setOrgId(String.valueOf(sysOrgEntity.getId()));
			return workFlowService.startProcess(worFlowDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}

	/**
	 * 关闭流程
	 */
	@RequestMapping("/end")
	public R end(@RequestBody WorkFlowDto worFlowDto) {
		try {
			return workFlowService.endProcess(worFlowDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}
	
	@RequestMapping("/select/{formId}")
	public R select(@PathVariable("formId") String formId) {
		try {
			return nodeInstanceService.select(formId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}
}
